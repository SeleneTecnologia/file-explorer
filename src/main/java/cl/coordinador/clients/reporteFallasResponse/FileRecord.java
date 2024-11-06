package cl.coordinador.clients.reporteFallasResponse;

public record FileRecord(
        String created,
        String mimetype,
        String name,
        int size,
        String downloadUrl
) {}
