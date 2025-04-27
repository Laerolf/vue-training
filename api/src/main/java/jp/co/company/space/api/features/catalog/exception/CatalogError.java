package jp.co.company.space.api.features.catalog.exception;

import jp.co.company.space.api.shared.exception.DomainError;

/**
 * An enum with catalog error messages.
 */
public enum CatalogError implements DomainError {

    MEAL_PREFERENCE_MISSING("mealPreference.missing", "The meal preference is missing."),
    MEAL_PREFERENCE_MISSING_KEY("mealPreference.missingKey", "The key of the meal preference is missing."),
    MEAL_PREFERENCE_MISSING_AVAILABLE_FROM("mealPreference.missingAvailableFrom", "The package type that makes the meal preference available is missing."),
    MEAL_PREFERENCE_MISSING_FREE_FROM("mealPreference.missingFreeFrom", "The package type that makes the meal preference free of charge is missing."),
    MEAL_PREFERENCE_GET_ALL("mealPreference.getAll", "Failed to get all meal preferences."),

    PACKAGE_TYPE_MISSING("packageType.missing", "The package type is missing."),
    PACKAGE_TYPE_MISSING_KEY("packageType.missingKey", "The key of the package type is missing."),
    PACKAGE_TYPE_GET_ALL("packageType.getAll", "Failed to get all package types.");

    private final String key;
    private final String description;

    CatalogError(String key, String description) {
        this.key = key;
        this.description = description;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
