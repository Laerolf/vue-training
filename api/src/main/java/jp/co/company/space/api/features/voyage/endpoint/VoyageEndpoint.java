package jp.co.company.space.api.features.voyage.endpoint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.booking.dto.BookingDto;
import jp.co.company.space.api.features.booking.exception.BookingError;
import jp.co.company.space.api.features.booking.exception.BookingException;
import jp.co.company.space.api.features.booking.service.BookingService;
import jp.co.company.space.api.features.pod.domain.Pod;
import jp.co.company.space.api.features.pod.dto.PodDto;
import jp.co.company.space.api.features.pod.service.PodReservationService;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.features.voyage.dto.VoyageBasicDto;
import jp.co.company.space.api.features.voyage.dto.VoyageDto;
import jp.co.company.space.api.features.voyage.exception.VoyageError;
import jp.co.company.space.api.features.voyage.exception.VoyageException;
import jp.co.company.space.api.features.voyage.service.VoyageService;
import jp.co.company.space.api.shared.dto.DomainErrorDto;
import jp.co.company.space.api.shared.util.LogBuilder;
import jp.co.company.space.api.shared.util.ResponseFactory;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static jp.co.company.space.api.shared.openApi.Examples.*;

/**
 * This class represents the REST endpoint for the {@link Voyage} topic.
 */
@ApplicationScoped
@Path("voyages")
@Tag(name = "Voyages")
public class VoyageEndpoint {

    private static final Logger LOGGER = Logger.getLogger(VoyageEndpoint.class.getName());

    @Inject
    private VoyageService voyageService;

    @Inject
    private BookingService bookingService;

    @Inject
    private PodReservationService podReservationService;

    protected VoyageEndpoint() {
    }

