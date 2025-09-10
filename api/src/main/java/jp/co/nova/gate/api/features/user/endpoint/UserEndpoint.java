package jp.co.nova.gate.api.features.user.endpoint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jp.co.nova.gate.api.features.authentication.exception.AuthenticationError;
import jp.co.nova.gate.api.features.authentication.exception.AuthenticationException;
import jp.co.nova.gate.api.features.booking.dto.BookingDto;
import jp.co.nova.gate.api.features.booking.service.BookingService;
import jp.co.nova.gate.api.features.user.domain.User;
import jp.co.nova.gate.api.features.user.dto.UserDto;
import jp.co.nova.gate.api.features.user.exception.UserError;
import jp.co.nova.gate.api.features.user.exception.UserException;
import jp.co.nova.gate.api.features.user.service.UserService;
import jp.co.nova.gate.api.shared.dto.DomainErrorDto;
import jp.co.nova.gate.api.shared.exception.DomainErrorDtoBuilder;
import jp.co.nova.gate.api.shared.exception.DomainException;
import jp.co.nova.gate.api.shared.util.LogBuilder;
import jp.co.nova.gate.api.shared.util.ResponseFactory;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.logging.Logger;

import static jp.co.nova.gate.api.shared.openApi.Examples.ID_EXAMPLE;

/**
 * This class represents the REST endpoint for the {@link User} topic.
 */
@ApplicationScoped
@Path("users")
@Tag(name = "Users")
public class UserEndpoint {

    private static final Logger LOGGER = Logger.getLogger(UserEndpoint.class.getName());

    @Inject
    private UserService userService;

    @Inject
    private BookingService bookingService;

    protected UserEndpoint() {
    }

    /**
     * Returns a user for the provided ID.
     *
     * @param context The security context of the request.
     * @return a {@link UserDto}.
     */
    @GET
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Returns the user of the request.", description = "Gets the user of the request if there is any.")
    @Parameter(name = "id", description = "The ID of a user.", example = ID_EXAMPLE)
    @APIResponses({
            @APIResponse(description = "A user.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDto.class))),
            @APIResponse(description = "The user was not found.", responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class))),
            @APIResponse(description = "Unauthorized", responseCode = "401"),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByRequestContext(@Context SecurityContext context) {
        try {
            return userService.findByUserPrincipal(context.getUserPrincipal())
                    .map(user -> Response.ok().entity(UserDto.create(user)).build())
                    .orElse(ResponseFactory.notFound().entity(new DomainErrorDtoBuilder(UserError.FIND_BY_REQUEST_CONTEXT).build()).build());
        } catch (UserException exception) {
            LOGGER.warning(new LogBuilder(UserError.FIND_BY_REQUEST_CONTEXT).withException(exception).build());
            return Response.serverError().entity(new DomainErrorDtoBuilder(exception).build()).build();
        }
    }

    /**
     * Returns a user for the provided ID.
     *
     * @param context The security context of the request.
     * @return a {@link UserDto}.
     */
    @Path("{userId}/bookings")
    @GET
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Returns the bookings of the user with the provided ID.", description = "Gets all bookings for the user matching the provided ID if there is any.")
    @Parameter(name = "userId", description = "The ID of a user.", example = ID_EXAMPLE)
    @APIResponses({
            @APIResponse(description = "The bookings for user with the provided ID.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = BookingDto.class))),
            @APIResponse(description = "Access denied.", responseCode = "403", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class))),
            @APIResponse(description = "Unauthorized", responseCode = "401"),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBookingsByUserId(@Context SecurityContext context, @PathParam("userId") String userId) {
        try {
            if (!context.getUserPrincipal().getName().equals(userId)) {
                throw new AuthenticationException(AuthenticationError.FORBIDDEN);
            }

            List<BookingDto> userBookings = bookingService.getAllByUserId(userId).stream().map(BookingDto::create).toList();
            return Response.ok().entity(userBookings).build();
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(UserError.FIND_BY_REQUEST_CONTEXT).withException(exception).build());

            if (exception.getKey().equals(AuthenticationError.FORBIDDEN.getKey())) {
                return ResponseFactory.forbidden().entity(new DomainErrorDtoBuilder(exception).build()).build();
            } else {
                return Response.serverError().entity(new DomainErrorDtoBuilder(exception).build()).build();
            }
        }
    }
}
