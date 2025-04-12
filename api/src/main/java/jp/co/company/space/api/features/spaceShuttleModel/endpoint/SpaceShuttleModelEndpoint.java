package jp.co.company.space.api.features.spaceShuttleModel.endpoint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttle.dto.SpaceShuttleBasicDto;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceShuttleModel.dto.SpaceShuttleModelDto;
import jp.co.company.space.api.features.spaceShuttleModel.service.SpaceShuttleModelService;
import jp.co.company.space.api.shared.util.ResponseFactory;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

/**
 * This class represents the REST endpoint for the {@link SpaceShuttleModel} topic.
 */
@ApplicationScoped
@Path("space-shuttle-models")
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
     * @return A {@link List} of all existing {@link SpaceShuttleModelDto} instances.
     */
    @GET
    @Operation(summary = "Returns all space shuttle models.", description = "Gives a list of all space shuttle models.")
    @APIResponse(description = "A JSON list of all space shuttle models.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = SpaceShuttleModelDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSpaceShuttleModels() {
        try {
            List<SpaceShuttleModelDto> spaceShuttleModels = spaceShuttleModelService.getAll().stream().map(SpaceShuttleModelDto::create).toList();
            return Response.ok().entity(spaceShuttleModels).build();
        } catch(Exception exception) {
            return Response.serverError().build();
        }
    }

    /**
     * Returns an optional space shuttle model for the provided ID.
     *
     * @param id The ID to search with.
     * @return an {@link Optional} {@link SpaceShuttleModelDto} instance.
     */
    @Path("{id}")
    @GET
    @Operation(summary = "Returns an optional space shuttle model for the provided ID.", description = "Gets a space shuttle model if the provided ID matches any.")
    @APIResponse(description = "An optional space shuttle model", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = SpaceShuttleModelDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response findSpaceShuttleModelById(@PathParam("id") String id) {
        try {
            return spaceShuttleModelService.findById(id)
                    .map(spaceShuttleModel -> Response.ok().entity(SpaceShuttleModelDto.create(spaceShuttleModel)).build())
                    .orElse(ResponseFactory.createNotFoundResponse());
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }

    /**
     * Returns all space shuttles with the space shuttle model matching the provided ID.
     *
     * @param id The ID to search with.
     * @return A {@link List} of {@link SpaceShuttle} instances.
     */
    @Path("{id}/space-shuttles")
    @GET
    @Operation(summary = "Returns all space shuttles with the space shuttle model matching the provided ID.", description = "Gets a list of space shuttles with the space shuttle model matching the provided ID.")
    @APIResponse(description = "A list of space shuttles", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = SpaceShuttleBasicDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSpaceShuttlesBySpaceShuttleModelId(@PathParam("id") String id) {
        try {
            List<SpaceShuttleBasicDto> spaceShuttles = spaceShuttleModelService.getAllSpaceShuttlesByModelId(id).stream().map(SpaceShuttleBasicDto::create).toList();
            return Response.ok().entity(spaceShuttles).build();
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }
}