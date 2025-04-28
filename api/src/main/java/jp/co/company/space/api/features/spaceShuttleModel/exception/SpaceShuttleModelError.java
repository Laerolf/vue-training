package jp.co.company.space.api.features.spaceShuttleModel.exception;

import jp.co.company.space.api.shared.exception.DomainError;

/**
 * An enum with space shuttle model error messages.
 */
public enum SpaceShuttleModelError implements DomainError {
    MISSING_ID("spaceShuttleModel.missingId", "The ID of a space shuttle model is missing."),
    MISSING_NAME("spaceShuttleModel.missingName", "The name of a space shuttle model is missing."),

    FIND_BY_ID("spaceShuttleModel.findById", "Failed to find a space shuttle model with the provided ID."),
    GET_ALL("spaceShuttleModel.getAll", "Failed to get all existing space shuttle models."),
    SAVE("spaceShuttleModel.save", "Failed to save a space shuttle model."),
    MERGE("spaceShuttleModel.merge", "Failed to merge a space shuttle model."),
    SAVE_LIST("spaceShuttleModel.saveList", "Failed to save space shuttle models."),

    START_SERVICE("spaceShuttleModel.serviceStartUp", "Failed to start up the space shuttle model service."),
    LOAD_INITIAL_DATA("spaceShuttleModel.loadInitialData", "Failed to load the initial space shuttle models into the database.");

    private final String key;
    private final String description;

    SpaceShuttleModelError(String key, String description) {
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
