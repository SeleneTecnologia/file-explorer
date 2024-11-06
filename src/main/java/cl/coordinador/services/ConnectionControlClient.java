package cl.coordinador.services;

import cl.coordinador.clients.reporteFallasResponse.ReporteFallasResponseDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "connection-control-api")
@Path("/v1/reporte-fallas")
public interface ConnectionControlClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    ReporteFallasResponseDTO getReport(
            @QueryParam("correlativo") String correlativo
    );
}