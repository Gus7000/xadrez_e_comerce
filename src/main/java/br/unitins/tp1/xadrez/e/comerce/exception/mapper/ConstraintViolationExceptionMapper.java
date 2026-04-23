package br.unitins.tp1.xadrez.e.comerce.exception.mapper;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.xadrez.e.comerce.exception.FieldError;
import br.unitins.tp1.xadrez.e.comerce.exception.ProblemDetails;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final String TYPE = "https://example.com/problems/validation-error";
    private static final String TITLE = "Validation Error";

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<FieldError> fieldErrors = exception.getConstraintViolations().stream()
                .map(violation -> new FieldError(
                        fieldFromPath(violation.getPropertyPath().toString()),
                        violation.getMessage()))
                .distinct()
                .collect(Collectors.toList());

        String detail = exception.getConstraintViolations().stream()
                .map(violation -> fieldFromPath(violation.getPropertyPath().toString()) + ": " + violation.getMessage())
                .distinct()
                .collect(Collectors.joining("; "));

        String instancePath = uriInfo.getRequestUri().getPath();
        if (!instancePath.startsWith("/")) {
            instancePath = "/" + instancePath;
        }

        ProblemDetails problem = new ProblemDetails(
                TYPE,
                TITLE,
                422,
                detail,
                instancePath,
                fieldErrors);

        return Response.status(422)
                .type(MediaType.valueOf("application/problem+json"))
                .entity(problem)
                .build();
    }

    private String fieldFromPath(String propertyPath) {
        int index = propertyPath.lastIndexOf('.');
        return index >= 0 ? propertyPath.substring(index + 1) : propertyPath;
    }
}
