package jp.co.company.space.api.features.spaceShuttleModel.endpoint;

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
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceShuttleModel.service.SpaceShuttleModelService;

/**
 * This class represents the REST endpoint for the {@link SpaceShuttleModel} topic.
 */
@ApplicationScoped
@Path("space-shuttle-model")
@Tag(name = "Space shuttle models")
public class SpaceShuttleModelEndpoint {
    /**
     * The space shuttle model service.
     */
    @Inject
    private SpaceShuttleModelService spaceShuttleModelService;

    protected SpaceShuttleModelEndpoint() {}

    /**
     * Returns all existing space shuttle models.
     * 
     * @return A {@link List} of all existing {@link SpaceShuttleModel} instances.
     */
    @Path("")
    @GET
    @Operation(summary = "Returns all space shuttle models.", description = "Gives a list of all space shuttle models.")
    @APIResponse(description = "A JSON list of all space shuttle models.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = SpaceShuttleModel.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public List<SpaceShuttleModel> getAllSpaceShuttleModels() {
        return spaceShuttleModelService.getAll();
    }

    /**
     * Returns an optional space shuttle model for the provided ID.
     * 
     * @param id The ID to search with.
     * @return an {@link Optional} {@link SpaceShuttleModel} instance.
     */
    @Path("{id}")
    @GET
    @Operation(summary = "Returns an optional space shuttle model for the provided ID.", description = "Gets a space shuttle model if the provided ID matches any.")
    @APIResponse(description = "An optional space shuttle model", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = SpaceShuttleModel.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<SpaceShuttleModel> findSpaceShuttleModelById(@PathParam("id") String id) {
        return spaceShuttleModelService.findById(id);
    }
}