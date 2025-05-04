package jp.co.company.space.api.features.authentication.endpoint;

import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.authentication.domain.AuthenticationTokenCookieCreationFactory;
import jp.co.company.space.api.features.authentication.exception.AuthenticationError;
import jp.co.company.space.api.features.authentication.input.LoginRequestForm;
import jp.co.company.space.api.features.authentication.service.AuthenticationService;
import jp.co.company.space.api.shared.dto.DomainErrorDto;
import jp.co.company.space.api.shared.exception.DomainException;
import jp.co.company.space.api.shared.util.LogBuilder;
import jp.co.company.space.api.shared.util.ResponseFactory;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.logging.Logger;

/**
 * This class represents the REST endpoint for the authentication topic.
 */
@ApplicationScoped
@Path("/auth")
@Tag(name = "Authentication")
public class AuthenticationEndpoint {

    private final static Logger LOGGER = Logger.getLogger(AuthenticationEndpoint.class.getName());

    @Inject
    private AuthenticationService authenticationService;

    private final String authenticationCookiePropertyName;

    @Inject
    protected AuthenticationEndpoint(@ConfigProperty(name = "mp.jwt.token.cookie") String cookiePropertyName) {
        authenticationCookiePropertyName = cookiePropertyName;
    }

    @Path("login")
    @POST
    @PermitAll
    @Operation(summary = "Handles a login attempt", description = "Handles an incoming login attempt, when successful, a authentication token will provided with a cookie.")
    @RequestBody(name = "form", description = "A form with details for a login attempt.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = LoginRequestForm.class)))
    @APIResponses({
            @APIResponse(description = "The user is logged in.", responseCode = "200"),
            @APIResponse(description = "The login attempt was unsuccessful.", responseCode = "401", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@RequestBody LoginRequestForm loginForm) {
        try {
            SignedJWT authenticationToken = authenticationService.login(loginForm);
            NewCookie authenticationCookie = new AuthenticationTokenCookieCreationFactory(authenticationCookiePropertyName, authenticationToken).generate();

            return Response.ok().cookie(authenticationCookie).build();
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(AuthenticationError.LOGIN).withException(exception).build());
            return ResponseFactory.unauthorized().entity(DomainErrorDto.create(AuthenticationError.LOGIN)).build();
        }
    }
}
