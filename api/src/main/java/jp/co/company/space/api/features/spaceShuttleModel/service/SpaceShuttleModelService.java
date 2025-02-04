package jp.co.company.space.api.features.spaceShuttleModel.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModelServiceInit;
import jp.co.company.space.api.features.spaceShuttleModel.repository.SpaceShuttleModelRepository;

/**
 * A service class handling the {@link SpaceShuttleModel} topic.
 */
@ApplicationScoped
public class SpaceShuttleModelService {

    /**
     * The space shuttle model repository.
     */
    @Inject
    private SpaceShuttleModelRepository shuttleModelRepository;

    /**
     * This service's initialisation event.
     */
    @Inject
    private Event<SpaceShuttleModelServiceInit> spaceShuttleServiceInitEvent;

    protected SpaceShuttleModelService() {}

    /**
     * The start-up logic for this service, after the start-up it emits its initialisation event.
     * 
     * @param init The event that triggers the start-up of this service.
     */
    protected void onStartUp(@Observes @Initialized(ApplicationScoped.class) Object init) {
        try {
            loadSpaceShuttleModels();
            spaceShuttleServiceInitEvent.fire(SpaceShuttleModelServiceInit.create());
        } catch (Exception exception) {
            throw new RuntimeException("Failed to load the initial space shuttle models into the database", exception);
        }
    }

    /**
     * Loads the space shuttle models from the JSON file into the database.
     */
    private void loadSpaceShuttleModels() {
        try (JsonReader reader = Json.createReader(
                SpaceShuttleModelService.class.getResourceAsStream("/static/space-shuttle-models.json"))) {
            List<SpaceShuttleModel> parsedSpaceShuttleModels = reader.readArray().stream().map(modelJsonValue -> {
                JsonObject modelJson = modelJsonValue.asJsonObject();

                String id = modelJson.getString("id");
                String name = modelJson.getString("name");
                int maxCapacity = modelJson.getInt("maxCapacity");
                long maxSpeed = modelJson.getInt("maxSpeed");

                return SpaceShuttleModel.reconstruct(id, name, maxCapacity, maxSpeed);
            }).collect(Collectors.toList());

            shuttleModelRepository.save(parsedSpaceShuttleModels);
        } catch (JsonException | NullPointerException exception) {
            throw new RuntimeException("Failed to load the initial space shuttle models into the database", exception);
        }
    }

    /**
     * Gets a {@link List} of existing {@link SpaceShuttleModel} instances
     * 
     * @return The {@link List} of existing {@link SpaceShuttleModel} instances.
     */
    public List<SpaceShuttleModel> getAll() {
        return shuttleModelRepository.getAll();
    }

    /**
     * Gets an {@link Optional} {@link SpaceShuttleModel} instance for the provided ID.
     * 
     * @param id The ID to search with.
     * @return An {@link Optional} {@link SpaceShuttleModel} instance.
     */
    public Optional<SpaceShuttleModel> findById(String id) {
        return shuttleModelRepository.findById(id);
    }

}
