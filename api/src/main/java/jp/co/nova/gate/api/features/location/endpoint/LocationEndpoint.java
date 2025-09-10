package jp.co.nova.gate.api.features.location.endpoint;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.co.nova.gate.api.features.location.domain.Location;
import jp.co.nova.gate.api.features.location.dto.LocationDto;
import jp.co.nova.gate.api.features.location.exception.LocationError;
import jp.co.nova.gate.api.features.location.service.LocationService;
import jp.co.nova.gate.api.shared.dto.DomainErrorDto;
import jp.co.nova.gate.api.shared.exception.DomainErrorDtoBuilder;
import jp.co.nova.gate.api.shared.exception.DomainException;
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

import static jp.co.nova.gate.api.shared.openApi.Examples.LOCATION_ID_EXAMPLE;

/**
 * A class that handles the {@link Location} endpoint.
 */
@ApplicationScoped
@Path("locations")
@Tag(name = "Locations")
public class LocationEndpoint {

    private static final Logger LOGGER = Logger.getLogger(LocationEndpoint.class.getName());

    /**
     * The location service.
     */
    @Inject
    private LocationService locationService;

    protected LocationEndpoint() {
    }

    /**
     * Returns all existing locations.
     *
     * @return A {@link List} of all existing {@link LocationDto}s.
     */
    @GET
    @PermitAll
    @Operation(summary = "Returns all locations.", description = "Gives a list of all locations.")
    @APIResponses({
            @APIResponse(description = "A JSON list of all locations.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = LocationDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLocations() {
        try {
            List<LocationDto> locations = locationService.getAll().stream().map(LocationDto::create).toList();
            return Response.ok().entity(locations).build();
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(LocationError.GET_ALL).withException(exception).build());
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns an {@link Optional} {@link LocationDto} for the provided ID.
     *
     * @param id The ID to search with.
     * @return an {@link Optional} {@link LocationDto}.
     */
    @Path("{id}")
    @GET
    @PermitAll
    @Operation(summary = "Returns an optional location for the provided ID.", description = "Gets a location if the provided ID matches any.")
    @Parameter(name = "id", description = "The ID of a location.", example = LOCATION_ID_EXAMPLE)
    @APIResponses({
            @APIResponse(description = "An optional location", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = LocationDto.class))),
            @APIResponse(description = "The location was not found.", responseCode = "404", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response findLocationById(@PathParam("id") String id) {
        try {
            return locationService.findById(id)
                    .map(location -> Response.ok().entity(LocationDto.create(location)).build())
                    .orElse(ResponseFactory.notFound().entity(new DomainErrorDtoBuilder(LocationError.FIND_BY_ID).withProperty("id", id).build()).build());
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(LocationError.FIND_BY_ID).withException(exception).withProperty("id", id).build());
            return Response.serverError().entity(new DomainErrorDtoBuilder(exception).withProperty("id", id).build()).build();
        }
    }
}
