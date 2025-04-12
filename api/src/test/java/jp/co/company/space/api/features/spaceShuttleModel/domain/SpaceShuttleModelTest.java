package jp.co.company.space.api.features.spaceShuttleModel.domain;

import jp.co.company.space.utils.features.spaceShuttleModel.SpaceShuttleModelTestDataBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link SpaceShuttleModel} class.
 */
public class SpaceShuttleModelTest {

    /**
     * The builder to create {@link SpaceShuttleModel} instances with.
     */
    private final SpaceShuttleModelTestDataBuilder spaceShuttleModelBuilder = new SpaceShuttleModelTestDataBuilder();

    @Nested
    class create {
        @Test
        void withValidArguments() {
            // When
            SpaceShuttleModel model = SpaceShuttleModel.create("A", 100, 10000);

            // Then
            assertNotNull(model.getId());
            assertEquals("A", model.getName());
            assertEquals(100, model.getMaxCapacity());
            assertEquals(10000, model.getMaxSpeed());
        }

        @Test
        void whenNameIsMissing() {
            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceShuttleModel.create(null, 100, 10000));

            assertEquals("The name of the space shuttle model is missing.", exception.getMessage());
        }
    }

    @Nested
    class reconstruct {
        @Test
        void withValidArguments() {
            // Given
            String id = UUID.randomUUID().toString();

            // When
            SpaceShuttleModel model = SpaceShuttleModel.reconstruct(id, "A", 100, 10000);

            // Then
            assertEquals(id, model.getId());
            assertEquals("A", model.getName());
            assertEquals(100, model.getMaxCapacity());
            assertEquals(10000, model.getMaxSpeed());
        }

        @Test
        void whenIdIsMissing() {
            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceShuttleModel.reconstruct(null, "A", 100, 10000));

            assertEquals("The ID of the space shuttle model is missing.", exception.getMessage());
        }

        @Test
        void whenNameIsMissing() {
            // Given
            String id = UUID.randomUUID().toString();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceShuttleModel.reconstruct(id, null, 100, 10000));

            assertEquals("The name of the space shuttle model is missing.", exception.getMessage());
        }
    }

    @Nested
    class equals {
        @Test
        void whenItMatches() {
            // Given
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();
            SpaceShuttleModel duplicatedModle = SpaceShuttleModel.reconstruct(shuttleModel.getId(),
                    shuttleModel.getName(), shuttleModel.getMaxCapacity(), shuttleModel.getMaxSpeed());

            // Then
            assertEquals(shuttleModel, duplicatedModle);
        }

        @Test
        void whenItDoesNotMatch() {
            // Given
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();
            SpaceShuttleModel duplicatedModle = SpaceShuttleModel.reconstruct(UUID.randomUUID().toString(),
                    shuttleModel.getName(), shuttleModel.getMaxCapacity(), shuttleModel.getMaxCapacity());

            // Then
            assertNotEquals(shuttleModel, duplicatedModle);
        }
    }

}