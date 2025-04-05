package jp.co.company.space.api.features.route.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.route.events.RouteServiceInit;
import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.route.repository.RouteRepository;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModelServiceInit;
import jp.co.company.space.api.features.spaceShuttleModel.service.SpaceShuttleModelService;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStationServiceInit;
import jp.co.company.space.api.features.spaceStation.service.SpaceStationService;

/**
 * A service class handling the {@link Route} topic.
 */
@ApplicationScoped
public class RouteService {

    @Inject
    private RouteRepository repository;

    @Inject
    private SpaceStationService spaceStationService;

    @Inject
    private SpaceShuttleModelService spaceShuttleModelService;

    /**
     * The route service initialization event.
     */
    @Inject
    private Event<RouteServiceInit> routeServiceInitEvent;

    /**
     * Is the {@link SpaceStationService} ready to be used?
     */
    private boolean isSpaceStationServiceReady;

    /**
     * Is the {@link SpaceStationService} ready to be used?
     */
    private boolean isSpaceShuttleModelServiceReady;

    protected RouteService() {}

    /**
     * Initializes the {@link RouteService} by loading the initial data into the database.
     * 
     * @param spaceStationServiceInit An event that triggers the initialization.
     */
    @Transactional
    protected void onStartUp(@Observes SpaceStationServiceInit spaceStationServiceInit) {
        try {
            isSpaceStationServiceReady = true;

            if (isReadyToBeInitialized()) {
                loadRoutes();
            }
        } catch (Exception exception) {
            throw new RuntimeException("Failed to load the initial data into the database", exception);
        }
    }

    /**
     * Initializes the {@link RouteService} by loading the initial data into the database.
     * 
     * @param spaceShuttleServiceInit An event that triggers the initialization.
     */
    @Transactional
    protected void onStartUp(@Observes SpaceShuttleModelServiceInit spaceShuttleServiceInit) {
        try {
            isSpaceShuttleModelServiceReady = true;

            if (isReadyToBeInitialized()) {
                loadRoutes();
            }
        } catch (Exception exception) {
            throw new RuntimeException("Failed to load the initial data into the database", exception);
        }
    }

    /**
     * Is the {@link RouteService} ready to be initialized?
     * 
     * @return true if the service is ready to be initialized, false otherwise.
     */
    private boolean isReadyToBeInitialized() {
        return isSpaceStationServiceReady && isSpaceShuttleModelServiceReady;
    }

    /**
     * Loads all initial {@link SpaceShuttle} instances into the database.
     */
    private void loadRoutes() {
        try (JsonReader reader = Json.createReader(RouteService.class.getResourceAsStream("/static/routes.json"))) {
            List<Route> parsedRoutes = reader.readArray().stream().map(routeJsonValue -> {
                JsonObject routeJson = routeJsonValue.asJsonObject();

                String id = routeJson.getString("id");
                String originId = routeJson.getString("originId");
                String destinationId = routeJson.getString("destinationId");
                String spaceShuttleModelId = routeJson.getString("spaceShuttleModelId");

                SpaceStation origin = spaceStationService.findById(originId).orElseThrow();
                SpaceStation destination = spaceStationService.findById(destinationId).orElseThrow();
                SpaceShuttleModel shuttleModel = spaceShuttleModelService.findById(spaceShuttleModelId).orElseThrow();

                return Route.reconstruct(id, origin, destination, shuttleModel);
            }).collect(Collectors.toList());

            repository.save(parsedRoutes);

            routeServiceInitEvent.fire(RouteServiceInit.create());
        } catch (JsonException | NullPointerException exception) {
            throw new RuntimeException("Failed to load the initial routes into the database", exception);
        }
    }

    /**
     * Gets a {@link List} of existing {@link Route} instances
     * 
     * @return The {@link List} of existing {@link Route} instances.
     */
    public List<Route> getAll() {
        return repository.getAll();
    }

    /**
     * Gets an {@link Optional} {@link Route} instance for the provided ID.
     * 
     * @param id The ID to search with.
     * @return An {@link Optional} {@link Route} instance.
     */
    public Optional<Route> findById(String id) {
        return repository.findById(id);
    }
}
