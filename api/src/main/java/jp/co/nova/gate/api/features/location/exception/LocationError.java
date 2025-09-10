package jp.co.nova.gate.api.features.location.exception;

import jp.co.nova.gate.api.shared.exception.DomainError;

/**
 * An enum with location error messages.
 */
public enum LocationError implements DomainError {
    MISSING_ID("location.missingId", "The ID of the location is missing."),
    MISSING_NAME("location.missingName", "The name of the location is missing."),

    FIND_BY_ID("location.findById", "Failed to find the location with the provided ID."),
    GET_ALL("location.getAll", "Failed to get all locations."),
    SAVE("location.save", "Failed to save the location."),
    SAVE_LIST("location.saveList", "Failed to save a list of locations."),
    MERGE("location.merge", "Failed to merge the location."),

    START_SERVICE("location.serviceStartUp", "Failed to start up the location service."),
    LOAD_INITIAL_DATA("location.loadInitialData", "Failed to load the initial locations into the database.");

    private final String key;
    private final String description;

    LocationError(String key, String description) {
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
