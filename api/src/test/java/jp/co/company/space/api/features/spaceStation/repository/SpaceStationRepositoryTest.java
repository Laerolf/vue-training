package jp.co.company.space.api.features.spaceStation.repository;

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
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import jp.co.company.space.utils.features.location.LocationTestDataBuilder;
import jp.co.company.space.utils.features.spaceStation.SpaceStationTestDataBuilder;

/**
 * Unit tests for the {@link SpaceStationRepository} class.
 */
@HelidonTest
public class SpaceStationRepositoryTest {
    /**
     * The builder to create {@link SpaceStation} instances with.
     */
    private SpaceStationTestDataBuilder spaceStationBuilder;

    /**
     * The builder to create {@link Location} instances with.
     */
    private LocationTestDataBuilder locationBuilder;

    /**
     * The repository to test.
     */
    @Inject
    private SpaceStationRepository repository;

    /**
     * The locations entity manager.
     */
    @PersistenceContext(unitName = "locations")
    private EntityManager entityManager;

    /**
     * The transaction to use.
     */
    private EntityTransaction transaction;

    @BeforeEach
    void beforeEach() {
        transaction = entityManager.getTransaction();
        transaction.begin();

        spaceStationBuilder = Optional.ofNullable(spaceStationBuilder)
                .orElse(new SpaceStationTestDataBuilder(entityManager));
        locationBuilder = Optional.ofNullable(locationBuilder).orElse(new LocationTestDataBuilder(entityManager));
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
        Location location = locationBuilder.createAndPersist();
        SpaceStation spaceStation = spaceStationBuilder.createAndPersist(location);

        // When
        Optional<SpaceStation> result = repository.findById(spaceStation.getId());

        // Then
        assertTrue(result.isPresent());
    }

    @Test
    void save_oneShuttleModel() {
        // Given
        Location location = locationBuilder.createAndPersist();
        SpaceStation spaceStation = spaceStationBuilder.createAndPersist(location);

        // When
        SpaceStation result = repository.save(spaceStation);

        // Then
        assertEquals(spaceStation, result);
    }

    @Test
    void save_aListOfShuttleModels() {
        // Given
        Location locationA = locationBuilder.createAndPersist();
        Location locationB = locationBuilder.createAndPersist();
        List<SpaceStation> location = List.of(spaceStationBuilder.createAndPersist(locationA),
                spaceStationBuilder.createAndPersist(locationB));

        // When
        List<SpaceStation> result = repository.save(location);

        // Then
        assertEquals(location, result);
    }
}
