package jp.co.nova.gate.api.features.route.endpoint;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.co.nova.gate.api.features.route.domain.Route;
import jp.co.nova.gate.api.features.route.dto.RouteBasicDto;
import jp.co.nova.gate.api.features.route.dto.RouteDto;
import jp.co.nova.gate.api.features.route.exception.RouteError;
import jp.co.nova.gate.api.features.route.service.RouteService;
import jp.co.nova.gate.api.shared.dto.DomainErrorDto;
import jp.co.nova.gate.api.shared.exception.DomainErrorDtoBuilder;
import jp.co.nova.gate.api.shared.exception.DomainException;
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

import static jp.co.nova.gate.api.shared.openApi.Examples.ROUTE_ID_EXAMPLE;

/**
 * This class represents the REST endpoint for the {@link Route} topic.
 */
@ApplicationScoped
@Path("routes")
@Tag(name = "Routes")
public class RouteEndpoint {

    @Inject
    private RouteService routeService;

    protected RouteEndpoint() {
    }

    /**
     * Returns all existing routes.
     *
     * @return A {@link List} of all existing {@link RouteBasicDto}s.
     */
    @GET
    @PermitAll
    @Operation(summary = "Returns all routes.", description = "Gives a list of all routes.")
    @APIResponses({
            @APIResponse(description = "A JSON list of all routes.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = RouteBasicDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRoutes() {
        try {
            List<RouteBasicDto> allRoutes = routeService.getAll().stream().map(RouteBasicDto::create).toList();
            return Response.ok().entity(allRoutes).build();
        } catch (DomainException exception) {
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns an {@link Optional} {@link RouteDto} for the provided ID.
     *
     * @param id The ID to search with.
     * @return an {@link Optional} {@link RouteDto}.
     */
    @Path("{id}")
    @GET
    @PermitAll
    @Operation(summary = "Returns an optional route for the provided ID.", description = "Gets a route if the provided ID matches any.")
    @Parameter(name = "id", description = "The ID of a route.", example = ROUTE_ID_EXAMPLE)
    @APIResponses({
            @APIResponse(description = "An optional route", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RouteDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response findRouteById(@PathParam("id") String id) {
        try {
            return routeService.findById(id)
                    .map(route -> Response.ok().entity(RouteDto.create(route)).build())
                    .orElse(ResponseFactory.notFound().entity(new DomainErrorDtoBuilder(RouteError.FIND_BY_ID).withProperty("id", id).build()).build());
        } catch (DomainException exception) {
            return Response.serverError().entity(new DomainErrorDtoBuilder(exception).withProperty("id", id).build()).build();
        }
    }
}
