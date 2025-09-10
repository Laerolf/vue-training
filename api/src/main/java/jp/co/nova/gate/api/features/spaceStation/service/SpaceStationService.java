package jp.co.nova.gate.api.features.spaceStation.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.ObserverException;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.*;
import jakarta.transaction.Transactional;
import jp.co.nova.gate.api.features.location.domain.Location;
import jp.co.nova.gate.api.features.location.events.LocationServiceInit;
import jp.co.nova.gate.api.features.location.exception.LocationError;
import jp.co.nova.gate.api.features.location.exception.LocationException;
import jp.co.nova.gate.api.features.location.service.LocationService;
import jp.co.nova.gate.api.features.spaceStation.domain.SpaceStation;
import jp.co.nova.gate.api.features.spaceStation.domain.SpaceStationServiceInit;
import jp.co.nova.gate.api.features.spaceStation.exception.SpaceStationError;
import jp.co.nova.gate.api.features.spaceStation.exception.SpaceStationException;
import jp.co.nova.gate.api.features.spaceStation.exception.SpaceStationRuntimeException;
import jp.co.nova.gate.api.features.spaceStation.repository.SpaceStationRepository;
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
public class SpaceStationService {

    private static final Logger LOGGER = Logger.getLogger(SpaceStationService.class.getName());

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
    protected void onStartUp(@Observes LocationServiceInit init) throws SpaceStationRuntimeException {
        try {
            LOGGER.info(new LogBuilder("Initializing the space station service.").build());
            loadSpaceStations();
            LOGGER.info(new LogBuilder("The space station service is ready!").build());

            event.fire(SpaceStationServiceInit.create());
        } catch (IllegalArgumentException | ObserverException exception) {
            LOGGER.warning(new LogBuilder(SpaceStationError.LOAD_INITIAL_DATA).withException(exception).build());
            throw new SpaceStationRuntimeException(SpaceStationError.START_SERVICE, exception);
        }
    }

    /**
     * Loads all initial {@link SpaceStation}s into the database.
     */
    private void loadSpaceStations() throws SpaceStationException {
        try (JsonReader reader = Json.createReader(SpaceStationService.class.getResourceAsStream("/static/space-stations.json"))) {
            List<SpaceStation> parsedSpaceStations = reader.readArray().stream().map(spaceStationJsonValue -> {
                JsonObject spaceStationJson = spaceStationJsonValue.asJsonObject();

                String id = spaceStationJson.getString("id");
                String name = spaceStationJson.getString("name");
                String code = spaceStationJson.getString("code");
                String country = spaceStationJson.getOrDefault("country", JsonValue.NULL).toString();

                String locationId = spaceStationJson.getString("locationId");
                Location location = locationService.findById(locationId).orElseThrow(() -> new LocationException(LocationError.FIND_BY_ID));

                return SpaceStation.reconstruct(id, name, code, country, location);
            }).collect(Collectors.toList());

            repository.save(parsedSpaceStations);
            LOGGER.info(String.format("Created %d space stations.", parsedSpaceStations.size()));
        } catch (JsonException | NullPointerException | DomainException exception) {
            LOGGER.warning(new LogBuilder(SpaceStationError.LOAD_INITIAL_DATA).withException(exception).build());
            throw new SpaceStationException(SpaceStationError.LOAD_INITIAL_DATA, exception);
        }
    }

    /**
     * Gets a {@link List} of existing {@link SpaceStation}s
     *
     * @return The {@link List} of existing {@link SpaceStation}s.
     */
    public List<SpaceStation> getAll() throws SpaceStationException {
        try {
            return repository.getAll();
        } catch (SpaceStationException exception) {
            LOGGER.warning(new LogBuilder(SpaceStationError.GET_ALL).withException(exception).build());
            throw exception;
        }
    }

    /**
     * Gets an {@link Optional} {@link SpaceStation} for the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link SpaceStation}.
     */
    public Optional<SpaceStation> findById(String id) throws SpaceStationException {
        try {
            return repository.findById(id);
        } catch (SpaceStationException exception) {
            LOGGER.warning(new LogBuilder(SpaceStationError.FIND_BY_ID).withException(exception).withProperty("id", id).build());
            throw exception;
        }
    }
}
