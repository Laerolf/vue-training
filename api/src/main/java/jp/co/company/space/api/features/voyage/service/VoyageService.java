package jp.co.company.space.api.features.voyage.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jp.co.company.space.api.features.pod.domain.Pod;
import jp.co.company.space.api.features.pod.domain.PodReservation;
import jp.co.company.space.api.features.pod.service.PodService;
import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.route.events.RouteServiceInit;
import jp.co.company.space.api.features.route.service.RouteService;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttleLayout;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttleLayoutFactory;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttleServiceInit;
import jp.co.company.space.api.features.spaceShuttle.service.SpaceShuttleService;
import jp.co.company.space.api.features.spaceShuttleModel.service.SpaceShuttleModelService;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.features.voyage.domain.VoyageStatus;
import jp.co.company.space.api.features.voyage.repository.VoyageRepository;
import jp.co.company.space.api.shared.exception.DomainException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A service class handling the {@link Voyage} topic.
 */
@ApplicationScoped
public class VoyageService {

    @Inject
    private VoyageRepository repository;

    @Inject
    private RouteService routeService;

    @Inject
    private SpaceShuttleService spaceShuttleService;

    @Inject
    private PodService podService;

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

    protected void onStartUp(@Observes SpaceShuttleServiceInit spaceShuttleServiceInit) {
        try {
            isSpaceShuttleServiceReady = true;

            if (isReadyToBeInitialized()) {
                loadVoyages();
            }
        } catch (Exception exception) {
            throw new RuntimeException("Failed to load the initial data to the database.", exception);
        }
    }

    protected void onStartUp(@Observes RouteServiceInit routeServiceInit) {
        try {
            isRouteServiceReady = true;

            if (isReadyToBeInitialized()) {
                loadVoyages();
            }
        } catch (DomainException exception) {
            throw new RuntimeException("Failed to load the initial data to the database.", exception);
        }
    }

    /**
     * @return True when the dependency services have been initialized, false otherwise.
     */
    private boolean isReadyToBeInitialized() {
        return isRouteServiceReady && isSpaceShuttleServiceReady;
    }

    /**
     * Loads the initial {@link Voyage} instances into the database.
     */
    private void loadVoyages() {
        try (JsonReader reader = Json.createReader(VoyageService.class.getResourceAsStream("/static/voyages.json"))) {
            List<Voyage> parsedVoyages = reader.readArray().stream().map(voyageJsonValue -> {
                try {
                    JsonObject voyageJson = voyageJsonValue.asJsonObject();

                    String id = voyageJson.getString("id");
                    ZonedDateTime departureDate = ZonedDateTime.parse(voyageJson.getString("departureDate"));
                    ZonedDateTime arrivalDate = ZonedDateTime.parse(voyageJson.getString("arrivalDate"));

                    String routeId = voyageJson.getString("routeId");
                    String spaceShuttleId = voyageJson.getString("spaceShuttleId");
                    String statusKey = voyageJson.getString("status");

                    Route route = routeService.findById(routeId).orElseThrow(() -> new NoSuchElementException("No route present"));
                    SpaceShuttle spaceShuttle = spaceShuttleService.findById(spaceShuttleId).orElseThrow();
                    VoyageStatus status = VoyageStatus.valueOf(statusKey);

                    return Voyage.reconstruct(id, departureDate, arrivalDate, status, route, spaceShuttle);
                } catch (NoSuchElementException exception) {
                    throw new DomainException(String.format("Failed to find an element for a voyage [value='%s']: %s", voyageJsonValue, exception.getMessage()));
                }
            }).collect(Collectors.toList());

            repository.save(parsedVoyages);
        } catch (JsonException | NullPointerException exception) {
            throw new RuntimeException("Failed to load the initial space voyages into the database", exception);
        }
    }

    /**
     * Gets all existing {@link Voyage} instances.
     *
     * @return A {@link List} of all {@link Voyage} instances.
     */
    public List<Voyage> getAll() {
        return repository.getAll();
    }

    /**
     * Returns an {@link Optional} {@link Voyage} instance for the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link Voyage} instance.
     */
    public Optional<Voyage> findById(String id) {
        return repository.findById(id);
    }

    /**
     * Returns a {@link List} of {@link Voyage} instances having an origin space station matching the provided space station ID.
     *
     * @param originId The space station ID to search with.
     * @return A {@link List} of {@link Voyage} instances.
     */
    public List<Voyage> getAllFrom(String originId) {
        return repository.getAllVoyagesFromOriginId(originId);
    }

    /**
     * Returns a {@link List} of {@link Voyage} instances having a destination space station matching the provided space station ID.
     *
     * @param destinationId The space station ID to search with.
     * @return A {@link List} of {@link Voyage} instances.
     */
    public List<Voyage> getAllTo(String destinationId) {
        return repository.getAllVoyagesToDestinationId(destinationId);
    }

    /**
     * Returns a {@link List} of {@link Voyage} instances having an origin space station matching the provided origin space
     * station ID and a destination space station matching the provided destination space station ID.
     *
     * @param originId      The origin space station ID to search with.
     * @param destinationId The destination space station ID to search with.
     * @return A {@link List} of {@link Voyage} instances.
     */
    public List<Voyage> getAllFromTo(String originId, String destinationId) {
        return repository.getAllVoyagesFromOriginIdToDestinationId(originId, destinationId);
    }

    /**
     * Gets a {@link List} of all {@link Pod} instances for a {@link Voyage} instance if any {@link Voyage} instance matches the provided ID.
     *
     * @param id The ID to search for.
     * @return A {@link List} of all {@link Pod} instances.
     */
    public List<Pod> getAllPodsByVoyageId(String id) {
        Voyage selectedVoyage =  findById(id).orElseThrow();

        SpaceShuttleLayout spaceShuttleLayout = new SpaceShuttleLayoutFactory(selectedVoyage.getSpaceShuttle().getModel()).create();
        List<PodReservation> reservedPods = podService.getAllPodReservationsByVoyage(selectedVoyage);

        return spaceShuttleLayout.getAllPods(reservedPods);
    }
}
