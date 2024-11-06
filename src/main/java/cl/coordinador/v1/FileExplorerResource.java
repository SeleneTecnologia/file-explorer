package cl.coordinador.v1;

import cl.coordinador.services.FileExplorerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/v1/process")
public class FileExplorerResource {

    @Inject
    FileExplorerService fileExplorerService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getReporteFallas(@QueryParam("correlative") String correlative) {
        fileExplorerService.processFiles(correlative);
    }
}
