package jp.co.company.space.api.features.route.endpoint;

import java.util.List;

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
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.route.service.RouteService;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;

/**
 * This class represents the REST endpoint for the {@link SpaceStation} topic.
 */
@ApplicationScoped
@Path("route")
@Tag(name = "Routes")
public class RouteEndpoint {

    @Inject
    private RouteService routeService;

    protected RouteEndpoint() {}

    /**
     * Returns all existing routes.
     * 
     * @return A {@link List} of all existing {@link Route} instances.
     */
    @Path("")
    @GET
    @Operation(summary = "Returns all routes.", description = "Gives a list of all routes.")
    @APIResponse(description = "A JSON list of all routes.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = Route.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public List<Route> getAllRoutes() {
        return routeService.getAll();
    }
}
