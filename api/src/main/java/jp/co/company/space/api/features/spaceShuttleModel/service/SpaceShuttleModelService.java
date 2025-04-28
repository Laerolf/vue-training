package jp.co.company.space.api.features.spaceShuttleModel.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModelServiceInit;
import jp.co.company.space.api.features.spaceShuttleModel.exception.SpaceShuttleModelError;
import jp.co.company.space.api.features.spaceShuttleModel.exception.SpaceShuttleModelException;
import jp.co.company.space.api.features.spaceShuttleModel.exception.SpaceShuttleModelRuntimeException;
import jp.co.company.space.api.features.spaceShuttleModel.repository.SpaceShuttleModelRepository;
import jp.co.company.space.api.shared.exception.DomainException;
import jp.co.company.space.api.shared.util.LogBuilder;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * A service class handling the {@link SpaceShuttleModel} topic.
 */
@ApplicationScoped
public class SpaceShuttleModelService {

    private static final Logger LOGGER = Logger.getLogger(SpaceShuttleModelService.class.getName());

    /**
     * The space shuttle model repository.
     */
    @Inject
    private SpaceShuttleModelRepository repository;

    /**
     * This service's initialisation event.
     */
    @Inject
    private Event<SpaceShuttleModelServiceInit> spaceShuttleServiceInitEvent;

    protected SpaceShuttleModelService() {
    }

    /**
     * The start-up logic for this service, after the start-up it emits its initialisation event.
     *
     * @param init The event that triggers the start-up of this service.
     */
    @Transactional
    protected void onStartUp(@Observes @Initialized(ApplicationScoped.class) Object init) throws SpaceShuttleModelRuntimeException {
        try {
            LOGGER.info(new LogBuilder("Initializing the space shuttle model service.").build());

            loadSpaceShuttleModels();
            spaceShuttleServiceInitEvent.fire(SpaceShuttleModelServiceInit.create());

            LOGGER.info(new LogBuilder("The space shuttle model service is ready!").build());
        } catch (SpaceShuttleModelException exception) {
            LOGGER.severe(new LogBuilder(SpaceShuttleModelError.START_SERVICE).withException(exception).build());
            throw new SpaceShuttleModelRuntimeException(SpaceShuttleModelError.START_SERVICE, exception);
        }
    }

    /**
     * Loads the space shuttle models from the JSON file into the database.
     */
    private void loadSpaceShuttleModels() throws SpaceShuttleModelException {
        try (JsonReader reader = Json.createReader(SpaceShuttleModelService.class.getResourceAsStream("/static/space-shuttle-models.json"))) {
            List<SpaceShuttleModel> parsedSpaceShuttleModels = reader.readArray().stream().map(modelJsonValue -> {
                JsonObject modelJson = modelJsonValue.asJsonObject();

                String id = modelJson.getString("id");
                String name = modelJson.getString("name");
                int maxCapacity = modelJson.getInt("maxCapacity");
                long maxSpeed = modelJson.getInt("maxSpeed");

                return SpaceShuttleModel.reconstruct(id, name, maxCapacity, maxSpeed);
            }).collect(Collectors.toList());

            repository.save(parsedSpaceShuttleModels);
            LOGGER.info(String.format("Created %d space shuttle models.", parsedSpaceShuttleModels.size()));
        } catch (JsonException | NullPointerException | DomainException exception) {
            LOGGER.warning(new LogBuilder(SpaceShuttleModelError.LOAD_INITIAL_DATA).withException(exception).build());
            throw new SpaceShuttleModelException(SpaceShuttleModelError.LOAD_INITIAL_DATA, exception);
        }
    }

    /**
     * Gets a {@link List} of existing {@link SpaceShuttleModel} instances
     *
     * @return The {@link List} of existing {@link SpaceShuttleModel} instances.
     */
    public List<SpaceShuttleModel> getAll() throws SpaceShuttleModelException {
        try {
            return repository.getAll();
        } catch (SpaceShuttleModelException exception) {
            LOGGER.warning(new LogBuilder(SpaceShuttleModelError.GET_ALL).withException(exception).build());
            throw exception;
        }
    }

    /**
     * Gets an {@link Optional} {@link SpaceShuttleModel} instance for the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link SpaceShuttleModel} instance.
     */
    public Optional<SpaceShuttleModel> findById(String id) throws SpaceShuttleModelException {
        try {
            return repository.findById(id);
        } catch (SpaceShuttleModelException exception) {
            LOGGER.warning(new LogBuilder(SpaceShuttleModelError.FIND_BY_ID).withException(exception).withProperty("id", id).build());
            throw exception;
        }
    }
}