    /**
     * Returns all existing space voyages.
     *
     * @return A {@link List} of all existing {@link Voyage} instances.
     */
    @GET
    @Operation(summary = "Returns all space voyages.", description = "Gives a list of all space voyages.")
    @APIResponses({
            @APIResponse(description = "A JSON list of all space voyages.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = VoyageBasicDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVoyages() {
        try {
            List<VoyageBasicDto> voyages = voyageService.getAll().stream().map(VoyageBasicDto::create).toList();
            return Response.ok().entity(voyages).build();
        } catch (VoyageException exception) {
            LOGGER.warning(new LogBuilder(VoyageError.GET_ALL).withException(exception).build());
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns an {@link Optional} {@link Voyage} matching the provided ID.
     *
     * @return An {@link Optional} {@link Voyage}.
     */
    @Path("{id}")
    @GET
    @Operation(summary = "Returns the space voyage for the provided ID if there is any.", description = "Returns the space voyage for the provided ID.")
    @Parameter(name = "id", description = "The ID of a space voyage.", example = VOYAGE_ID_EXAMPLE)
    @APIResponses({
            @APIResponse(description = "A space voyage.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = VoyageDto.class))),
            @APIResponse(description = "The voyage was not found.", responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response findVoyageById(@PathParam("id") String id) {
        try {
            return voyageService.findById(id)
                    .map(voyage -> Response.ok(VoyageDto.create(voyage)).build())
                    .orElse(ResponseFactory.notFound().entity(DomainErrorDto.create(VoyageError.FIND_BY_ID)).build());
        } catch (VoyageException exception) {
            LOGGER.warning(
                    new LogBuilder(VoyageError.FIND_BY_ID)
                            .withException(exception)
                            .withProperty("id", id)
                            .build()
            );
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns a {@link List} of all {@link Booking} instances with a user matching the provided {@link Voyage} ID.
     *
     * @param voyageId The user ID to search with.
     * @return A {@link List} of {@link Booking} instances.
     */
    @Path("{voyageId}/bookings")
    @GET
    @Operation(summary = "Returns all bookings with a voyage matching the provided voyage ID.", description = "Returns the booking with a voyage ID matching the provided ID.")
    @Parameter(name = "voyageId", description = "The ID of a voyage.", example = VOYAGE_ID_EXAMPLE)
    @APIResponses({
            @APIResponse(description = "A list of bookings.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = VoyageBasicDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBookingsByVoyageId(@PathParam("voyageId") String voyageId) {
        try {
            List<BookingDto> voyages = bookingService.getAllByVoyageId(voyageId).stream().map(BookingDto::create).toList();
            return Response.ok().entity(voyages).build();
        } catch (BookingException exception) {
            LOGGER.warning(
                    new LogBuilder(BookingError.FIND_BY_VOYAGE_ID)
                            .withException(exception)
                            .withProperty("voyageId", voyageId)
                            .build()
            );
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns a {@link List} of all {@link Voyage} instances with an origin space station matching the provided
     * {@link SpaceStation} ID.
     *
     * @return A {@link List} of {@link Voyage} instances.
     */
    @Path("/from/{originId}")
    @GET
    @Operation(summary = "Returns all space voyages starting from the provided space station ID.", description = "Returns all space voyages with an origin space station matching the provided space station ID.")
    @Parameter(name = "originId", description = "The ID of the origin space station.", example = SPACE_STATION_ORIGIN_ID_EXAMPLE)
    @APIResponses({
            @APIResponse(description = "A list of space voyages.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = VoyageBasicDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVoyagesByOriginId(@PathParam("originId") String originId) {
        try {
            List<VoyageBasicDto> voyages = voyageService.getAllFrom(originId).stream().map(VoyageBasicDto::create).toList();
            return Response.ok().entity(voyages).build();
        } catch (VoyageException exception) {
            LOGGER.warning(
                    new LogBuilder(VoyageError.GET_ALL_BY_ORIGIN_ID)
                            .withException(exception)
                            .withProperty("originId", originId)
                            .build()
            );
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns a {@link List} of all {@link Voyage} instances with a destination space station matching the provided
     * {@link SpaceStation} ID.
     *
     * @return A {@link List} of {@link Voyage} instances.
     */
    @Path("/to/{destinationId}")
    @GET
    @Operation(summary = "Returns all space voyages starting to the provided space station ID.", description = "Returns all space voyages with an destination space station matching the provided space station ID.")
    @Parameter(name = "destinationId", description = "The ID of the destination space station.", example = SPACE_STATION_DESTINATION_ID_EXAMPLE)
    @APIResponses({
            @APIResponse(description = "A list of space voyages.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = VoyageBasicDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVoyagesByDestinationId(@PathParam("destinationId") String destinationId) {
        try {
            List<VoyageBasicDto> voyages = voyageService.getAllTo(destinationId).stream().map(VoyageBasicDto::create).toList();
            return Response.ok().entity(voyages).build();
        } catch (VoyageException exception) {
            LOGGER.warning(
                    new LogBuilder(VoyageError.GET_ALL_BY_DESTINATION_ID)
                            .withException(exception)
                            .withProperty("destinationId", destinationId)
                            .build()
            );
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns a {@link List} of all {@link Voyage} instances with an origin space station matching the provided
     * {@link SpaceStation} ID.
     *
     * @return A {@link List} of {@link Voyage} instances.
     */
    @Path("/from/{originId}/to/{destinationId}")
    @GET
    @Operation(summary = "Returns all space voyages starting from the provided origin space station ID to provided destination space station ID.", description = "Returns all space voyages with an origin space station matching the provided space station ID and a destination space station matching the provided destination space station ID.")
    @Parameters({
            @Parameter(name = "originId", description = "The ID of the origin space station.", example = SPACE_STATION_ORIGIN_ID_EXAMPLE),
            @Parameter(name = "destinationId", description = "The ID of the destination space station.", example = SPACE_STATION_DESTINATION_ID_EXAMPLE)
    })
    @APIResponses({
            @APIResponse(description = "A list of space voyages.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = VoyageBasicDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVoyagesByOriginIdAndDestinationId(@PathParam("originId") String originId, @PathParam("destinationId") String destinationId) {
        try {
            List<VoyageBasicDto> voyages = voyageService.getAllFromTo(originId, destinationId).stream().map(VoyageBasicDto::create).toList();
            return Response.ok().entity(voyages).build();
        } catch (VoyageException exception) {
            LOGGER.warning(
                    new LogBuilder(VoyageError.GET_ALL_BY_ORIGIN_ID_AND_DESTINATION_ID)
                            .withException(exception)
                            .withProperty("originId", originId)
                            .withProperty("destinationId", destinationId)
                            .build()
            );
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns all pods for the voyage matching the provided ID.
     *
     * @param id The ID to search with for a voyage.
     * @return A {@link List} of all {@link PodDto} instances.
     */
    @GET
    @Path("{id}/pods")
    @Operation(summary = "Returns all pods for the voyage matching the provided ID.", description = "Gets all pods for the voyage matching the provided ID.")
    @Parameter(name = "id", description = "The ID of a space voyage.", example = VOYAGE_ID_EXAMPLE)
    @APIResponses({
            @APIResponse(description = "A list of pods", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PodDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVoyagePods(@PathParam("id") String id) {
        try {
            List<Pod> pods = voyageService.getAllPodsByVoyageId(id);
            return Response.ok().entity(pods.stream().map(PodDto::create).toList()).build();
        } catch (VoyageException exception) {
            LOGGER.warning(
                    new LogBuilder(VoyageError.GET_ALL_PODS_BY_VOYAGE_ID)
                            .withException(exception)
                            .withProperty("id", id)
                            .build()
            );
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }
}
