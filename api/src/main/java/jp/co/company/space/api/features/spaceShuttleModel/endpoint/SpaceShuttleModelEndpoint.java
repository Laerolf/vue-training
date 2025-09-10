package jp.co.company.space.api.features.spaceShuttleModel.endpoint;

import jakarta.annotation.security.PermitAll;
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
import jp.co.company.space.api.features.spaceShuttle.exception.SpaceShuttleError;
import jp.co.company.space.api.features.spaceShuttle.exception.SpaceShuttleException;
import jp.co.company.space.api.features.spaceShuttle.service.SpaceShuttleService;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceShuttleModel.dto.SpaceShuttleModelDto;
import jp.co.company.space.api.features.spaceShuttleModel.exception.SpaceShuttleModelError;
import jp.co.company.space.api.features.spaceShuttleModel.exception.SpaceShuttleModelException;
import jp.co.company.space.api.features.spaceShuttleModel.service.SpaceShuttleModelService;
import jp.co.company.space.api.shared.dto.DomainErrorDto;
import jp.co.company.space.api.shared.exception.DomainErrorDtoBuilder;
import jp.co.company.space.api.shared.util.LogBuilder;
import jp.co.company.space.api.shared.util.ResponseFactory;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static jp.co.company.space.api.shared.openApi.Examples.SPACE_SHUTTLE_MODEL_ID_EXAMPLE;

/**
 * This class represents the REST endpoint for the {@link SpaceShuttleModel} topic.
 */
@ApplicationScoped
@Path("space-shuttle-models")
@Tag(name = "Space shuttle models")
public class SpaceShuttleModelEndpoint {

    private static final Logger LOGGER = Logger.getLogger(SpaceShuttleModelEndpoint.class.getName());

    @Inject
    private SpaceShuttleModelService spaceShuttleModelService;

    @Inject
    private SpaceShuttleService spaceShuttleService;

    protected SpaceShuttleModelEndpoint() {
    }

    /**
     * Returns all existing space shuttle models.
     *
     * @return A {@link List} of all existing {@link SpaceShuttleModelDto}s.
     */
    @GET
    @PermitAll
    @Operation(summary = "Returns all space shuttle models.", description = "Gives a list of all space shuttle models.")
    @APIResponses({
            @APIResponse(description = "A JSON list of all space shuttle models.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = SpaceShuttleModelDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSpaceShuttleModels() {
        try {
            List<SpaceShuttleModelDto> spaceShuttleModels = spaceShuttleModelService.getAll().stream().map(SpaceShuttleModelDto::create).toList();
            return Response.ok().entity(spaceShuttleModels).build();
        } catch (SpaceShuttleModelException exception) {
            LOGGER.warning(new LogBuilder(SpaceShuttleModelError.GET_ALL).withException(exception).build());
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns an optional space shuttle model for the provided ID.
     *
     * @param id The ID to search with.
     * @return an {@link Optional} {@link SpaceShuttleModelDto}.
     */
    @Path("{id}")
    @GET
    @PermitAll
    @Operation(summary = "Returns an optional space shuttle model for the provided ID.", description = "Gets a space shuttle model if the provided ID matches any.")
    @Parameter(name = "id", description = "The ID of a space shuttle model.", example = SPACE_SHUTTLE_MODEL_ID_EXAMPLE)
    @APIResponses({
            @APIResponse(description = "A space shuttle model", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = SpaceShuttleModelDto.class))),
            @APIResponse(description = "The space shuttle model was not found.", responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response findSpaceShuttleModelById(@PathParam("id") String id) {
        try {
            return spaceShuttleModelService.findById(id)
                    .map(spaceShuttleModel -> Response.ok().entity(SpaceShuttleModelDto.create(spaceShuttleModel)).build())
                    .orElse(ResponseFactory.notFound().entity(new DomainErrorDtoBuilder(SpaceShuttleModelError.FIND_BY_ID).withProperty("id", id).build()).build());
        } catch (SpaceShuttleModelException exception) {
            LOGGER.warning(new LogBuilder(SpaceShuttleModelError.FIND_BY_ID).withException(exception).withProperty("id", id).build());
            return Response.serverError().entity(new DomainErrorDtoBuilder(exception).withProperty("id", id).build()).build();
        }
    }

    /**
     * Returns all space shuttles with the space shuttle model matching the provided ID.
     *
     * @param modelId The ID to search with.
     * @return A {@link List} of {@link SpaceShuttle}s.
     */
    @Path("{modelId}/space-shuttles")
    @GET
    @PermitAll
    @Operation(summary = "Returns all space shuttles with the space shuttle model matching the provided ID.", description = "Gets a list of space shuttles with the space shuttle model matching the provided ID.")
    @Parameter(name = "modelId", description = "The ID of a space shuttle model.", example = SPACE_SHUTTLE_MODEL_ID_EXAMPLE)
    @APIResponses({
            @APIResponse(description = "A list of space shuttles.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = SpaceShuttleBasicDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSpaceShuttlesBySpaceShuttleModelId(@PathParam("modelId") String modelId) {
        try {
            List<SpaceShuttleBasicDto> spaceShuttles = spaceShuttleService.getAllSpaceShuttlesByModelId(modelId).stream().map(SpaceShuttleBasicDto::create).toList();
            return Response.ok().entity(spaceShuttles).build();
        } catch (SpaceShuttleException exception) {
            LOGGER.warning(new LogBuilder(SpaceShuttleError.GET_ALL_BY_MODEL_ID).withException(exception).withProperty("modelId", modelId).build());
            return Response.serverError().entity(new DomainErrorDtoBuilder(exception).withProperty("modelId", modelId).build()).build();
        }
    }
}