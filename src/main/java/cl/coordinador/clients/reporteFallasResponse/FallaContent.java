package cl.coordinador.clients.reporteFallasResponse;

import java.util.List;

public record FallaContent(
        String id,
        String causa,
        String causaElementos,
        String causaFenomenoElectrico,
        String causaFenomenoFisico,
        String causaModo,
        String codigoCausaDefinitiva,
        String comentarioCausa,
        String comentarioFechaRetorno,
        String comentarioFenomenoElectrico,
        String comentarioFenomenoElemento,
        String comentarioFenomenoFisico,
        String comentarioFenomenoModo,
        String consumo,
        long correlativo,
        List<FileRecord> files
) {}