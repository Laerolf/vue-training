package jp.co.company.space.api.features.spaceShuttle.repository;

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
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.utils.features.spaceShuttle.SpaceShuttleTestDataBuilder;
import jp.co.company.space.utils.features.spaceShuttleModel.SpaceShuttleModelTestDataBuilder;

/**
 * Unit tests for the {@link SpaceShuttleRepository} class.
 */
@HelidonTest
public class SpaceShuttleRepositoryTest {
        /**
         * The builder to create {@link SpaceShuttle} instances with.
         */
        private SpaceShuttleTestDataBuilder spaceShuttleBuilder;

        /*
         * The builder to create {@link SpaceShuttleModel} instances with.
         */
        private SpaceShuttleModelTestDataBuilder spaceShuttleModelBuilder;

        /**
         * The repository to test.
         */
        @Inject
        private SpaceShuttleRepository repository;

        /**
         * The space shuttles entity manager.
         */
        @PersistenceContext(unitName = "spaceShuttles")
        private EntityManager entityManager;

        /**
         * The transaction to use.
         */
        private EntityTransaction transaction;

        @BeforeEach
        void beforeEach() {
                transaction = entityManager.getTransaction();
                transaction.begin();

                spaceShuttleBuilder = Optional.ofNullable(spaceShuttleBuilder)
                                .orElse(new SpaceShuttleTestDataBuilder(entityManager));
                spaceShuttleModelBuilder = Optional.ofNullable(spaceShuttleModelBuilder)
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
                SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.createAndPersist();
                SpaceShuttle shuttle = spaceShuttleBuilder.createAndPersist(shuttleModel);

                // When
                Optional<SpaceShuttle> result = repository.findById(shuttle.getId());

                // Then
                assertTrue(result.isPresent());
        }

        @Test
        void save_oneShuttleModel() {
                // Given
                SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.createAndPersist();
                SpaceShuttle shuttle = spaceShuttleBuilder.createAndPersist(shuttleModel);

                // When
                SpaceShuttle result = repository.save(shuttle);

                // Then
                assertEquals(shuttle, result);
        }

        @Test
        void save_aListOfShuttleModels() {
                // Given
                SpaceShuttleModel shuttleModelA = spaceShuttleModelBuilder.createAndPersist();
                SpaceShuttleModel shuttleModelB = spaceShuttleModelBuilder.createAndPersist();
                List<SpaceShuttle> shuttles = List.of(spaceShuttleBuilder.createAndPersist(shuttleModelA),
                                spaceShuttleBuilder.createAndPersist(shuttleModelB));

                // When
                List<SpaceShuttle> result = repository.save(shuttles);

                // Then
                assertEquals(shuttles, result);
        }
}
