package cl.coordinador.services.impl;

import cl.coordinador.clients.reporteFallasResponse.FileRecord;
import cl.coordinador.clients.reporteFallasResponse.ReporteFallasResponseDTO;
import cl.coordinador.exceptions.FileExplorerApplicationException;
import cl.coordinador.exceptions.FileExplorerExceptionCodes;
import cl.coordinador.models.entities.CoordinatedAdditionalInfo;
import cl.coordinador.models.entities.EafAttachment;
import cl.coordinador.models.entities.LoadStatus;
import cl.coordinador.models.entities.ProcessStatus;
import cl.coordinador.models.repositories.CoordinatedAdditionalInfoRepository;
import cl.coordinador.models.repositories.EafAttachmentRepository;
import cl.coordinador.services.ConnectionControlClient;
import cl.coordinador.services.FileExplorerService;
import cl.coordinador.services.S3Service;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;


@ApplicationScoped
public class FileExplorerServiceImpl implements FileExplorerService {
    private static final Logger LOG = Logger.getLogger(FileExplorerServiceImpl.class);

    @Inject
    @RestClient
    ConnectionControlClient connectionControlClient;

    private final S3Service s3Service;

    private final CoordinatedAdditionalInfoRepository coordinatedAdditionalInfoRepository;

    private final EafAttachmentRepository eafAttachmentRepository;

    @ConfigProperty(name = "coordinated.additional-info.target-path")
    String targetPath;
    @ConfigProperty(name = "coordinated.additional-info.accepted-mimetype")
    String acceptedMimetype;

    @ConfigProperty(name = "coordinated.additional-info.accepted-sheet")
    String acceptedSheet;

    private final static String MANUAL_ENTRY = "MN99";

    @Inject
    public FileExplorerServiceImpl(S3Service s3Service, CoordinatedAdditionalInfoRepository coordinatedAdditionalInfoRepository, EafAttachmentRepository eafAttachmentRepository) {
        this.s3Service = s3Service;
        this.coordinatedAdditionalInfoRepository = coordinatedAdditionalInfoRepository;
        this.eafAttachmentRepository = eafAttachmentRepository;
    }

    @Override
    @Transactional
    public void processFiles(String correlative) {
        try {
            // Obtener el primer EafAttachment donde el atributo sea igual a correlative
            EafAttachment eafAttachment = eafAttachmentRepository.find("codeNeomante", correlative).firstResult();

            if (eafAttachment == null) {
                LOG.warn("No se encontró EafAttachment con el correlative: " + correlative);
                return;
            }

            ReporteFallasResponseDTO report = connectionControlClient.getReport(correlative);
            if (report != null && !report.content().isEmpty()) {
                LOG.info("ADJUNTOS ENCONTRADOS: " + report.content().get(0).files());
                report.content().stream()
                        .findFirst()
                        .ifPresent(content -> content.files().stream()
                                .filter(fileRecord -> acceptedMimetype.equals(fileRecord.mimetype()))
                                .forEach(fileRecord -> {
                                            LOG.info("ADJUNTO ENCONTRADO: " + fileRecord.name());
                                            processFile(fileRecord, eafAttachment);
                                        }
                                ));
            } else {
                LOG.info("NO SE ENCONTRARON ADJUNTOS");
            }
        } catch (Exception e) {
            LOG.error("Error processing files for correlative " + correlative, e);
            throw new FileExplorerApplicationException(FileExplorerExceptionCodes.FILE_EXPLORER_GENERAL_ERROR);
        }
    }

    @Transactional
    public void processFile(FileRecord fileRecord, EafAttachment eafAttachment) {
        LOG.info(fileRecord.toString());
        try {
            LOG.info("INICIO DOWNLOAD FILE");
            Path tempFile = downloadFile(fileRecord.downloadUrl());
            LOG.info("FIN DOWNLOAD FILE");

            if (hasSheet(tempFile, acceptedSheet)) {
                LOG.info("SE encontró adjunto que cumple condiciones: " + fileRecord.name());
                Long seq = coordinatedAdditionalInfoRepository.secuenceId();
                String path = targetPath + seq + "_" + fileRecord.name();

                LOG.info("INICIO SUBIENDO ARCHIVO A S3");
                s3Service.uploadFile(path, tempFile.toString());
                LOG.info("FIN ARCHIVO SUBIDO A S3");

                // Insertar registro en la base de datos
                LOG.info("INICIO INSERCIÓN EN TABLA COORDINATED_ADDITIONAL_INFO ");
                CoordinatedAdditionalInfo info = createAdditionalInfo(seq, fileRecord.name(), path, eafAttachment);
                coordinatedAdditionalInfoRepository.persist(info);
                LOG.info("FIN INSERCIÓN EN TABLA COORDINATED_ADDITIONAL_INFO ");

            } else {
                LOG.info("Archivo: no tiene hoja CONSUMO REGULADO");
            }
            LOG.info("ELIMINACIÓN DE ARCHIVO TEMPORAL");
            Files.deleteIfExists(tempFile);
        } catch (IOException | InvalidFormatException e) {
            LOG.error("Error processing file: " + fileRecord.name(), e);
            throw new FileExplorerApplicationException(FileExplorerExceptionCodes.FILE_EXPLORER_GENERAL_ERROR);
        }
    }

    private CoordinatedAdditionalInfo createAdditionalInfo(Long seq, String fileName, String filePath, EafAttachment eafAttachment) {

        Integer eafId = this.eafAttachmentRepository.findEafIdByHeaderId(eafAttachment.getEafHeaderId());
        LoadStatus loadStatus = new LoadStatus();
        loadStatus.setId(MANUAL_ENTRY);

        ProcessStatus processStatus = new ProcessStatus();
        processStatus.setId(MANUAL_ENTRY);

        CoordinatedAdditionalInfo info = new CoordinatedAdditionalInfo();
        info.setId(seq.intValue());
        info.setName(fileName);
        info.setEntryTimestamp(LocalDateTime.now());
        info.setRegulatedLoadStatus(loadStatus);
        info.setFreeDxLoadStatus(loadStatus);
        info.setRegulatedProcessStatus(processStatus);
        info.setFreeDxProcessStatus(processStatus);
        info.setFilePath(filePath);
        info.setUsername("FILE-EXPLORER");//TODO: agregar username cuando se desarrolle auth
        info.setEafAttachmentId(eafAttachment.getId());
        info.setEafId(eafId);
        info.setAllAssociated(false);

        return info;
    }

    private Path downloadFile(String fileUrl) throws IOException {
        Path tempFile = Files.createTempFile("excel_", ".xlsx");
        try {
            URI uri = new URI(fileUrl.replace(" ", "%20"));
            URL url = uri.toURL();
            var connection = url.openConnection();
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(30000);

            try (var inputStream = connection.getInputStream()) {
                Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (URISyntaxException e) {
            LOG.error("Invalid URI syntax: {}", fileUrl, e);
            throw new IOException("Invalid URL format", e);
        } catch (Exception e) {
            LOG.error("Error saving file:", e);
            throw e;
        }

        return tempFile;
    }

    private boolean hasSheet(Path excelPath, String sheetName) throws IOException, InvalidFormatException {
        try (Workbook workbook = WorkbookFactory.create(excelPath.toFile())) {
            return workbook.getSheet(sheetName) != null;
        }
    }
}
