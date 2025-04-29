package jp.co.company.space.api.features.voyage.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jp.co.company.space.api.features.location.exception.LocationError;
import jp.co.company.space.api.features.pod.domain.Pod;
import jp.co.company.space.api.features.pod.domain.PodReservation;
import jp.co.company.space.api.features.pod.service.PodReservationService;
import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.route.events.RouteServiceInit;
import jp.co.company.space.api.features.route.exception.RouteError;
import jp.co.company.space.api.features.route.exception.RouteException;
import jp.co.company.space.api.features.route.service.RouteService;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttleServiceInit;
import jp.co.company.space.api.features.spaceShuttle.exception.SpaceShuttleError;
import jp.co.company.space.api.features.spaceShuttle.exception.SpaceShuttleException;
import jp.co.company.space.api.features.spaceShuttle.service.SpaceShuttleService;
import jp.co.company.space.api.features.spaceShuttleModel.service.SpaceShuttleModelService;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.features.voyage.domain.VoyageStatus;
import jp.co.company.space.api.features.voyage.exception.VoyageError;
import jp.co.company.space.api.features.voyage.exception.VoyageException;
import jp.co.company.space.api.features.voyage.exception.VoyageRuntimeException;
import jp.co.company.space.api.features.voyage.repository.VoyageRepository;
import jp.co.company.space.api.shared.exception.DomainException;
import jp.co.company.space.api.shared.util.LogBuilder;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * A service class handling the {@link Voyage} topic.
 */
@ApplicationScoped
public class VoyageService {

    private static final Logger LOGGER = Logger.getLogger(VoyageService.class.getName());

    @Inject
    private VoyageRepository repository;

    @Inject
    private RouteService routeService;

    @Inject
    private SpaceShuttleService spaceShuttleService;

    @Inject
    private PodReservationService podReservationService;

    /**
     * Is the {@link SpaceShuttleModelService} ready to be used?
     */
    private boolean isSpaceShuttleServiceReady;

    /**
     * Is the {@link RouteService} ready to be used?
     */
    private boolean isRouteServiceReady;

    protected VoyageService() {
    }

    protected void onStartUp(@Observes SpaceShuttleServiceInit spaceShuttleServiceInit) throws VoyageRuntimeException {
        try {
            isSpaceShuttleServiceReady = true;

            if (isReadyToBeInitialized()) {
                initialize();
            }
        } catch (Exception exception) {
            LOGGER.severe(new LogBuilder(LocationError.START_SERVICE).withException(exception).build());
            throw new VoyageRuntimeException(VoyageError.START_SERVICE, exception);
        }
    }

    protected void onStartUp(@Observes RouteServiceInit routeServiceInit) throws VoyageRuntimeException {
        try {
            isRouteServiceReady = true;

            if (isReadyToBeInitialized()) {
                initialize();
            }
        } catch (DomainException exception) {
            LOGGER.severe(new LogBuilder(LocationError.START_SERVICE).withException(exception).build());
            throw new VoyageRuntimeException(VoyageError.START_SERVICE, exception);
        }
    }

    /**
     * @return True when the dependency services have been initialized, false otherwise.
     */
    private boolean isReadyToBeInitialized() {
        return isRouteServiceReady && isSpaceShuttleServiceReady;
    }

    /**
     * Initializes this service.
     */
    private void initialize() throws VoyageException {
        try {
            LOGGER.info(new LogBuilder("Initializing the voyage service.").build());
            loadVoyages();
            LOGGER.info(new LogBuilder("The voyage service is ready!").build());
        } catch (VoyageException exception) {
            LOGGER.warning(new LogBuilder(VoyageError.START_SERVICE).withException(exception).build());
            throw new VoyageException(VoyageError.START_SERVICE, exception);
        }
    }

    /**
     * Loads the initial {@link Voyage} instances into the database.
     */
    private void loadVoyages() throws VoyageException {
        try (JsonReader reader = Json.createReader(VoyageService.class.getResourceAsStream("/static/voyages.json"))) {
            List<Voyage> parsedVoyages = reader.readArray().stream().map(voyageJsonValue -> {
                JsonObject voyageJson = voyageJsonValue.asJsonObject();

                String id = voyageJson.getString("id");
                ZonedDateTime departureDate = ZonedDateTime.parse(voyageJson.getString("departureDate"));
                ZonedDateTime arrivalDate = ZonedDateTime.parse(voyageJson.getString("arrivalDate"));

                String routeId = voyageJson.getString("routeId");
                String spaceShuttleId = voyageJson.getString("spaceShuttleId");
                String statusKey = voyageJson.getString("status");

                Route route = routeService.findById(routeId).orElseThrow(() -> new RouteException(RouteError.FIND_BY_ID));
                SpaceShuttle spaceShuttle = spaceShuttleService.findById(spaceShuttleId).orElseThrow(() -> new SpaceShuttleException(SpaceShuttleError.FIND_BY_ID));
                VoyageStatus status = VoyageStatus.valueOf(statusKey);

                return Voyage.reconstruct(id, departureDate, arrivalDate, status, route, spaceShuttle);
            }).collect(Collectors.toList());

            repository.save(parsedVoyages);
            LOGGER.info(new LogBuilder(String.format("Created %d voyages.", parsedVoyages.size())).build());
        } catch (JsonException | NullPointerException | DomainException exception) {
            LOGGER.warning(new LogBuilder(VoyageError.LOAD_INITIAL_DATA).withException(exception).build());
            throw new VoyageException(VoyageError.LOAD_INITIAL_DATA, exception);
        }
    }

