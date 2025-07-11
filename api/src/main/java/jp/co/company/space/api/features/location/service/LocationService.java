package jp.co.company.space.api.features.location.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.ObserverException;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.location.events.LocationServiceInit;
import jp.co.company.space.api.features.location.exception.LocationError;
import jp.co.company.space.api.features.location.exception.LocationException;
import jp.co.company.space.api.features.location.exception.LocationRuntimeException;
import jp.co.company.space.api.features.location.repository.LocationRepository;
import jp.co.company.space.api.shared.exception.DomainException;
import jp.co.company.space.api.shared.util.LogBuilder;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * A service class handling the {@link Location} topic.
 */
@ApplicationScoped
public class LocationService {

    private static final Logger LOGGER = Logger.getLogger(LocationService.class.getName());

    /**
     * The location repository.
     */
    @Inject
    private LocationRepository locationRepository;

    /**
     * The location service initialization event.
     */
    @Inject
    private Event<LocationServiceInit> locationServiceInitEvent;

    protected LocationService() {
    }

    /**
     * Initializes the {@link LocationService} by loading the initial data into the database.
     *
     * @param init The event that triggers the initialization.
     */
    @Transactional
    public void onStartUp(@Observes @Initialized(ApplicationScoped.class) Object init) throws LocationRuntimeException {
        try {
            LOGGER.info(new LogBuilder("Initializing the location service.").build());
            loadLocations();
            LOGGER.info(new LogBuilder("The location service is ready!").build());

            locationServiceInitEvent.fire(LocationServiceInit.create());
        } catch (IllegalArgumentException | ObserverException exception) {
            LOGGER.severe(new LogBuilder(LocationError.START_SERVICE).withException(exception).build());
            throw new LocationRuntimeException(LocationError.START_SERVICE, exception);
        }
    }

    /**
     * Loads all initial {@link Location} instances into the database.
     */
    private void loadLocations() throws LocationException {
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

            locationRepository.save(parsedLocations);
            LOGGER.info(new LogBuilder(String.format("Created %d locations.", parsedLocations.size())).build());
        } catch (JsonException | NullPointerException | DomainException exception) {
            LOGGER.warning(new LogBuilder(LocationError.LOAD_INITIAL_DATA).withException(exception).build());
            throw new LocationException(LocationError.LOAD_INITIAL_DATA, exception);
        }
    }

    /**
     * Gets a {@link List} of existing {@link Location} instances
     *
     * @return The {@link List} of existing {@link Location} instances.
     */
    public List<Location> getAll() throws LocationException {
        try {
            return locationRepository.getAll();
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(LocationError.GET_ALL).withException(exception).build());
            throw exception;
        }
    }

    /**
     * Gets an {@link Optional} {@link Location} instance for the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link Location} instance.
     */
    public Optional<Location> findById(String id) throws LocationException {
        try {
            return locationRepository.findById(id);
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(LocationError.FIND_BY_ID).withException(exception).withProperty("id", id).build());
            throw exception;
        }
    }
}
