package jp.co.company.space.api.features.locationCharacteristic.exception;

import jp.co.company.space.api.shared.exception.DomainError;

/**
 * An enum with location characteristic error messages.
 */
public enum LocationCharacteristicError implements DomainError {
    MISSING_ID("locationCharacteristic.missingId", "The ID of the location characteristic is missing."),
    MISSING_CHARACTERISTIC("locationCharacteristic.missingCharacteristic", "The characteristic of the location characteristic is missing."),
    MISSING_LOCATION("locationCharacteristic.missingLocation", "The location of the location characteristic is missing."),
    MISSING_KEY("locationCharacteristic.missingKey", "The key of the location characteristic is missing."),
    MISSING_LOCALE_CODE("locationCharacteristic.missingLocaleCode", "The locale code of the location characteristic is missing."),

    CREATE("locationCharacteristic.create", "Failed to create a new location characteristic."),
    FIND_BY_ID("locationCharacteristic.findById", "Failed to find a location characteristic for the provided ID."),
    SAVE("locationCharacteristic.save", "Failed to save a location characteristic."),
    MERGE("locationCharacteristic.merge", "Failed to merge a location characteristic with an existing location characteristic."),
    SAVE_LIST("locationCharacteristic.saveList", "Failed to save location characteristics.");

    private final String key;
    private final String description;

    LocationCharacteristicError(String key, String description) {
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
