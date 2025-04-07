package jp.co.company.space.api.features.voyage.endpoint;

import java.util.List;
import java.util.Optional;

import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.features.voyage.dto.VoyageDto;
import jp.co.company.space.api.features.voyage.dto.VoyageBasicDto;
import jp.co.company.space.api.features.voyage.service.VoyageService;

/**
 * This class represents the REST endpoint for the {@link Voyage} topic.
 */
@ApplicationScoped
@Path("voyages")
@Tag(name = "Voyages")
public class VoyageEndpoint {
    /**
     * The space shuttle model service.
     */
    @Inject
    private VoyageService voyageService;

    protected VoyageEndpoint() {}

    /**
     * Returns all existing space voyages.
     * 
     * @return A {@link List} of all existing {@link Voyage} instances.
     */
    @GET
    @Operation(summary = "Returns all space voyages.", description = "Gives a list of all space voyages.")
    @APIResponse(description = "A JSON list of all space voyages.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = VoyageBasicDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVoyages() {
        try {
            List<VoyageBasicDto> voyages = voyageService.getAll().stream().map(VoyageBasicDto::create).toList();
            return Response.ok().entity(voyages).build();
        } catch (Exception exception) {
            return Response.serverError().build();
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
    @Parameter(name = "id", description = "The ID of a space voyage.", example = "5f485136-20a8-41f3-9073-4156d32c9c36")
    @APIResponse(description = "A space voyage.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = VoyageDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response findVoyageById(@PathParam("id") String id) {
        try {
            return voyageService.findById(id)
                    .map(voyage -> Response.ok(VoyageDto.create(voyage)).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception exception) {
            return Response.serverError().build();
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
    @Parameter(name = "originId", description = "The ID of the origin space station.", example = "00000000-0000-1000-8000-000000000003")
    @APIResponse(description = "A list of space voyages.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = VoyageBasicDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVoyagesFrom(@PathParam("originId") String originId) {
        try {
            List<VoyageBasicDto> voyages = voyageService.getAllFrom(originId).stream().map(VoyageBasicDto::create).toList();
            return Response.ok().entity(voyages).build();
        } catch (Exception exception) {
            return Response.serverError().build();
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
    @Parameter(name = "destinationId", description = "The ID of the destination space station.", example = "00000000-0000-1000-8000-000000000012")
    @APIResponse(description = "A list of space voyages.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = VoyageBasicDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVoyagesTo(@PathParam("destinationId") String destinationId) {
        try {
            List<VoyageBasicDto> voyages = voyageService.getAllTo(destinationId).stream().map(VoyageBasicDto::create).toList();
            return Response.ok().entity(voyages).build();
        } catch (Exception exception) {
            return Response.serverError().build();
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
    @Parameter(name = "originId", description = "The ID of the origin space station.", example = "00000000-0000-1000-8000-000000000003")
    @Parameter(name = "destinationId", description = "The ID of the destination space station.", example = "00000000-0000-1000-8000-000000000012")
    @APIResponse(description = "A list of space voyages.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = VoyageBasicDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVoyagesFromTo(@PathParam("originId") String originId, @PathParam("destinationId") String destinationId) {
        try {
            List<VoyageBasicDto> voyages = voyageService.getAllFromTo(originId, destinationId).stream().map(VoyageBasicDto::create).toList();
            return Response.ok().entity(voyages).build();
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }
}
