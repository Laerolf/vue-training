package jp.co.nova.gate.api.features.catalog.exception;

import jp.co.nova.gate.api.shared.exception.DomainError;

/**
 * An enum with catalog error messages.
 */
public enum CatalogError implements DomainError {
    MISSING_KEY("catalog.key", "The key of the catalog item is missing."),
    MISSING_LABEL("catalog.label", "The label of the catalog item is missing."),

    CATALOG_ITEM_MISMATCH("catalog.mismatch", "The provided catalog item is not a known catalog item type."),
    CATALOG_ITEM_GENDER_MISMATCH("catalog.genderMismatch", "The provided catalog item is not a gender."),
    CATALOG_ITEM_MEAL_PREFERENCE_MISMATCH("catalog.mealPreferenceMismatch", "The provided catalog item is not a meal preference."),
    CATALOG_ITEM_NATIONALITY_MISMATCH("catalog.nationalityMismatch", "The provided catalog item is not a nationality."),
    CATALOG_ITEM_PACKAGE_TYPE_MISMATCH("catalog.packageTypeMismatch", "The provided catalog item is not a package type."),
    CATALOG_ITEM_POD_TYPE_MISMATCH("catalog.podTypeMismatch", "The provided catalog item is not a pod type."),

    MEAL_PREFERENCE_MISSING("mealPreference.missing", "The meal preference is missing."),
    MEAL_PREFERENCE_MISSING_AVAILABLE_FROM("mealPreference.missingAvailableFrom", "The package type that makes the meal preference available is missing."),
    MEAL_PREFERENCE_MISSING_FREE_FROM("mealPreference.missingFreeFrom", "The package type that makes the meal preference free of charge is missing."),

    PACKAGE_TYPE_MISSING("packageType.missing", "The package type is missing."),

    GENDER_MISSING("gender.missing", "The gender is missing."),

    NATIONALITY_MISSING("nationality.missing", "The nationality is missing.");

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