    /**
     * Gets all existing {@link Voyage} instances.
     *
     * @return A {@link List} of all {@link Voyage} instances.
     */
    public List<Voyage> getAll() throws VoyageException {
        try {
            return repository.getAll();
        } catch (VoyageException exception) {
            LOGGER.warning(new LogBuilder(VoyageError.GET_ALL).withException(exception).build());
            throw exception;
        }
    }

    /**
     * Returns an {@link Optional} {@link Voyage} instance for the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link Voyage} instance.
     */
    public Optional<Voyage> findById(String id) throws VoyageException {
        try {
            return repository.findById(id);
        } catch (VoyageException exception) {
            LOGGER.warning(new LogBuilder(VoyageError.FIND_BY_ID).withException(exception).withProperty("id", id).build());
            throw exception;
        }
    }

    /**
     * Returns a {@link List} of {@link Voyage} instances having an origin space station matching the provided space station ID.
     *
     * @param originId The space station ID to search with.
     * @return A {@link List} of {@link Voyage} instances.
     */
    public List<Voyage> getAllFrom(String originId) throws VoyageException {
        try {
            return repository.getAllVoyagesByOriginId(originId);
        } catch (VoyageException exception) {
            LOGGER.warning(new LogBuilder(VoyageError.GET_ALL_BY_ORIGIN_ID).withException(exception).withProperty("originId", originId).build());
            throw exception;
        }
    }

    /**
     * Returns a {@link List} of {@link Voyage} instances having a destination space station matching the provided space station ID.
     *
     * @param destinationId The space station ID to search with.
     * @return A {@link List} of {@link Voyage} instances.
     */
    public List<Voyage> getAllTo(String destinationId) throws VoyageException {
        try {
            return repository.getAllVoyagesByDestinationId(destinationId);
        } catch (VoyageException exception) {
            LOGGER.warning(new LogBuilder(VoyageError.GET_ALL_BY_DESTINATION_ID).withException(exception).withProperty("destinationId", destinationId).build());
            throw exception;
        }
    }

    /**
     * Returns a {@link List} of {@link Voyage} instances having an origin space station matching the provided origin space
     * station ID and a destination space station matching the provided destination space station ID.
     *
     * @param originId      The origin space station ID to search with.
     * @param destinationId The destination space station ID to search with.
     * @return A {@link List} of {@link Voyage} instances.
     */
    public List<Voyage> getAllFromTo(String originId, String destinationId) throws VoyageException {
        try {
            return repository.getAllVoyagesByOriginIdAndDestinationId(originId, destinationId);
        } catch (VoyageException exception) {
            LOGGER.warning(
                    new LogBuilder(VoyageError.GET_ALL_BY_ORIGIN_ID_AND_DESTINATION_ID)
                            .withException(exception)
                            .withProperty("originId", originId)
                            .withProperty("originId", destinationId)
                            .build()
            );
            throw exception;
        }
    }

    /**
     * Gets a {@link List} of all {@link Pod} instances for a {@link Voyage} instance if any {@link Voyage} instance matches the provided ID.
     *
     * @param id The ID to search for.
     * @return A {@link List} of {@link Pod} instances.
     */
    public List<Pod> getAllPodsByVoyageId(String id) throws VoyageException {
        try {
            Voyage selectedVoyage = findById(id).orElseThrow(() -> new VoyageException(VoyageError.FIND_BY_ID));
            return getAllPodsByVoyage(selectedVoyage);
        } catch (VoyageException exception) {
            LOGGER.warning(
                    new LogBuilder(VoyageError.GET_ALL_PODS_BY_VOYAGE_ID)
                            .withException(exception)
                            .withProperty("id", id)
                            .build()
            );
            throw exception;
        }
    }

    /**
     * Gets a {@link List} of all {@link Pod} instances for a {@link Voyage} instance if any {@link Voyage} instance matches the provided voyage instance.
     *
     * @param voyage The voyage to search for.
     * @return A {@link List} of {@link Pod} instances.
     */
    public List<Pod> getAllPodsByVoyage(Voyage voyage) throws VoyageException {
        try {
            List<PodReservation> reservedPods = podReservationService.getAllPodReservationsByVoyage(voyage);
            return voyage.getSpaceShuttle().getLayout().getAllPodsWithAvailability(reservedPods);
        } catch (DomainException exception) {
            LOGGER.warning(
                    new LogBuilder(VoyageError.GET_ALL_PODS_BY_VOYAGE)
                            .withException(exception)
                            .build()
            );
            throw new VoyageException(VoyageError.GET_ALL_PODS_BY_VOYAGE, exception);
        }
    }
}
