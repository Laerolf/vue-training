package jp.co.company.space.api.features.catalog.service;

import jakarta.enterprise.context.ApplicationScoped;
import jp.co.company.space.api.features.catalog.domain.MealPreference;
import jp.co.company.space.api.features.catalog.domain.PackageType;

import java.util.List;

/**
 * A service class handling the catalog topics.
 */
@ApplicationScoped
public class CatalogService {

    protected CatalogService() {
    }

    /**
     * Creates a {@link List} of all existing {@link MealPreference} instances.
     * @return A {@link List} of {@link MealPreference} instances.
     */
    public List<MealPreference> getAllMealPreferences() {
        return List.of(MealPreference.values());
    }

    /**
     * Creates a {@link List} of all existing {@link PackageType} instances.
     * @return A {@link List} of {@link PackageType} instances.
     */
    public List<PackageType> getAllPackageTypes() {
        return List.of(PackageType.values());
    }

}
