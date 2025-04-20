package jp.co.company.space.api.features.spaceStation.endpoint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import jp.co.company.space.api.features.spaceStation.dto.SpaceStationBasicDto;
import jp.co.company.space.api.features.spaceStation.dto.SpaceStationDto;
import jp.co.company.space.api.features.spaceStation.service.SpaceStationService;
import jp.co.company.space.api.shared.util.ResponseFactory;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

import static jp.co.company.space.api.shared.openApi.examples.SPACE_STATION_ORIGIN_ID_EXAMPLE;

/**
 * This class represents the REST endpoint for the {@link SpaceStation} topic.
 */
@ApplicationScoped
@Path("space-stations")
@Tag(name = "Space stations")
public class SpaceStationEndpoint {
    /**
     * The space station service.
     */
    @Inject
    private SpaceStationService spaceStationService;

    protected SpaceStationEndpoint() {}

    /**
     * Returns all existing space stations.
     *
     * @return A {@link List} of all existing {@link SpaceStationBasicDto} instances.
     */
    @GET
    @Operation(summary = "Returns all space stations.", description = "Gives a list of all space stations.")
    @APIResponse(description = "A list of all space stations.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = SpaceStationBasicDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSpaceStations() {
        try {
            List<SpaceStationBasicDto> spaceStations = spaceStationService.getAll().stream().map(SpaceStationBasicDto::create).toList();
            return Response.ok().entity(spaceStations).build();
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }

    /**
     * Returns an optional space station for the provided ID.
     *
     * @param id The ID to search with.
     * @return an {@link Optional} {@link SpaceStationDto} instance.
     */
    @GET
    @Path("{id}")
    @Operation(summary = "Returns an optional space station for the provided ID.", description = "Gets a space station if the provided ID matches any.")
    @Parameter(name = "id", description = "The ID of a space station.", example = SPACE_STATION_ORIGIN_ID_EXAMPLE)
    @APIResponse(description = "An optional space station", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = SpaceStationDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response findSpaceStationById(@PathParam("id") String id) {
        try {
            return spaceStationService.findById(id)
                    .map(spaceStation -> Response.ok().entity(SpaceStationDto.create(spaceStation)).build())
                    .orElse(ResponseFactory.createNotFoundResponse());
        } catch(Exception exception) {
            return Response.serverError().build();
        }
    }
}
