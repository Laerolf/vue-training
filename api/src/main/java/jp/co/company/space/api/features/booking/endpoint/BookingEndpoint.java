package jp.co.company.space.api.features.booking.endpoint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.booking.dto.BookingDto;
import jp.co.company.space.api.features.booking.input.BookingCreationForm;
import jp.co.company.space.api.features.booking.service.BookingService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.Optional;

import static jp.co.company.space.api.shared.openApi.examples.ID_EXAMPLE;

/**
 * This class represents the REST endpoint for the {@link Booking} topic.
 */
@ApplicationScoped
@Path("bookings")
@Tag(name = "Bookings")
public class BookingEndpoint {

    @Inject
    private BookingService bookingService;

    protected BookingEndpoint() {
    }

    /**
     * Creates a new {@link Booking} instance and returns it as a {@link BookingDto} instance.
     *
     * @param form A form with details about the new {@link Booking} instance.
     * @return A new {@link BookingDto} instance
     */
    @POST
    @Operation(summary = "Creates a new booking.", description = "Creates a new booking and returns it.")
    @RequestBody(name = "form", description = "A form with details for a new booking.", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = BookingCreationForm.class)))
    @APIResponse(description = "A new booking.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = BookingDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBooking(@RequestBody BookingCreationForm form) {
        try {
            Booking newBooking = bookingService.create(form);
            return Response.ok(BookingDto.create(newBooking)).build();
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }

    /**
     * Returns an {@link Optional} {@link Booking} matching the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link Booking}.
     */
    @Path("{id}")
    @GET
    @Operation(summary = "Returns the booking for the provided ID if there is any.", description = "Returns the booking for the provided ID.")
    @Parameter(name = "id", description = "The ID of a booking.", example = ID_EXAMPLE)
    @APIResponse(description = "A booking.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = BookingDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBookingById(@PathParam("id") String id) {
        try {
            return bookingService.findById(id)
                    .map(booking -> Response.ok(BookingDto.create(booking)).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }
}
