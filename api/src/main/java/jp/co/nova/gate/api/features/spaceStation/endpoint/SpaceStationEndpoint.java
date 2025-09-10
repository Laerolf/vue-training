package jp.co.nova.gate.api.features.spaceStation.endpoint;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.co.nova.gate.api.features.spaceStation.domain.SpaceStation;
import jp.co.nova.gate.api.features.spaceStation.dto.SpaceStationBasicDto;
import jp.co.nova.gate.api.features.spaceStation.dto.SpaceStationDto;
import jp.co.nova.gate.api.features.spaceStation.exception.SpaceStationError;
import jp.co.nova.gate.api.features.spaceStation.exception.SpaceStationException;
import jp.co.nova.gate.api.features.spaceStation.service.SpaceStationService;
import jp.co.nova.gate.api.shared.dto.DomainErrorDto;
import jp.co.nova.gate.api.shared.exception.DomainErrorDtoBuilder;
import jp.co.nova.gate.api.shared.util.LogBuilder;
import jp.co.nova.gate.api.shared.util.ResponseFactory;
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

import static jp.co.nova.gate.api.shared.openApi.Examples.SPACE_STATION_ORIGIN_ID_EXAMPLE;

/**
 * This class represents the REST endpoint for the {@link SpaceStation} topic.
 */
@ApplicationScoped
@Path("space-stations")
@Tag(name = "Space stations")
public class SpaceStationEndpoint {

    private static final Logger LOGGER = Logger.getLogger(SpaceStationEndpoint.class.getName());

    /**
     * The space station service.
     */
    @Inject
    private SpaceStationService spaceStationService;

    protected SpaceStationEndpoint() {
    }

    /**
     * Returns all existing space stations.
     *
     * @return A {@link List} of all existing {@link SpaceStationBasicDto}s.
     */
    @GET
    @PermitAll
    @Operation(summary = "Returns all space stations.", description = "Gives a list of all space stations.")
    @APIResponses({
            @APIResponse(description = "A list of all space stations.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = SpaceStationBasicDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSpaceStations() {
        try {
            List<SpaceStationBasicDto> spaceStations = spaceStationService.getAll().stream().map(SpaceStationBasicDto::create).toList();
            return Response.ok().entity(spaceStations).build();
        } catch (SpaceStationException exception) {
            LOGGER.warning(new LogBuilder(SpaceStationError.GET_ALL).withException(exception).build());
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns an optional space station for the provided ID.
     *
     * @param id The ID to search with.
     * @return an {@link Optional} {@link SpaceStationDto}.
     */
    @Path("{id}")
    @GET
    @PermitAll
    @Operation(summary = "Returns an optional space station for the provided ID.", description = "Gets a space station if the provided ID matches any.")
    @Parameter(name = "id", description = "The ID of a space station.", example = SPACE_STATION_ORIGIN_ID_EXAMPLE)
    @APIResponses({
            @APIResponse(description = "A space station", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = SpaceStationDto.class))),
            @APIResponse(description = "The space station was not found.", responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response findSpaceStationById(@PathParam("id") String id) {
        try {
            return spaceStationService.findById(id)
                    .map(spaceStation -> Response.ok().entity(SpaceStationDto.create(spaceStation)).build())
                    .orElse(ResponseFactory.notFound().entity(new DomainErrorDtoBuilder(SpaceStationError.FIND_BY_ID).withProperty("id", id).build()).build());
        } catch (SpaceStationException exception) {
            LOGGER.warning(new LogBuilder(SpaceStationError.GET_ALL).withException(exception).withProperty("id", id).build());
            return Response.serverError().entity(new DomainErrorDtoBuilder(exception).withProperty("id", id).build()).build();
        }
    }
}
