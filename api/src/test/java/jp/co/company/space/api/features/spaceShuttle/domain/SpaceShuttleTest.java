package jp.co.company.space.api.features.spaceShuttle.domain;

import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.utils.features.spaceShuttle.SpaceShuttleTestDataBuilder;
import jp.co.company.space.utils.features.spaceShuttleModel.SpaceShuttleModelTestDataBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link SpaceShuttle} class.
 */
public class SpaceShuttleTest {
    /**
     * The builder to create {@link SpaceShuttle} instances with.
     */
    private final SpaceShuttleTestDataBuilder spaceShuttleBuilder = new SpaceShuttleTestDataBuilder();

    /**
     * The builder to create {@link SpaceShuttleModel} instances with.
     */
    private final SpaceShuttleModelTestDataBuilder spaceShuttleModelBuilder = new SpaceShuttleModelTestDataBuilder();

    @Nested
    class create {
        @Test
        void withValidArguments() {
            // Given
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();

            // When
            SpaceShuttle spaceShuttle = SpaceShuttle.create("A", shuttleModel);

            // Then
            assertNotNull(spaceShuttle.getId());
            assertEquals("A", spaceShuttle.getName());
            assertEquals(shuttleModel, spaceShuttle.getModel());
        }

        @Test
        void whenNameIsMissing() {
            // Given
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceShuttle.create(null, shuttleModel));

            assertEquals("The name of the space shuttle is missing.", exception.getMessage());
        }

        @Test
        void whenModelIsMissing() {
            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceShuttle.create("A", null));

            assertEquals("The model of the space shuttle is missing.", exception.getMessage());
        }
    }

    @Nested
    class reconstruct {
        @Test
        void withValidArguments() {
            // Given
            String id = UUID.randomUUID().toString();
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();

            // When
            SpaceShuttle spaceShuttle = SpaceShuttle.reconstruct(id, "A", shuttleModel);

            // Then
            assertEquals(id, spaceShuttle.getId());
            assertEquals("A", spaceShuttle.getName());
            assertEquals(shuttleModel, spaceShuttle.getModel());
        }

        @Test
        void whenIdIsMissing() {
            // Given
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceShuttle.reconstruct(null, "A", shuttleModel));

            assertEquals("The ID of the space shuttle is missing.", exception.getMessage());
        }

        @Test
        void whenNameIsMissing() {
            // Given
            String id = UUID.randomUUID().toString();
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceShuttle.reconstruct(id, null, shuttleModel));

            assertEquals("The name of the space shuttle is missing.", exception.getMessage());
        }

        @Test
        void whenModelIsMissing() {
            // Given
            String id = UUID.randomUUID().toString();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceShuttle.reconstruct(id, "A", null));

            assertEquals("The model of the space shuttle is missing.", exception.getMessage());
        }
    }

    @Nested
    class equals {
        @Test
        void whenItMatches() {
            // Given
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();
            SpaceShuttle shuttle = spaceShuttleBuilder.create(shuttleModel);
            SpaceShuttle duplicatedShuttle = SpaceShuttle.reconstruct(shuttle.getId(), shuttle.getName(),
                    shuttle.getModel());

            // Then
            assertEquals(shuttle, duplicatedShuttle);
        }

        @Test
        void whenItDoesNotMatch() {
            // Given
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();
            SpaceShuttle shuttle = spaceShuttleBuilder.create(shuttleModel);
            SpaceShuttle duplicatedShuttle = SpaceShuttle.create(shuttle.getName(), shuttle.getModel());

            // Then
            assertNotEquals(shuttle, duplicatedShuttle);
        }
    }
}
