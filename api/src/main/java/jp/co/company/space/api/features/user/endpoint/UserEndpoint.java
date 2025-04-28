package jp.co.company.space.api.features.user.endpoint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.booking.service.BookingService;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.user.dto.NewUserDto;
import jp.co.company.space.api.features.user.dto.UserDto;
import jp.co.company.space.api.features.user.exception.UserError;
import jp.co.company.space.api.features.user.exception.UserException;
import jp.co.company.space.api.features.user.input.UserCreationForm;
import jp.co.company.space.api.features.user.service.UserService;
import jp.co.company.space.api.shared.dto.DomainErrorDto;
import jp.co.company.space.api.shared.util.LogBuilder;
import jp.co.company.space.api.shared.util.ResponseFactory;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.logging.Logger;

import static jp.co.company.space.api.shared.openApi.Examples.ID_EXAMPLE;

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
     * Creates a new user and returns the newly created user information.
     *
     * @param form The form with the details for a new user.
     * @return A {@link NewUserDto} instance if the request was successful.
     */
    @POST
    @Operation(summary = "Creates a new user.", description = "Creates a new user and returns the newly created user information if the request was successful.")
    @RequestBody(name = "form", description = "A form with details for a new user.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserCreationForm.class)))
    @APIResponses({
            @APIResponse(description = "The newly created user's details.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = NewUserDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@RequestBody UserCreationForm form) {
        try {
            NewUserDto newUser = NewUserDto.create(userService.create(form));
            return Response.ok().entity(newUser).build();
        } catch (UserException exception) {
            LOGGER.warning(new LogBuilder(UserError.CREATE).withException(exception).build());
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns a user for the provided ID.
     *
     * @param id The ID to search with.
     * @return a {@link UserDto} instance.
     */
    @GET
    @Path("{id}")
    @Operation(summary = "Returns a user for the provided ID.", description = "Gets a user if the provided ID matches any.")
    @Parameter(name = "id", description = "The ID of a user.", example = ID_EXAMPLE)
    @APIResponses({
            @APIResponse(description = "A user.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDto.class))),
            @APIResponse(description = "The user was not found.", responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUserById(@PathParam("id") String id) {
        try {
            return userService.findById(id)
                    .map(user -> Response.ok().entity(UserDto.create(user)).build())
                    .orElse(ResponseFactory.notFound().entity(DomainErrorDto.create(UserError.FIND_BY_ID)).build());
        } catch (UserException exception) {
            LOGGER.warning(new LogBuilder(UserError.FIND_BY_ID).withException(exception).withProperty("id", id).build());
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }
}
