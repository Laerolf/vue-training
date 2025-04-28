package jp.co.company.space.api.features.voyage.exception;

import jp.co.company.space.api.shared.exception.DomainError;

/**
 * An enum with voyage error messages.
 */
public enum VoyageError implements DomainError {
    MISSING("voyage.missing", "A voyage is missing."),
    MISSING_ID("voyage.missingId", "The ID of a voyage is missing."),
    MISSING_DEPARTURE_DATE("voyage.missingDepartureDate", "The departure date of a voyage is missing."),
    MISSING_ARRIVAL_DATE("voyage.missingArrivalDate", "The arrival date of a voyage is missing."),
    MISSING_DURATION("voyage.missingDuration", "The duration of a voyage is missing."),
    MISSING_STATUS("voyage.missingStatus", "The status of a voyage is missing."),
    MISSING_ROUTE("voyage.missingRoute", "The route of a voyage is missing."),
    MISSING_ROUTE_ID("voyage.missingRouteId", "The route ID of a voyage is missing."),
    MISSING_ORIGIN_ID("voyage.missingOriginId", "The route's origin ID of a voyage is missing."),
    MISSING_DESTINATION_ID("voyage.missingDestinationId", "The route's destination ID of a voyage is missing."),
    MISSING_SPACE_SHUTTLE("voyage.missingSpaceShuttle", "The space shuttle of a voyage is missing."),
    MISSING_SPACE_SHUTTLE_ID("voyage.missingSpaceShuttleId", "The space shuttle ID of a voyage is missing."),

    ARRIVAL_DATE_BEFORE_DEPARTURE_DATE("voyage.arrivalDateBeforeDepartureDate", "The arrival date of a voyage cannot be before its departure date."),

    VOYAGE_DURATION_CALCULATION("voyageDuration.calculation", "Unable to calculate the duration of a voyage."),

    FIND_BY_ID("voyage.findById", "Failed to find a voyage with the provided ID."),
    GET_ALL("voyage.getAll", "Failed to get all existing voyages."),
    GET_ALL_BY_ORIGIN_ID("voyage.getAllByOriginId", "Failed to get all voyages with the provided origin ID."),
    GET_ALL_BY_DESTINATION_ID("voyage.getAllByDestinationId", "Failed to get all voyages with the provided destination ID."),
    GET_ALL_BY_ORIGIN_ID_AND_DESTINATION_ID("voyage.getAllByOriginIdAndDestinationId", "Failed to get all voyages with the provided origin ID and destination ID."),
    GET_ALL_PODS_BY_VOYAGE("voyage.getAllPodsByVoyage", "Failed to get all pods of the voyage matching the provided voyage."),
    GET_ALL_PODS_BY_VOYAGE_ID("voyage.getAllPodsByVoyageId", "Failed to get all pods of the voyage matching the provided voyage ID."),
    SAVE("voyage.save", "Failed to save a voyage."),
    MERGE("voyage.merge", "Failed to merge a voyage."),
    SAVE_LIST("voyage.saveList", "Failed to save stations."),

    START_SERVICE("voyage.serviceStartUp", "Failed to start up the voyage service."),
    LOAD_INITIAL_DATA("voyage.loadInitialData", "Failed to load the initial voyages into the database.");
    
    
    private final String key;
    private final String description;

    VoyageError(String key, String description) {
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
