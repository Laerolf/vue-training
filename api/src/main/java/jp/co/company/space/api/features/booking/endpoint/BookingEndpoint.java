package jp.co.company.space.api.features.booking.endpoint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.booking.dto.BookingDto;
import jp.co.company.space.api.features.booking.exception.BookingError;
import jp.co.company.space.api.features.booking.input.BookingCreationForm;
import jp.co.company.space.api.features.booking.service.BookingService;
import jp.co.company.space.api.shared.dto.DomainErrorDto;
import jp.co.company.space.api.shared.exception.DomainErrorDtoBuilder;
import jp.co.company.space.api.shared.exception.DomainException;
import jp.co.company.space.api.shared.util.LogBuilder;
import jp.co.company.space.api.shared.util.ResponseFactory;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.Optional;
import java.util.logging.Logger;

import static jp.co.company.space.api.shared.openApi.Examples.ID_EXAMPLE;

/**
 * This class represents the REST endpoint for the {@link Booking} topic.
 */
@ApplicationScoped
@Path("bookings")
@Tag(name = "Bookings")
public class BookingEndpoint {

    private static final Logger LOGGER = Logger.getLogger(BookingEndpoint.class.getName());

    @Inject
    private BookingService bookingService;

    protected BookingEndpoint() {
    }

    /**
     * Creates a new {@link Booking} instance and returns it as a {@link BookingDto} instance.
     *
     * @param context The context of the request.
     * @param form A form with details about the new {@link Booking} instance.
     * @return A new {@link BookingDto} instance
     */
    @POST
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Creates a new booking.", description = "Creates a new booking and returns it.")
    @RequestBody(name = "form", description = "A form with details for a new booking.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = BookingCreationForm.class)))
    @APIResponses({
            @APIResponse(description = "A new booking.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = BookingDto.class))),
            @APIResponse(description = "Booking rejected.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBooking(@RequestBody BookingCreationForm form, @Context SecurityContext context) {
        try {
            Booking newBooking = bookingService.create(context.getUserPrincipal(), form);
            return Response.ok(BookingDto.create(newBooking)).build();
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(BookingError.CREATE).withException(exception).build());
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns an {@link Optional} {@link Booking} matching the provided ID.
     *
     * @param context The context of the request.
     * @param id The ID to search with.
     * @return An {@link Optional} {@link Booking}.
     */
    @Path("{id}")
    @GET
    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Returns the booking for the provided ID if there is any.", description = "Returns the booking for the provided ID.")
    @Parameter(name = "id", description = "The ID of a booking.", example = ID_EXAMPLE)
    @APIResponses({
            @APIResponse(description = "A booking.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = BookingDto.class))),
            @APIResponse(description = "The booking was not found.", responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBookingById(@Context SecurityContext context, @PathParam("id") String id) {
        try {
            return bookingService.findById(id)
                    .filter(booking -> booking.getUser().getId().equals(context.getUserPrincipal().getName()))
                    .map(booking -> Response.ok(BookingDto.create(booking)).build())
                    .orElse(ResponseFactory.notFound().entity(new DomainErrorDtoBuilder(BookingError.FIND_BY_ID).withProperty("id", id).build()).build());
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(BookingError.FIND_BY_ID).withException(exception).withProperty("id", id).build());
            return Response.serverError().entity(new DomainErrorDtoBuilder(exception).withProperty("id", id).build()).build();
        }
    }
}
