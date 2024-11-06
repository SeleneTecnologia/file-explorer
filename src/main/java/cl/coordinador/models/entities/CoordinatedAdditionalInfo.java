package cl.coordinador.models.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "coordinated_additional_info", schema = "dbo")
public class CoordinatedAdditionalInfo {

    @Id
    private Integer id;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(name = "entry_timestamp", nullable = false)
    private LocalDateTime entryTimestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regulated_load_status_id", nullable = false)
    private LoadStatus regulatedLoadStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regulated_process_status_id", nullable = false)
    private ProcessStatus regulatedProcessStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_dx_load_status_id ", nullable = false)
    private LoadStatus freeDxLoadStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_dx_process_status_id ", nullable = false)
    private ProcessStatus freeDxProcessStatus;

    @Column(name = "file_path", length = 255)
    private String filePath;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(columnDefinition = "uniqueidentifier", nullable = true)
    private UUID uuid = UUID.randomUUID();

    @Column(name = "start_extraction_timestamp")
    private LocalDateTime startExtractionTimestamp;

    @Column(name = "end_extraction_timestamp")
    private LocalDateTime endExtractionTimestamp;

    @Column(name = "start_validation_timestamp")
    private LocalDateTime startValidationTimestamp;

    @Column(name = "end_validation_timestamp")
    private LocalDateTime endValidationTimestamp;

    @Column(name = "start_transfer_timestamp")
    private LocalDateTime startTransferTimestamp;

    @Column(name = "end_transfer_timestamp")
    private LocalDateTime endTransferTimestamp;

    @Column(name = "start_generate_log_timestamp")
    private LocalDateTime startGenerateLogTimestamp;

    @Column(name = "end_generate_log_timestamp")
    private LocalDateTime endGenerateLogTimestamp;

    @Column(name = "eaf_attachments_5_id")
    private Integer eafAttachmentId;
    @Column(name = "eaf_id")
    private Integer eafId;

    @Column(name = "all_associated", nullable = false)
    private Boolean allAssociated;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getEntryTimestamp() {
        return entryTimestamp;
    }

    public void setEntryTimestamp(LocalDateTime entryTimestamp) {
        this.entryTimestamp = entryTimestamp;
    }

    public LoadStatus getRegulatedLoadStatus() {
        return regulatedLoadStatus;
    }

    public void setRegulatedLoadStatus(LoadStatus regulatedLoadStatus) {
        this.regulatedLoadStatus = regulatedLoadStatus;
    }

    public ProcessStatus getRegulatedProcessStatus() {
        return regulatedProcessStatus;
    }

    public void setRegulatedProcessStatus(ProcessStatus regulatedProcessStatus) {
        this.regulatedProcessStatus = regulatedProcessStatus;
    }

    public LoadStatus getFreeDxLoadStatus() {
        return freeDxLoadStatus;
    }

    public void setFreeDxLoadStatus(LoadStatus freeDxLoadStatus) {
        this.freeDxLoadStatus = freeDxLoadStatus;
    }

    public ProcessStatus getFreeDxProcessStatus() {
        return freeDxProcessStatus;
    }

    public void setFreeDxProcessStatus(ProcessStatus freeDxProcessStatus) {
        this.freeDxProcessStatus = freeDxProcessStatus;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getStartExtractionTimestamp() {
        return startExtractionTimestamp;
    }

    public void setStartExtractionTimestamp(LocalDateTime startExtractionTimestamp) {
        this.startExtractionTimestamp = startExtractionTimestamp;
    }

    public LocalDateTime getEndExtractionTimestamp() {
        return endExtractionTimestamp;
    }

    public void setEndExtractionTimestamp(LocalDateTime endExtractionTimestamp) {
        this.endExtractionTimestamp = endExtractionTimestamp;
    }

    public LocalDateTime getStartValidationTimestamp() {
        return startValidationTimestamp;
    }

    public void setStartValidationTimestamp(LocalDateTime startValidationTimestamp) {
        this.startValidationTimestamp = startValidationTimestamp;
    }

    public LocalDateTime getEndValidationTimestamp() {
        return endValidationTimestamp;
    }

    public void setEndValidationTimestamp(LocalDateTime endValidationTimestamp) {
        this.endValidationTimestamp = endValidationTimestamp;
    }

    public LocalDateTime getStartTransferTimestamp() {
        return startTransferTimestamp;
    }

    public void setStartTransferTimestamp(LocalDateTime startTransferTimestamp) {
        this.startTransferTimestamp = startTransferTimestamp;
    }

    public LocalDateTime getEndTransferTimestamp() {
        return endTransferTimestamp;
    }

    public void setEndTransferTimestamp(LocalDateTime endTransferTimestamp) {
        this.endTransferTimestamp = endTransferTimestamp;
    }

    public LocalDateTime getStartGenerateLogTimestamp() {
        return startGenerateLogTimestamp;
    }

    public void setStartGenerateLogTimestamp(LocalDateTime startGenerateLogTimestamp) {
        this.startGenerateLogTimestamp = startGenerateLogTimestamp;
    }

    public LocalDateTime getEndGenerateLogTimestamp() {
        return endGenerateLogTimestamp;
    }

    public void setEndGenerateLogTimestamp(LocalDateTime endGenerateLogTimestamp) {
        this.endGenerateLogTimestamp = endGenerateLogTimestamp;
    }

    public Integer getEafAttachmentId() {
        return eafAttachmentId;
    }

    public void setEafAttachmentId(Integer eafAttachmentId) {
        this.eafAttachmentId = eafAttachmentId;
    }

    public Integer getEafId() {
        return eafId;
    }

    public void setEafId(Integer eafId) {
        this.eafId = eafId;
    }

    public Boolean getAllAssociated() {
        return allAssociated;
    }

    public void setAllAssociated(Boolean allAssociated) {
        this.allAssociated = allAssociated;
    }
}
