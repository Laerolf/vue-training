package jp.co.company.space.api.features.location.endpoint;

import java.util.List;
import java.util.Optional;

import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.location.dto.LocationDto;
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
import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.location.service.LocationService;

/**
 * A class that handles the {@link Location} endpoint.
 */
@ApplicationScoped
@Path("locations")
@Tag(name = "Locations")
public class LocationEndpoint {

    /**
     * The location service.
     */
    @Inject
    private LocationService locationService;

    protected LocationEndpoint() {}

    /**
     * Returns all existing locations.
     * 
     * @return A {@link List} of all existing {@link Location} instances.
     */
    @GET
    @Operation(summary = "Returns all locations.", description = "Gives a list of all locations.")
    @APIResponse(description = "A JSON list of all locations.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = LocationDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLocations() {
        try {
            List<LocationDto> locations = locationService.getAll().stream().map(LocationDto::create).toList();
            return Response.ok().entity(locations).build();
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }

    /**
     * Returns an optional location for the provided ID.
     * 
     * @param id The ID to search with.
     * @return an {@link Optional} {@link Location} instance.
     */
    @Path("{id}")
    @GET
    @Operation(summary = "Returns an optional location for the provided ID.", description = "Gets a location if the provided ID matches any.")
    @APIResponse(description = "An optional location", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = LocationDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response findLocationById(@PathParam("id") String id) {
        try {
            return Response.ok().entity(locationService.findById(id).map(LocationDto::create)).build();
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }
}
