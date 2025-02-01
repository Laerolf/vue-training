package jp.co.company.space.api.features.spaceShuttle.endpoint;

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
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttle.service.SpaceShuttleService;

/**
 * This class represents the REST endpoint for the {@link SpaceStation} topic.
 */
@ApplicationScoped
@Path("space-shuttle")
@Tag(name = "Space shuttles")
public class SpaceShuttleEndpoint {

    /**
     * The space shuttle service.
     */
    @Inject
    private SpaceShuttleService spaceShuttleService;

    protected SpaceShuttleEndpoint() {}

    /**
     * Returns all existing space shuttles.
     * 
     * @return A {@link List} of all existing {@link SpaceShuttle} instances.
     */
    @Path("")
    @GET
    @Operation(summary = "Returns all space shuttles.", description = "Gives a list of all space shuttles.")
    @APIResponse(description = "A list of all space shuttles.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = SpaceShuttle.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public List<SpaceShuttle> getAllSpaceShuttles() {
        return spaceShuttleService.getAll();
    }

    /**
     * Returns an optional space shuttle for the provided ID.
     * 
     * @param id The ID to search with.
     * @return an {@link Optional} {@link SpaceShuttle} instance.
     */
    @GET
    @Path("{id}")
    @Operation(summary = "Returns an optional space shuttle for the provided ID.", description = "Gets a space shuttle if the provided ID matches any.")
    @APIResponse(description = "An optional space shuttle", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = SpaceShuttle.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<SpaceShuttle> findSpaceShuttleById(@PathParam("id") String id) {
        return spaceShuttleService.findById(id);
    }

}
