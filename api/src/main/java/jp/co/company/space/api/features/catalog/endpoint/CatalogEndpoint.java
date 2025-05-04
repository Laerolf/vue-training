package jp.co.company.space.api.features.catalog.endpoint;

import jakarta.annotation.security.PermitAll;
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
import jp.co.company.space.api.shared.dto.DomainErrorDto;
import jp.co.company.space.api.shared.exception.DomainException;
import jp.co.company.space.api.shared.util.LogBuilder;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.logging.Logger;

/**
 * This class represents the REST endpoint for the catalog topics.
 */
@ApplicationScoped
@Path("catalog")
@Tag(name = "Catalog")
public class CatalogEndpoint {

    private static final Logger LOGGER = Logger.getLogger(CatalogEndpoint.class.getName());

    @Inject
    private CatalogService catalogService;

    protected CatalogEndpoint() {
    }

    /**
     * Returns all existing meal preferences.
     *
     * @return A {@link List} of all existing {@link MealPreferenceDto} instances.
     */
    @Path("meal-preferences")
    @GET
    @PermitAll
    @Operation(summary = "Returns all meal preferences.", description = "Gives a list of all meal preferences.")
    @APIResponses({
            @APIResponse(description = "A JSON list of all meal preferences.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = MealPreferenceDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMealPreferences() {
        try {
            List<MealPreferenceDto> allRoutes = catalogService.getAllMealPreferences().stream().map(MealPreferenceDto::create).toList();
            return Response.ok().entity(allRoutes).build();
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder("Failed to get all meal preferences").withException(exception).build());
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
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
    @APIResponses({
            @APIResponse(description = "A JSON list of all package types.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = PackageTypeDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPackageTypes() {
        try {
            List<PackageTypeDto> allRoutes = catalogService.getAllPackageTypes().stream().map(PackageTypeDto::create).toList();
            return Response.ok().entity(allRoutes).build();
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder("Failed to get all package types").withException(exception).build());
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }
}
