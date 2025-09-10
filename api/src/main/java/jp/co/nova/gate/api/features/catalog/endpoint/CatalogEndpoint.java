package jp.co.nova.gate.api.features.catalog.endpoint;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jp.co.nova.gate.api.features.catalog.domain.CatalogItem;
import jp.co.nova.gate.api.features.catalog.dto.*;
import jp.co.nova.gate.api.features.catalog.service.CatalogService;
import jp.co.nova.gate.api.shared.dto.DomainErrorDto;
import jp.co.nova.gate.api.shared.exception.DomainException;
import jp.co.nova.gate.api.shared.util.LogBuilder;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Represents the REST endpoint for the catalog topics.
 */
@ApplicationScoped
@Path("catalog")
@PermitAll
@Tag(name = "Catalog")
public class CatalogEndpoint {

    private static final Logger LOGGER = Logger.getLogger(CatalogEndpoint.class.getName());

    @Inject
    private CatalogService catalogService;

    protected CatalogEndpoint() {
    }

    /**
     * Returns all catalog items.
     *
     * @return A {@link Map} of {@link CatalogItem} values.
     */
    @GET
    @Operation(summary = "Returns all catalog items.", description = "Gives a map of all catalog items, mapped by their catalog topic.")
    @APIResponses({
            @APIResponse(description = "A JSON map of all catalog items.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.OBJECT))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCatalogItems() {
        try {
            Map<String, List<CatalogItemDto>> catalogItemMap = catalogService.getAllCatalogItems().entrySet().stream()
                    .collect(Collectors.toMap(
                            catalogTopicListEntry -> catalogTopicListEntry.getKey().getLabel(),
                            entry -> entry.getValue().stream()
                                    .map(CatalogItemDto::fromCatalogItem)
                                    .toList()
                    ));

            return Response.ok().entity(catalogItemMap).build();
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder("Failed to get all catalog items.").withException(exception).build());
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns all possible genders.
     *
     * @return A {@link List} of all possible {@link GenderDto} values.
     */
    @Path("genders")
    @GET
    @Operation(summary = "Returns all genders.", description = "Gives a list of all genders.")
    @APIResponses({
            @APIResponse(description = "A JSON list of all genders.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = GenderDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGenders() {
        try {
            List<GenderDto> allGenders = catalogService.getAllGenders().stream().map(GenderDto::fromCatalogItem).toList();
            return Response.ok().entity(allGenders).build();
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder("Failed to get all genders.").withException(exception).build());
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns all possible meal preferences.
     *
     * @return A {@link List} of all possible {@link MealPreferenceDto} values.
     */
    @Path("meal-preferences")
    @GET
    @Operation(summary = "Returns all meal preferences.", description = "Gives a list of all meal preferences.")
    @APIResponses({
            @APIResponse(description = "A JSON list of all meal preferences.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = MealPreferenceDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMealPreferences() {
        try {
            List<MealPreferenceDto> allMealPreferences = catalogService.getAllMealPreferences().stream().map(MealPreferenceDto::fromCatalogItem).toList();
            return Response.ok().entity(allMealPreferences).build();
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder("Failed to get all meal preferences.").withException(exception).build());
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns all possible nationalities.
     *
     * @return A {@link List} of all possible {@link NationalityDto} values.
     */
    @Path("nationalities")
    @GET
    @Operation(summary = "Returns all nationalities.", description = "Gives a list of all nationalities.")
    @APIResponses({
            @APIResponse(description = "A JSON list of all nationalities.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = NationalityDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNationalities() {
        try {
            List<NationalityDto> allNationalities = catalogService.getAllNationalities().stream().map(NationalityDto::fromCatalogItem).toList();
            return Response.ok().entity(allNationalities).build();
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder("Failed to get all nationalities.").withException(exception).build());
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns all possible package types.
     *
     * @return A {@link List} of all possible {@link PackageTypeDto} values.
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
            List<PackageTypeDto> allRoutes = catalogService.getAllPackageTypes().stream().map(PackageTypeDto::fromCatalogItem).toList();
            return Response.ok().entity(allRoutes).build();
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder("Failed to get all package types").withException(exception).build());
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }

    /**
     * Returns all possible pod types.
     *
     * @return A {@link List} of all possible {@link PodTypeDto} values.
     */
    @Path("pod-types")
    @GET
    @Operation(summary = "Returns all pod types.", description = "Gives a list of all pod types.")
    @APIResponses({
            @APIResponse(description = "A JSON list of all pod types.", responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(type = SchemaType.ARRAY, implementation = PodTypeDto.class))),
            @APIResponse(description = "Something went wrong.", responseCode = "500", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DomainErrorDto.class)))
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPodTypes() {
        try {
            List<PodTypeDto> allPodTypes = catalogService.getAllPodTypes().stream().map(PodTypeDto::fromCatalogItem).toList();
            return Response.ok().entity(allPodTypes).build();
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder("Failed to get all pod types.").withException(exception).build());
            return Response.serverError().entity(DomainErrorDto.create(exception)).build();
        }
    }
}
