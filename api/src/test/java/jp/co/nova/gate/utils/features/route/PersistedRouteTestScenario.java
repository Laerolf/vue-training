package jp.co.nova.gate.utils.features.route;

import jakarta.inject.Inject;
import jp.co.nova.gate.api.features.location.domain.Location;
import jp.co.nova.gate.api.features.location.repository.LocationRepository;
import jp.co.nova.gate.api.features.route.domain.Route;
import jp.co.nova.gate.api.features.route.repository.RouteRepository;
import jp.co.nova.gate.api.features.spaceStation.domain.SpaceStation;
import jp.co.nova.gate.api.features.spaceStation.repository.SpaceStationRepository;
import jp.co.nova.gate.api.shared.exception.DomainException;
import jp.co.nova.gate.utils.features.location.LocationTestDataBuilder;
import jp.co.nova.gate.utils.features.spaceShuttleModel.PersistedSpaceShuttleModelTestScenario;
import jp.co.nova.gate.utils.features.spaceStation.SpaceStationTestDataBuilder;
import jp.co.nova.gate.utils.shared.testScenario.TestScenario;
import jp.co.nova.gate.utils.shared.testScenario.TestScenarioException;

/**
 * A {@link TestScenario} with a persisted route.
 * Nested scenarios: {@link PersistedSpaceShuttleModelTestScenario}
 */
public class PersistedRouteTestScenario implements TestScenario {

    private static final String DESTINATION_LOCATION_NAME = "Mars";
    private static final double DESTINATION_LOCATION_LATITUDE = 0;
    private static final double DESTINATION_LOCATION_LONGITUDE = 13.1;
    private static final double DESTINATION_LOCATION_RADIAL_DISTANCE = 1.5;

    @Inject
    private PersistedSpaceShuttleModelTestScenario persistedSpaceShuttleModelTestScenario;

    @Inject
    private RouteRepository repository;

    @Inject
    private SpaceStationRepository spaceStationRepository;

    @Inject
    private LocationRepository locationRepository;

    private Route persistedRoute;

    protected PersistedRouteTestScenario() {
    }

    @Override
    public void setup() {
        try {
            persistedSpaceShuttleModelTestScenario.setup();

            Location origin = new LocationTestDataBuilder().create();
            origin = locationRepository.save(origin);
            SpaceStation originStation = new SpaceStationTestDataBuilder().create(origin);
            originStation = spaceStationRepository.save(originStation);

            Location destination = new LocationTestDataBuilder()
                    .withName(DESTINATION_LOCATION_NAME)
                    .withLatitude(DESTINATION_LOCATION_LATITUDE)
                    .withLongitude(DESTINATION_LOCATION_LONGITUDE)
                    .withRadialDistance(DESTINATION_LOCATION_RADIAL_DISTANCE)
                    .create();

            destination = locationRepository.save(destination);
            SpaceStation destinationStation = new SpaceStationTestDataBuilder().create(destination);
            destinationStation = spaceStationRepository.save(destinationStation);

            Route route = new RouteTestDataBuilder().create(
                    originStation,
                    destinationStation,
                    persistedSpaceShuttleModelTestScenario.getPersistedSpaceShuttleModel()
            );
            persistedRoute = repository.save(route);
        } catch (TestScenarioException | DomainException exception) {
            throw new TestScenarioException("Failed to setup a route test scenario", exception);
        }
    }

    public Route getPersistedRoute() {
        return persistedRoute;
    }
}
