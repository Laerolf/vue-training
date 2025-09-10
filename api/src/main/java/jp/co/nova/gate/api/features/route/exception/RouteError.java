package jp.co.nova.gate.api.features.route.exception;

import jp.co.nova.gate.api.shared.exception.DomainError;

/**
 * An enum with route error messages.
 */
public enum RouteError implements DomainError {
    MISSING("route.missing", "The route is missing."),
    MISSING_ID("route.missingId", "The ID of the route is missing."),
    MISSING_ORIGIN("route.missingOrigin", "The origin of the route is missing."),
    MISSING_ORIGIN_ID("route.missingOriginId", "The origin ID of the route is missing."),
    MISSING_DESTINATION("route.missingDestination", "The destination of the route is missing."),
    MISSING_DESTINATION_ID("route.missingDestinationId", "The destination ID of the route is missing."),
    MISSING_SPACE_SHUTTLE_MODEL("route.missingSpaceShuttleModel", "The space shuttle model of the route is missing."),
    MISSING_SPACE_SHUTTLE_MODEL_ID("route.missingSpaceShuttleModelId", "The space shuttle model ID of the route is missing."),

    ROUTE_DISTANCE_MISSING_ROUTE("routeDistance.missingRoute", "The route to calculate the voyage distance with is missing."),

    FIND_BY_ID("route.findById", "Failed to find a route with the provided ID."),
    GET_ALL("route.getAll", "Failed to get all existing routes."),
    SAVE("route.save", "Failed to save a route."),
    MERGE("route.merge", "Failed to merge a route."),
    SAVE_LIST("route.saveList", "Failed to save routes."),

    START_SERVICE("route.serviceStartUp", "Failed to start up the route service."),
    LOAD_INITIAL_DATA("route.loadInitialData", "Failed to load the initial routes into the database.");

    private final String key;
    private final String description;

    RouteError(String key, String description) {
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
