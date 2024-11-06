package cl.coordinador.exceptions.mappers;

import cl.coordinador.exceptions.FileExplorerBusinessException;
import cl.coordinador.v1.dto.ExceptionDTO;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConnectionControlBusinessExceptionMapper implements ExceptionMapper<FileExplorerBusinessException> {

    @Override
    public Response toResponse(FileExplorerBusinessException exception) {
        var exceptionResponse = new ExceptionDTO(exception.getCode().name(),exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(exceptionResponse).build();
    }
}
