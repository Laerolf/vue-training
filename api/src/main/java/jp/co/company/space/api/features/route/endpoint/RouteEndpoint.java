package jp.co.company.space.api.features.route.endpoint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.route.dto.RouteBasicDto;
import jp.co.company.space.api.features.route.dto.RouteDto;
import jp.co.company.space.api.features.route.service.RouteService;
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
 * This class represents the REST endpoint for the {@link Route} topic.
 */
@ApplicationScoped
@Path("routes")
@Tag(name = "Routes")
public class RouteEndpoint {

    @Inject
    private RouteService routeService;

    protected RouteEndpoint() {}

    /**
     * Returns all existing routes.
     *
     * @return A {@link List} of all existing {@link RouteBasicDto} instances.
     */
    @GET
    @Operation(summary = "Returns all routes.", description = "Gives a list of all routes.")
    @APIResponse(description = "A JSON list of all routes.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = RouteBasicDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRoutes() {
        try {
            List<RouteBasicDto> allRoutes = routeService.getAll().stream().map(RouteBasicDto::create).toList();
            return Response.ok().entity(allRoutes).build();
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }

    /**
     * Returns an {@link Optional} {@link RouteDto} for the provided ID.
     *
     * @param id The ID to search with.
     * @return an {@link Optional} {@link RouteDto} instance.
     */
    @Path("{id}")
    @GET
    @Operation(summary = "Returns an optional route for the provided ID.", description = "Gets a route if the provided ID matches any.")
    @APIResponse(description = "An optional route", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RouteDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response findRouteById(@PathParam("id") String id) {
        try {
            return routeService.findById(id)
                    .map(route -> Response.ok().entity(RouteDto.create(route)).build())
                    .orElse(ResponseFactory.createNotFoundResponse());
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }
}
