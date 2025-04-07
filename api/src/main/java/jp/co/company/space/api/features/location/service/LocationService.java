package jp.co.company.space.api.features.location.service;

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
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.location.domain.LocationServiceInit;
import jp.co.company.space.api.features.location.repository.LocationRepository;

/**
 * A service class handling the {@link Location} topic.
 */
@ApplicationScoped
public class LocationService {
    /**
     * The location repository.
     */
    @Inject
    private LocationRepository repository;

    /**
     * The location service initialization event.
     */
    @Inject
    private Event<LocationServiceInit> locationServiceInitEvent;

    protected LocationService() {}

    /**
     * Initializes the {@link LocationService} by loading the initial data into the database.
     * 
     * @param init The event that triggers the initialization.
     */
    @Transactional
    public void onStartUp(@Observes @Initialized(ApplicationScoped.class) Object init) {
        try {
            loadLocations();
            locationServiceInitEvent.fire(LocationServiceInit.create());
        } catch (Exception exception) {
            throw new RuntimeException("Failed to load the initial data into the database", exception);
        }
    }

    /**
     * Loads all initial {@link Location} instances into the database.
     */
    private void loadLocations() {
        try (JsonReader reader = Json
                .createReader(LocationService.class.getResourceAsStream("/static/locations.json"))) {
            List<Location> parsedLocations = reader.readArray().stream().map(locationJsonValue -> {
                JsonObject locationJson = locationJsonValue.asJsonObject();

                String id = locationJson.getString("id");
                String name = locationJson.getString("name");
                double latitude = locationJson.getJsonNumber("latitude").doubleValue();
                double longitude = locationJson.getJsonNumber("longitude").doubleValue();
                double radialDistance = locationJson.getJsonNumber("radialDistance").doubleValue();

                return Location.reconstruct(id, name, latitude, longitude, radialDistance);
            }).collect(Collectors.toList());

            repository.save(parsedLocations);
        } catch (JsonException | NullPointerException exception) {
            throw new RuntimeException("Failed to load the initial locations into the database", exception);
        }
    }

    /**
     * Gets a {@link List} of existing {@link Location} instances
     * 
     * @return The {@link List} of existing {@link Location} instances.
     */
    public List<Location> getAll() {
        return repository.getAll();
    }

    /**
     * Gets an {@link Optional} {@link Location} instance for the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link Location} instance.
     */
    public Optional<Location> findById(String id) {
        return repository.findById(id);
    }
}
