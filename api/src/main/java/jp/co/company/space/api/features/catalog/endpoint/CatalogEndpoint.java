package jp.co.company.space.api.features.catalog.endpoint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.catalog.dto.MealPreferenceDto;
import jp.co.company.space.api.features.catalog.dto.PackageTypeDto;
import jp.co.company.space.api.features.catalog.service.CatalogService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

/**
 * This class represents the REST endpoint for the catalog topics.
 */
@ApplicationScoped
@Path("catalog")
@Tag(name = "Catalog")
public class CatalogEndpoint {

    @Inject
    private CatalogService catalogService;

    protected CatalogEndpoint() {}

    /**
     * Returns all existing meal preferences.
     *
     * @return A {@link List} of all existing {@link MealPreferenceDto} instances.
     */
    @Path("meal-preferences")
    @GET
    @Operation(summary = "Returns all meal preferences.", description = "Gives a list of all meal preferences.")
    @APIResponse(description = "A JSON list of all meal preferences.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = MealPreferenceDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMealPreferences() {
        try {
            List<MealPreferenceDto> allRoutes = catalogService.getAllMealPreferences().stream().map(MealPreferenceDto::create).toList();
            return Response.ok().entity(allRoutes).build();
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }

    /**
     * Returns all existing package types.
     *
     * @return A {@link List} of all existing {@link PackageTypeDto} instances.
     */
    @Path("package-types")
    @GET
    @Operation(summary = "Returns all package types.", description = "Gives a list of all package types.")
    @APIResponse(description = "A JSON list of all package types.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = PackageTypeDto.class)))
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPackageTypes() {
        try {
            List<PackageTypeDto> allRoutes = catalogService.getAllPackageTypes().stream().map(PackageTypeDto::create).toList();
            return Response.ok().entity(allRoutes).build();
        } catch (Exception exception) {
            return Response.serverError().build();
        }
    }
}
