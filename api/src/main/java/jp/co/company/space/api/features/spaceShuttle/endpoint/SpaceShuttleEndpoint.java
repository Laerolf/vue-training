package jp.co.company.space.api.features.spaceShuttle.endpoint;

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
import jp.co.company.space.api.features.spaceShuttle.dto.SpaceShuttleDto;
import jp.co.company.space.api.features.spaceShuttle.exception.SpaceShuttleError;
import jp.co.company.space.api.features.spaceShuttle.service.SpaceShuttleService;
import jp.co.company.space.api.shared.dto.DomainErrorDto;
import jp.co.company.space.api.shared.exception.DomainErrorDtoBuilder;
import jp.co.company.space.api.shared.exception.DomainException;
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

import static jp.co.company.space.api.shared.openApi.Examples.SPACE_SHUTTLE_ID_EXAMPLE;

/**
 * This class represents the REST endpoint for the {@link SpaceShuttle} topic.
 */
@ApplicationScoped
@Path("space-shuttles")
@Tag(name = "Space shuttles")
public class SpaceShuttleEndpoint {

    private static final Logger LOGGER = Logger.getLogger(SpaceShuttleEndpoint.class.getName());

    /**
     * The space shuttle service.
     */
    @Inject
    private SpaceShuttleService spaceShuttleService;

    protected SpaceShuttleEndpoint() {
    }

    /**
     * Returns all existing space shuttles.
     *
     * @return A {@link List} of all existing {@link SpaceShuttleBasicDto} instances.
     */
    @GET
    @Operation(summary = "Returns all space shuttles.", description = "Gives a list of all space shuttles.")
    @APIResponses({
            @APIResponse(description = "A list of all space shuttles.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = SpaceShuttleBasicDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSpaceShuttles() {
        try {
            List<SpaceShuttleBasicDto> spaceShuttles = spaceShuttleService.getAll().stream().map(SpaceShuttleBasicDto::create).toList();
            return Response.ok().entity(spaceShuttles).build();
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(SpaceShuttleError.GET_ALL).withException(exception).build());
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns an optional space shuttle for the provided ID.
     *
     * @param id The ID to search with.
     * @return an {@link Optional} {@link SpaceShuttleDto} instance.
     */
    @GET
    @Path("{id}")
    @Operation(summary = "Returns an optional space shuttle for the provided ID.", description = "Gets a space shuttle if the provided ID matches any.")
    @Parameter(name = "id", description = "The ID of a space shuttle.", example = SPACE_SHUTTLE_ID_EXAMPLE)
    @APIResponses({
            @APIResponse(description = "An optional space shuttle", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = SpaceShuttleDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response findSpaceShuttleById(@PathParam("id") String id) {
        try {
            return spaceShuttleService.findById(id)
                    .map(spaceShuttle -> Response.ok().entity(SpaceShuttleDto.create(spaceShuttle)).build())
                    .orElse(ResponseFactory.notFound().entity(new DomainErrorDtoBuilder(SpaceShuttleError.FIND_BY_ID).withProperty("id", id).build()).build());
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(SpaceShuttleError.FIND_BY_ID).withException(exception).withProperty("id", id).build());
            return Response.serverError().entity(new DomainErrorDtoBuilder(exception).withProperty("id", id).build()).build();
        }
    }
}
