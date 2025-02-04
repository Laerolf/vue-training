package jp.co.company.space.api.features.route.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import jp.co.company.space.utils.features.location.LocationTestDataBuilder;
import jp.co.company.space.utils.features.route.RouteTestDataBuilder;
import jp.co.company.space.utils.features.spaceShuttleModel.SpaceShuttleModelTestDataBuilder;
import jp.co.company.space.utils.features.spaceStation.SpaceStationTestDataBuilder;

/**
 * Unit tests for the {@link RouteRepository} class.
 */
@HelidonTest
public class RouteRepositoryTest {

    /**
     * The repository to test.
     */
    @Inject
    private RouteRepository repository;

    /**
     * The test data builder for routes.
     */
    private RouteTestDataBuilder routeBuilder;

    /**
     * The test data builder for locations.
     */
    private LocationTestDataBuilder locationBuilder;

    /**
     * The test data builder for spaceStations.
     */
    private SpaceStationTestDataBuilder spaceStationTestDataBuilder;

    /**
     * The test data builder for space shuttle models.
     */
    private SpaceShuttleModelTestDataBuilder spaceShuttleModelBuilder;

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    private EntityTransaction transaction;

    @BeforeEach
    void beforeEach() {
        transaction = entityManager.getTransaction();
        transaction.begin();

        locationBuilder = Optional.ofNullable(locationBuilder).orElse(new LocationTestDataBuilder(entityManager));
        spaceShuttleModelBuilder = Optional.ofNullable(spaceShuttleModelBuilder)
                .orElse(new SpaceShuttleModelTestDataBuilder(entityManager));
        spaceStationTestDataBuilder = Optional.ofNullable(spaceStationTestDataBuilder)
                .orElse(new SpaceStationTestDataBuilder(entityManager));
        routeBuilder = Optional.ofNullable(routeBuilder).orElse(new RouteTestDataBuilder(entityManager));
    }

    @AfterEach
    void afterEach() {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
    }

    @Test
    void findById_withAValidId() {
        // Given
        Location earth = locationBuilder.createAndPersist();
        SpaceStation origin = spaceStationTestDataBuilder.createAndPersist(earth);
        SpaceStation destination = spaceStationTestDataBuilder.createAndPersist(earth);
        SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.createAndPersist();

        Route route = routeBuilder.createAndPersist(origin, destination, shuttleModel);

        // When
        Optional<Route> result = repository.findById(route.getId());

        // Then
        assertTrue(result.isPresent());
    }

    @Test
    void save_oneLocation() {
        // Given
        Location earth = locationBuilder.createAndPersist();
        SpaceStation origin = spaceStationTestDataBuilder.createAndPersist(earth);
        SpaceStation destination = spaceStationTestDataBuilder.createAndPersist(earth);
        SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.createAndPersist();

        Route route = routeBuilder.create(origin, destination, shuttleModel);

        // When
        Route result = repository.save(route);

        // Then
        assertEquals(route, result);
    }

    @Test
    void save_aListOfLocations() {
        // Given
        Location earth = locationBuilder.createAndPersist();
        SpaceStation origin = spaceStationTestDataBuilder.createAndPersist(earth);
        SpaceStation destination = spaceStationTestDataBuilder.createAndPersist(earth);
        SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.createAndPersist();

        List<Route> routes = List.of(routeBuilder.create(origin, destination, shuttleModel),
                routeBuilder.create(destination, origin, shuttleModel));

        // When
        List<Route> result = repository.save(routes);

        // Then
        assertEquals(routes, result);
    }
}
