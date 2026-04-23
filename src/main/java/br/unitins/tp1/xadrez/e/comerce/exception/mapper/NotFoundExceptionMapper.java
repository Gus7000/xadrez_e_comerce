package br.unitins.tp1.xadrez.e.comerce.exception.mapper;

import br.unitins.tp1.xadrez.e.comerce.exception.ProblemDetails;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    private static final String TYPE = "https://example.com/problems/not-found";
    private static final String TITLE = "Not Found";

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(NotFoundException exception) {
        String instancePath = uriInfo.getRequestUri().getPath();
        if (!instancePath.startsWith("/")) {
            instancePath = "/" + instancePath;
        }

        ProblemDetails problem = new ProblemDetails(
                TYPE,
                TITLE,
                Response.Status.NOT_FOUND.getStatusCode(),
                exception.getMessage(),
                instancePath);

        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.valueOf("application/problem+json"))
                .entity(problem)
                .build();
    }
}
