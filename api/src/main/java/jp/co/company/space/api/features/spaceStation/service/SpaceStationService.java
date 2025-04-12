package jp.co.company.space.api.features.spaceStation.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.*;
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.location.domain.LocationServiceInit;
import jp.co.company.space.api.features.location.service.LocationService;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStationServiceInit;
import jp.co.company.space.api.features.spaceStation.repository.SpaceStationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A service class handling the {@link SpaceStation} topic.
 */
@ApplicationScoped
public class SpaceStationService {
    /**
     * The space station repository.
     */
    @Inject
    private SpaceStationRepository repository;

    /**
     * The location service.
     */
    @Inject
    private LocationService locationService;

    /**
     * This service's initialisation event.
     */
    @Inject
    private Event<SpaceStationServiceInit> event;

    /**
     * The start-up logic for this service, after the start-up it emits its initialisation event.
     *
     * @param init The event that triggers the start-up of this service.
     */
    @Transactional
    protected void onStartUp(@Observes LocationServiceInit init) {
        try {
            loadSpaceStations();

            event.fire(SpaceStationServiceInit.create());
        } catch (Exception exception) {
            throw new RuntimeException("Failed to load the initial data into the database", exception);
        }
    }

    /**
     * Loads all initial {@link SpaceStation} instances into the database.
     */
    private void loadSpaceStations() {
        try (JsonReader reader = Json.createReader(SpaceStationService.class.getResourceAsStream("/static/space-stations.json"))) {
            List<SpaceStation> parsedSpaceStations = reader.readArray().stream().map(spaceStationJsonValue -> {
                JsonObject spaceStationJson = spaceStationJsonValue.asJsonObject();

                String id = spaceStationJson.getString("id");
                String name = spaceStationJson.getString("name");
                String code = spaceStationJson.getString("code");
                String country = spaceStationJson.getOrDefault("country", JsonValue.NULL).toString();

                String locationId = spaceStationJson.getString("locationId");
                Location location = locationService.findById(locationId).orElseThrow();

                return SpaceStation.reconstruct(id, name, code, country, location);
            }).collect(Collectors.toList());

            repository.save(parsedSpaceStations);
        } catch (JsonException | NullPointerException exception) {
            throw new RuntimeException("Failed to load the initial space stations into the database", exception);
        }
    }

    /**
     * Gets a {@link List} of existing {@link SpaceStation} instances
     *
     * @return The {@link List} of existing {@link SpaceStation} instances.
     */
    public List<SpaceStation> getAll() {
        return repository.getAll();
    }

    /**
     * Gets an {@link Optional} {@link SpaceStation} instance for the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link SpaceStation} instance.
     */
    public Optional<SpaceStation> findById(String id) {
        return repository.findById(id);
    }
}
