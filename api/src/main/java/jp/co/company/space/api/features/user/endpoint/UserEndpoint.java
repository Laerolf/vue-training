package jp.co.company.space.api.features.user.endpoint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.user.dto.NewUserDto;
import jp.co.company.space.api.features.user.dto.UserDto;
import jp.co.company.space.api.features.user.input.UserCreationForm;
import jp.co.company.space.api.features.user.service.UserService;
import jp.co.company.space.api.shared.util.ResponseFactory;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

/**
 * This class represents the REST endpoint for the {@link User} topic.
 */
@ApplicationScoped
@Path("users")
@Tag(name = "Users")
public class UserEndpoint {

    @Inject
    private UserService userService;

    protected UserEndpoint() {
    }

    /**
     * Returns all existing users.
     *
     * @return A {@link List} of all existing {@link UserDto} instances.
     */
    @GET
    @Operation(summary = "Returns all users", description = "Gives a list of all existing users.")
    @APIResponse(description = "A list of all existing users.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = UserDto.class)))
    public Response getAllUsers() {
        try {
            List<UserDto> allUsers = userService.getAll().stream().map(UserDto::create).toList();
            return Response.ok().entity(allUsers).build();
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }

    /**
     * Creates a new user and returns the newly created user information.
     *
     * @return A {@link NewUserDto} instance if the request was successful.
     */
    @POST
    @Operation(summary = "Creates a new user.", description = "Creates a new user and returns the newly created user information if the request was successful.")
    @APIResponse(description = "The newly created user's details.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = NewUserDto.class)))
    public Response createUser(@RequestBody UserCreationForm form) {
        try {
            NewUserDto newUser = userService.create(form);
            return Response.ok().entity(newUser).build();
        } catch (Exception exception) {
            return Response.serverError().build();
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
    @APIResponse(description = "A user.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserDto.class)))
    public Response findUserById(@PathParam("id") String id) {
        try {
            return userService.findById(id)
                    .map(user -> Response.ok().entity(UserDto.create(user)).build())
                    .orElse(ResponseFactory.createNotFoundResponse());
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }
}
