package jp.co.company.space.api.features.spaceShuttleModel.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.utils.features.spaceShuttleModel.SpaceShuttleModelTestDataBuilder;

/**
 * Unit tests for the {@link SpaceShuttleModelRepository} class.
 */
@HelidonTest
public class SpaceShuttleModelRepositoryTest {

    /**
     * The builder to create {@link SpaceShuttleModel} instances with.
     */
    private SpaceShuttleModelTestDataBuilder testDataBuilder;

    /**
     * The repository to test.
     */
    @Inject
    private SpaceShuttleModelRepository repository;

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    private EntityTransaction transaction;

    @BeforeEach
    void beforeEach() throws Exception {
        transaction = entityManager.getTransaction();
        transaction.begin();

        testDataBuilder = Optional.ofNullable(testDataBuilder)
                .orElse(new SpaceShuttleModelTestDataBuilder(entityManager));
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
        SpaceShuttleModel shuttleModel = testDataBuilder.createAndPersist();

        // When
        Optional<SpaceShuttleModel> result = repository.findById(shuttleModel.getId());

        // Then
        assertTrue(result.isPresent());
    }

    @Test
    void save_oneShuttleModel() {
        // Given
        SpaceShuttleModel shuttleModel = testDataBuilder.createAndPersist();

        // When
        SpaceShuttleModel result = repository.save(shuttleModel);

        // Then
        assertNotNull(result);
    }

    @Test
    void save_aListOfShuttleModels() {
        // Given
        List<SpaceShuttleModel> shuttleModels = List.of(testDataBuilder.createAndPersist(),
                testDataBuilder.createAndPersist());

        // When
        List<SpaceShuttleModel> result = repository.save(shuttleModels);

        // Then
        assertEquals(shuttleModels, result);
    }
}
