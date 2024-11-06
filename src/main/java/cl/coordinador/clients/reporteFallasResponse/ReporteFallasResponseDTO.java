package cl.coordinador.clients.reporteFallasResponse;

import java.util.List;

public record ReporteFallasResponseDTO(
        int page,
        int pageSize,
        int totalElements,
        int totalPages,
        List<FallaContent> content
) {}