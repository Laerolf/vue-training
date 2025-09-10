package jp.co.nova.gate.api.shared.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jp.co.nova.gate.api.shared.dto.ApplicationErrorDto;
import jp.co.nova.gate.api.shared.dto.DomainErrorDto;
import jp.co.nova.gate.api.shared.util.LogBuilder;
import jp.co.nova.gate.api.shared.util.ResponseFactory;

import java.util.logging.Logger;

/**
 * A {@link ExceptionMapper} for exceptions.
 */
@Provider
public class ApplicationExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<Exception> {

    private static final Logger LOGGER = Logger.getLogger(ApplicationExceptionMapper.class.getName());

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof ApplicationException) {
            LOGGER.warning(new LogBuilder("Something went wrong.").withException(exception).build());
        }

        return switch (exception) {
            case DomainException domainException ->
                    Response.serverError().entity(DomainErrorDto.create(domainException)).build();
            case ApplicationException applicationException ->
                    Response.serverError().entity(ApplicationErrorDto.create(applicationException)).build();
            case NotFoundException ignored -> ResponseFactory.notFound().build();
            case null, default ->
                    Response.serverError().entity(ApplicationErrorDto.create(GlobalApplicationError.UNSPECIFIED)).build();
        };
    }
}
