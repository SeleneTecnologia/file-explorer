package cl.coordinador.exceptions.mappers;

import cl.coordinador.exceptions.FileExplorerApplicationException;
import cl.coordinador.v1.dto.ExceptionDTO;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConnectionControlApplicationExceptionMapper implements ExceptionMapper<FileExplorerApplicationException> {

    @Override
    public Response toResponse(FileExplorerApplicationException exception) {
        var exceptionResponse = new ExceptionDTO(exception.getCode().name(),exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exceptionResponse).build();
    }
}
