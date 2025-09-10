package jp.co.nova.gate.api.features.spaceShuttle.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.transaction.Transactional;
import jp.co.nova.gate.api.features.location.exception.LocationException;
import jp.co.nova.gate.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.nova.gate.api.features.spaceShuttle.domain.SpaceShuttleServiceInit;
import jp.co.nova.gate.api.features.spaceShuttle.exception.SpaceShuttleError;
import jp.co.nova.gate.api.features.spaceShuttle.exception.SpaceShuttleException;
import jp.co.nova.gate.api.features.spaceShuttle.exception.SpaceShuttleRuntimeException;
import jp.co.nova.gate.api.features.spaceShuttle.repository.SpaceShuttleRepository;
import jp.co.nova.gate.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.nova.gate.api.features.spaceShuttleModel.domain.SpaceShuttleModelServiceInit;
import jp.co.nova.gate.api.features.spaceShuttleModel.exception.SpaceShuttleModelError;
import jp.co.nova.gate.api.features.spaceShuttleModel.exception.SpaceShuttleModelException;
import jp.co.nova.gate.api.features.spaceShuttleModel.service.SpaceShuttleModelService;
import jp.co.nova.gate.api.features.spaceStation.domain.SpaceStation;
import jp.co.nova.gate.api.shared.exception.DomainException;
import jp.co.nova.gate.api.shared.util.LogBuilder;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * A service class handling the {@link SpaceStation} topic.
 */
@ApplicationScoped
public class SpaceShuttleService {

    private static final Logger LOGGER = Logger.getLogger(SpaceShuttleService.class.getName());

    /**
     * The space shuttle repository.
     */
    @Inject
    private SpaceShuttleRepository repository;

    @Inject
    private SpaceShuttleModelService spaceShuttleModelService;

    /**
     * The space shuttle service initialization event.
     */
    @Inject
    private Event<SpaceShuttleServiceInit> spaceShuttleServiceInitEvent;

    protected SpaceShuttleService() {
    }

    /**
     * Initializes the {@link SpaceShuttleService} by loading the initial data into the database.
     *
     * @param init The event that triggers the initialization.
     */
    @Transactional
    protected void onStartUp(@Observes SpaceShuttleModelServiceInit init) throws SpaceShuttleRuntimeException {
        try {
            LOGGER.info(new LogBuilder("Initializing the space shuttle service.").build());
            loadSpaceShuttles();
            LOGGER.info(new LogBuilder("The space shuttle service is ready!").build());

            spaceShuttleServiceInitEvent.fire(SpaceShuttleServiceInit.create());
        } catch (Exception exception) {
            LOGGER.severe(new LogBuilder(SpaceShuttleError.START_SERVICE).withException(exception).build());
            throw new SpaceShuttleRuntimeException(SpaceShuttleError.START_SERVICE, exception);
        }
    }

    /**
     * Loads all initial {@link SpaceShuttle}s into the database.
     */
    private void loadSpaceShuttles() throws LocationException {
        try (JsonReader reader = Json.createReader(SpaceShuttleService.class.getResourceAsStream("/static/space-shuttles.json"))) {
            List<SpaceShuttle> parsedSpaceShuttles = reader.readArray().stream().map(shuttleJsonValue -> {
                JsonObject shuttleJson = shuttleJsonValue.asJsonObject();

                String id = shuttleJson.getString("id");
                String name = shuttleJson.getString("name");
                String modelId = shuttleJson.getString("modelId");

                SpaceShuttleModel shuttleModel = spaceShuttleModelService.findById(modelId).orElseThrow(() -> new SpaceShuttleModelException(SpaceShuttleModelError.FIND_BY_ID));
                return SpaceShuttle.reconstruct(id, name, shuttleModel);
            }).collect(Collectors.toList());

            repository.save(parsedSpaceShuttles);
            LOGGER.info(new LogBuilder(String.format("Created %d space shuttles.", parsedSpaceShuttles.size())).build());
        } catch (JsonException | NullPointerException | DomainException exception) {
            LOGGER.warning(new LogBuilder(SpaceShuttleError.LOAD_INITIAL_DATA).withException(exception).build());
            throw new SpaceShuttleException(SpaceShuttleError.LOAD_INITIAL_DATA, exception);
        }
    }

    /**
     * Gets a {@link List} of existing {@link SpaceShuttle}s
     *
     * @return The {@link List} of existing {@link SpaceShuttle}s.
     */
    public List<SpaceShuttle> getAll() throws SpaceShuttleException {
        try {
            return repository.getAll();
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(SpaceShuttleError.GET_ALL).withException(exception).build());
            throw exception;
        }
    }

    /**
     * Returns a {@link List} of all {@link SpaceShuttle}s with the {@link SpaceShuttleModel} matching the provided ID.
     *
     * @param id The ID to search with.
     * @return A {@link List} of {@link SpaceShuttle}s.
     */
    public List<SpaceShuttle> getAllSpaceShuttlesByModelId(String id) throws SpaceShuttleException {
        return repository.getAllSpaceShuttlesByModelId(id);
    }

    /**
     * Gets an {@link Optional} {@link SpaceShuttle} for the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link SpaceShuttle}.
     */
    public Optional<SpaceShuttle> findById(String id) throws SpaceShuttleException {
        try {
            return repository.findById(id);
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(SpaceShuttleError.FIND_BY_ID).withException(exception).withProperty("id", id).build());
            throw exception;
        }
    }
}
