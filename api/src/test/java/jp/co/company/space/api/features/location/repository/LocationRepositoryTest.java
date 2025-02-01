package jp.co.company.space.api.features.location.repository;

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
import jp.co.company.space.utils.features.location.LocationTestDataBuilder;

/**
 * Unit tests for the {@link LocationRepository} class.
 */
@HelidonTest
public class LocationRepositoryTest {
    /**
     * The builder to create {@link Location} instances with.
     */
    private LocationTestDataBuilder locationBuilder;

    /**
     * The repository to test.
     */
    @Inject
    private LocationRepository repository;

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

        // When
        Optional<Location> result = repository.findById(location.getId());

        // Then
        assertTrue(result.isPresent());
    }

    @Test
    void save_oneLocation() {
        // Given
        Location location = locationBuilder.createAndPersist();

        // When
        Location result = repository.save(location);

        // Then
        assertEquals(location, result);
    }

    @Test
    void save_aListOfLocations() {
        // Given
        List<Location> locations = List.of(locationBuilder.createAndPersist(), locationBuilder.createAndPersist());

        // When
        List<Location> result = repository.save(locations);

        // Then
        assertEquals(locations, result);
    }
}
