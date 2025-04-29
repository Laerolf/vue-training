package jp.co.company.space.api.features.route.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.ObserverException;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.route.events.RouteServiceInit;
import jp.co.company.space.api.features.route.exception.RouteError;
import jp.co.company.space.api.features.route.exception.RouteException;
import jp.co.company.space.api.features.route.exception.RouteRuntimeException;
import jp.co.company.space.api.features.route.repository.RouteRepository;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModelServiceInit;
import jp.co.company.space.api.features.spaceShuttleModel.exception.SpaceShuttleModelError;
import jp.co.company.space.api.features.spaceShuttleModel.exception.SpaceShuttleModelException;
import jp.co.company.space.api.features.spaceShuttleModel.service.SpaceShuttleModelService;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStationServiceInit;
import jp.co.company.space.api.features.spaceStation.exception.SpaceStationError;
import jp.co.company.space.api.features.spaceStation.exception.SpaceStationException;
import jp.co.company.space.api.features.spaceStation.service.SpaceStationService;
import jp.co.company.space.api.shared.exception.DomainException;
import jp.co.company.space.api.shared.util.LogBuilder;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * A service class handling the {@link Route} topic.
 */
@ApplicationScoped
public class RouteService {

    private static final Logger LOGGER = Logger.getLogger(RouteService.class.getName());

    @Inject
    private RouteRepository routeRepository;

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

    protected RouteService() {
    }

    /**
     * Initializes the {@link RouteService} by loading the initial data into the database.
     *
     * @param spaceStationServiceInit An event that triggers the initialization.
     */
    @Transactional
    protected void onStartUp(@Observes SpaceStationServiceInit spaceStationServiceInit) throws RouteRuntimeException {
        try {
            isSpaceStationServiceReady = true;

            if (isReadyToBeInitialized()) {
                initialize();
            }
        } catch (Exception exception) {
            LOGGER.severe(new LogBuilder(RouteError.START_SERVICE).withException(exception).build());
            throw new RouteRuntimeException(RouteError.START_SERVICE, exception);
        }
    }

    /**
     * Initializes the {@link RouteService} by loading the initial data into the database.
     *
     * @param spaceShuttleServiceInit An event that triggers the initialization.
     */
    @Transactional
    protected void onStartUp(@Observes SpaceShuttleModelServiceInit spaceShuttleServiceInit) throws RouteRuntimeException {
        try {
            isSpaceShuttleModelServiceReady = true;

            if (isReadyToBeInitialized()) {
                initialize();
            }
        } catch (Exception exception) {
            LOGGER.severe(new LogBuilder(RouteError.START_SERVICE).withException(exception).build());
            throw new RouteRuntimeException(RouteError.START_SERVICE, exception);
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
     * Initializes this service.
     */
    private void initialize() throws RouteException {
        LOGGER.info(new LogBuilder("Initializing the route service.").build());
        loadRoutes();
        LOGGER.info(new LogBuilder("The route service is ready!").build());

        routeServiceInitEvent.fire(RouteServiceInit.create());
    }

    /**
     * Loads all initial {@link SpaceShuttle} instances into the database.
     */
    private void loadRoutes() throws RouteException {
        try (JsonReader reader = Json.createReader(RouteService.class.getResourceAsStream("/static/routes.json"))) {
            List<Route> parsedRoutes = reader.readArray().stream().map(routeJsonValue -> {
                JsonObject routeJson = routeJsonValue.asJsonObject();

                String id = routeJson.getString("id");
                String originId = routeJson.getString("originId");
                String destinationId = routeJson.getString("destinationId");
                String spaceShuttleModelId = routeJson.getString("spaceShuttleModelId");

                SpaceStation origin = spaceStationService.findById(originId).orElseThrow(() -> new SpaceStationException(SpaceStationError.FIND_BY_ID));
                SpaceStation destination = spaceStationService.findById(destinationId).orElseThrow(() -> new SpaceStationException(SpaceStationError.FIND_BY_ID));
                SpaceShuttleModel shuttleModel = spaceShuttleModelService.findById(spaceShuttleModelId).orElseThrow(() -> new SpaceShuttleModelException(SpaceShuttleModelError.FIND_BY_ID));

                return Route.reconstruct(id, origin, destination, shuttleModel);
            }).collect(Collectors.toList());

            routeRepository.save(parsedRoutes);
            LOGGER.info(new LogBuilder(String.format("Created %d routes.", parsedRoutes.size())).build());
        } catch (JsonException | ObserverException | ClassCastException | DomainException |
                 NullPointerException exception) {
            LOGGER.warning(new LogBuilder(RouteError.LOAD_INITIAL_DATA).withException(exception).build());
            throw new RouteException(RouteError.LOAD_INITIAL_DATA, exception);
        }
    }

    /**
     * Gets a {@link List} of existing {@link Route} instances
     *
     * @return The {@link List} of existing {@link Route} instances.
     */
    public List<Route> getAll() throws RouteException {
        try {
            return routeRepository.getAll();
        } catch (RouteException exception) {
            LOGGER.warning(new LogBuilder(RouteError.GET_ALL).withException(exception).build());
            throw exception;
        }
    }

    /**
     * Gets an {@link Optional} {@link Route} instance for the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link Route} instance.
     */
    public Optional<Route> findById(String id) throws RouteException {
        try {
            return routeRepository.findById(id);
        } catch (RouteException exception) {
            LOGGER.warning(new LogBuilder(RouteError.FIND_BY_ID).withException(exception).build());
            throw exception;
        }
    }
}
