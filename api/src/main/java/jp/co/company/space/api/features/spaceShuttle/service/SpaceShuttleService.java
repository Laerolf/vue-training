package jp.co.company.space.api.features.spaceShuttle.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.location.exception.LocationException;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttleServiceInit;
import jp.co.company.space.api.features.spaceShuttle.exception.SpaceShuttleError;
import jp.co.company.space.api.features.spaceShuttle.exception.SpaceShuttleException;
import jp.co.company.space.api.features.spaceShuttle.exception.SpaceShuttleRuntimeException;
import jp.co.company.space.api.features.spaceShuttle.repository.SpaceShuttleRepository;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModelServiceInit;
import jp.co.company.space.api.features.spaceShuttleModel.service.SpaceShuttleModelService;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import jp.co.company.space.api.shared.exception.DomainException;
import jp.co.company.space.api.shared.util.LogBuilder;

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
            LOGGER.info(new LogBuilder("Initializing the location service.").build());

            loadSpaceShuttles();
            spaceShuttleServiceInitEvent.fire(SpaceShuttleServiceInit.create());

            LOGGER.info(new LogBuilder("The location service is ready!").build());
        } catch (Exception exception) {
            LOGGER.severe(new LogBuilder(SpaceShuttleError.START_SERVICE).withException(exception).build());
            throw new SpaceShuttleRuntimeException(SpaceShuttleError.START_SERVICE, exception);
        }
    }

    /**
     * Loads all initial {@link SpaceShuttle} instances into the database.
     */
    private void loadSpaceShuttles() throws LocationException {
        try (JsonReader reader = Json.createReader(SpaceShuttleService.class.getResourceAsStream("/static/space-shuttles.json"))) {
            List<SpaceShuttle> parsedSpaceShuttles = reader.readArray().stream().map(shuttleJsonValue -> {
                JsonObject shuttleJson = shuttleJsonValue.asJsonObject();

                String id = shuttleJson.getString("id");
                String name = shuttleJson.getString("name");
                String modelId = shuttleJson.getString("modelId");

                SpaceShuttleModel shuttleModel = spaceShuttleModelService.findById(modelId).orElseThrow();
                return SpaceShuttle.reconstruct(id, name, shuttleModel);
            }).collect(Collectors.toList());

            // TODO: check exceptions from other topics
            repository.save(parsedSpaceShuttles);
            LOGGER.info(new LogBuilder(String.format("Created %d space shuttles.", parsedSpaceShuttles.size())).build());
        } catch (JsonException | NullPointerException | SpaceShuttleException exception) {
            LOGGER.warning(new LogBuilder(SpaceShuttleError.LOAD_INITIAL_DATA).withException(exception).build());
            throw new SpaceShuttleException(SpaceShuttleError.LOAD_INITIAL_DATA, exception);
        }
    }

    /**
     * Gets a {@link List} of existing {@link SpaceShuttle} instances
     *
     * @return The {@link List} of existing {@link SpaceShuttle} instances.
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
     * Gets an {@link Optional} {@link SpaceShuttle} instance for the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link SpaceShuttle} instance.
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
