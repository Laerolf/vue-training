package jp.co.company.space.api.features.catalog.service;

import jakarta.enterprise.context.ApplicationScoped;
import jp.co.company.space.api.features.catalog.domain.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A service class handling the catalog topics.
 */
@ApplicationScoped
public class CatalogService {

    protected CatalogService() {
    }

    /**
     * Gets a {@link List} of all possible {@link MealPreference} values.
     *
     * @return A {@link List} of {@link MealPreference} values.
     */
    public List<CatalogItem> getAllMealPreferences() {
        return List.of(MealPreference.values());
    }

    /**
     * Gets a {@link List} of all possible {@link PackageType} values.
     *
     * @return A {@link List} of {@link PackageType}s.
     */
    public List<CatalogItem> getAllPackageTypes() {
        return List.of(PackageType.values());
    }

    /**
     * Gets a {@link List} of all possible {@link PodType} values.
     *
     * @return A {@link List} of {@link PodType}s.
     */
    public List<CatalogItem> getAllPodTypes() {
        return List.of(PodType.values());
    }

    /**
     * Gets a {@link List} of all possible {@link Nationality} values.
     *
     * @return A {@link List} of {@link Nationality}s.
     */
    public List<CatalogItem> getAllNationalities() {
        return Nationality.getAllNationalities();
    }

    /**
     * Gets a {@link List} of all possible {@link Gender} values.
     *
     * @return A {@link List} of {@link Gender}s.
     */
    public List<CatalogItem> getAllGenders() {
        return List.of(Gender.values());
    }

    /**
     * Gets a {@link Map} of all possible {@link CatalogItem} values, mapped by their {@link CatalogTopic} value.
     *
     * @return A {@link Map} of {@link CatalogItem} values.
     */
    public Map<CatalogTopic, List<CatalogItem>> getAllCatalogItems() {
        HashMap<CatalogTopic, List<CatalogItem>> catalogItems = new HashMap<>();

        catalogItems.put(CatalogTopic.GENDERS, getAllGenders());
        catalogItems.put(CatalogTopic.MEAL_PREFERENCES, getAllMealPreferences());
        catalogItems.put(CatalogTopic.NATIONALITIES, getAllNationalities());
        catalogItems.put(CatalogTopic.PACKAGE_TYPES, getAllPackageTypes());
        catalogItems.put(CatalogTopic.POD_TYPES, getAllPodTypes());

        return catalogItems;
    }
}
