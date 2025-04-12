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
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttleServiceInit;
import jp.co.company.space.api.features.spaceShuttle.repository.SpaceShuttleRepository;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModelServiceInit;
import jp.co.company.space.api.features.spaceShuttleModel.service.SpaceShuttleModelService;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A service class handling the {@link SpaceStation} topic.
 */
@ApplicationScoped
public class SpaceShuttleService {

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

    protected SpaceShuttleService() {}

    /**
     * Initializes the {@link SpaceShuttleService} by loading the initial data into the database.
     *
     * @param init The event that triggers the initialization.
     */
    @Transactional
    protected void onStartUp(@Observes SpaceShuttleModelServiceInit init) {
        try {
            loadSpaceShuttles();
            spaceShuttleServiceInitEvent.fire(SpaceShuttleServiceInit.create());
        } catch (Exception exception) {
            throw new RuntimeException("Failed to load the initial data into the database", exception);
        }
    }

    /**
     * Loads all initial {@link SpaceShuttle} instances into the database.
     */
    private void loadSpaceShuttles() {
        try (JsonReader reader = Json.createReader(SpaceShuttleService.class.getResourceAsStream("/static/space-shuttles.json"))) {
            List<SpaceShuttle> parsedSpaceShuttles = reader.readArray().stream().map(shuttleJsonValue -> {
                JsonObject shuttleJson = shuttleJsonValue.asJsonObject();

                String id = shuttleJson.getString("id");
                String name = shuttleJson.getString("name");
                String modelId = shuttleJson.getString("modelId");

                SpaceShuttleModel shuttleModel = spaceShuttleModelService.findById(modelId).orElseThrow();
                return SpaceShuttle.reconstruct(id, name, shuttleModel);
            }).collect(Collectors.toList());

            repository.save(parsedSpaceShuttles);
        } catch (JsonException | NullPointerException exception) {
            throw new RuntimeException("Failed to load the initial space shuttles into the database", exception);
        }
    }

    /**
     * Gets a {@link List} of existing {@link SpaceShuttle} instances
     *
     * @return The {@link List} of existing {@link SpaceShuttle} instances.
     */
    public List<SpaceShuttle> getAll() {
        return repository.getAll();
    }

    /**
     * Gets an {@link Optional} {@link SpaceShuttle} instance for the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link SpaceShuttle} instance.
     */
    public Optional<SpaceShuttle> findById(String id) {
        return repository.findById(id);
    }
}
