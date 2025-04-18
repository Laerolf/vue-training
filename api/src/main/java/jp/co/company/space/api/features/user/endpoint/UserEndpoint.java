package jp.co.company.space.api.features.user.endpoint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.booking.dto.BookingBasicDto;
import jp.co.company.space.api.features.booking.service.BookingService;
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
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
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

    @Inject
    private BookingService bookingService;

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
     * @param form The form with the details for a new user.
     * @return A {@link NewUserDto} instance if the request was successful.
     */
    @POST
    @Operation(summary = "Creates a new user.", description = "Creates a new user and returns the newly created user information if the request was successful.")
    @RequestBody(name = "form", description = "A form with details for a new user.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserCreationForm.class)))
    @APIResponse(description = "The newly created user's details.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = NewUserDto.class)))
    public Response createUser(@RequestBody UserCreationForm form) {
        try {
            NewUserDto newUser = NewUserDto.create(userService.create(form));
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

    /**
     * Returns a {@link List} of all {@link Booking} instances with a user matching the provided {@link User} ID.
     *
     * @param userId The user ID to search with.
     * @return A {@link List} of {@link Booking} instances.
     */
    @GET
    @Path("{userId}/bookings")
    @Operation(summary = "Returns all bookings with a user matching the provided user ID.", description = "Returns the booking with a user ID matching the provided ID.")
    @Parameter(name = "userId", description = "The ID of a user.", example = "00000000-0000-1000-8000-000000000003")
    @APIResponse(description = "A list of bookings.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = BookingBasicDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBookingsByUserId(@PathParam("userId") String userId) {
        try {
            List<BookingBasicDto> bookings = bookingService.getAllByUserId(userId).stream().map(BookingBasicDto::create).toList();
            return Response.ok().entity(bookings).build();
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }
}
