package jp.co.company.space.api.features.spaceStation.endpoint;

import java.util.List;
import java.util.Optional;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import jp.co.company.space.api.features.spaceStation.service.SpaceStationService;

/**
 * This class represents the REST endpoint for the {@link SpaceStation} topic.
 */
@ApplicationScoped
@Path("space-station")
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
     * @return A {@link List} of all existing {@link SpaceStation} instances.
     */
    @Path("")
    @GET
    @Operation(summary = "Returns all space stations.", description = "Gives a list of all space stations.")
    @APIResponse(description = "A list of all space stations.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = SpaceStation.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public List<SpaceStation> getAllSpaceStations() {
        return spaceStationService.getAll();
    }

    /**
     * Returns an optional space station for the provided ID.
     * 
     * @param id The ID to search with.
     * @return an {@link Optional} {@link SpaceStation} instance.
     */
    @GET
    @Path("{id}")
    @Operation(summary = "Returns an optional space station for the provided ID.", description = "Gets a space station if the provided ID matches any.")
    @APIResponse(description = "An optional space station", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = SpaceStation.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<SpaceStation> findSpaceStationById(@PathParam("id") String id) {
        return spaceStationService.findById(id);
    }
}
