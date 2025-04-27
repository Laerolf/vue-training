package jp.co.company.space.api.features.spaceShuttle.exception;

import jp.co.company.space.api.shared.exception.DomainError;

/**
 * An enum with space shuttle error messages.
 */
public enum SpaceShuttleError implements DomainError {
    MISSING_ID("spaceShuttle.missingId", "The ID of a space shuttle is missing."),
    MISSING_NAME("spaceShuttle.missingName", "The name of a space shuttle is missing."),
    MISSING_MODEL("spaceShuttle.missingModel", "The model of a space shuttle is missing."),
    MISSING_MODEL_ID("spaceShuttle.missingModel", "The model ID of a space shuttle is missing."),
    MISSING_POD_RESERVATIONS("spaceShuttle.missingPodReservations", "The pod reservations are missing."),
    MISSING_POD_CODE_FOR_RESERVATION("spaceShuttle.missingPodCodeForReservation", "The pod code for a reservation is missing."),
    MISSING_PASSENGER_FOR_RESERVATION("spaceShuttle.missingPassengerForReservation", "The passenger for a reservation is missing."),

    LAYOUT_CREATE_PODS_FOR_DECK("spaceShuttleLayout.createPodsForDeck", "Failed to create the pods for a space shuttle deck."),
    LAYOUT_CREATE_PODS_BY_DECK("spaceShuttleLayout.createPodsByDeck", "Failed to create the pods by deck map for a space shuttle layout."),
    LAYOUT_MISSING_PODS_PER_DECK("spaceShuttleLayout.missingPodsPerDeck", "The pods per deck of a space shuttle layout are missing."),
    LAYOUT_NO_PODS_PER_DECK("spaceShuttleLayout.missingPodsPerDeck", "No pods per deck of a space shuttle layout were found."),

    FIND_BY_ID("spaceShuttle.findById", "Failed to find a space shuttle with the provided ID."),
    GET_ALL("spaceShuttle.getAll", "Failed to get all existing space shuttles."),
    SAVE("spaceShuttle.save", "Failed to save a space shuttle."),
    MERGE("spaceShuttle.merge", "Failed to merge a space shuttle."),
    SAVE_LIST("spaceShuttle.saveList", "Failed to save space shuttles."),

    START_SERVICE("spaceShuttle.serviceStartUp", "Failed to start up the space shuttle service."),
    LOAD_INITIAL_DATA("spaceShuttle.loadInitialData", "Failed to load the initial space shuttles into the database.");

    private final String key;
    private final String description;

    SpaceShuttleError(String key, String description) {
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
