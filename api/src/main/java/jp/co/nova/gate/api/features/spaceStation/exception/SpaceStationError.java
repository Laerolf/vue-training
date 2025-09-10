package jp.co.nova.gate.api.features.spaceStation.exception;

import jp.co.nova.gate.api.shared.exception.DomainError;

/**
 * An enum with space station error messages.
 */
public enum SpaceStationError implements DomainError {
    MISSING("spaceStation.missing", "A space station is missing."),
    MISSING_ID("spaceStation.missingId", "The ID of a space station is missing."),
    MISSING_NAME("spaceStation.missingName", "The name of a space station is missing."),
    MISSING_CODE("spaceStation.missingCode", "The code of a space station is missing."),
    MISSING_LOCATION("spaceStation.missingLocation", "The location of a space station is missing."),

    FIND_BY_ID("spaceStation.findById", "Failed to find a space station with the provided ID."),
    GET_ALL("spaceStation.getAll", "Failed to get all existing space stations."),
    SAVE("spaceStation.save", "Failed to save a space station."),
    MERGE("spaceStation.merge", "Failed to merge a space station."),
    SAVE_LIST("spaceStation.saveList", "Failed to save stations."),

    START_SERVICE("spaceStation.serviceStartUp", "Failed to start up the space station service."),
    LOAD_INITIAL_DATA("spaceStation.loadInitialData", "Failed to load the initial space stations into the database.");

    private final String key;
    private final String description;

    SpaceStationError(String key, String description) {
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
