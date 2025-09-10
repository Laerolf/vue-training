package jp.co.nova.gate.api.features.spaceStation.domain;

import jp.co.nova.gate.api.features.location.domain.Location;
import jp.co.nova.gate.utils.features.location.LocationTestDataBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link SpaceStation} class.
 */
public class SpaceStationTest {

    /**
     * The builder to create {@link Location}s with.
     */
    private final LocationTestDataBuilder locationBuilder = new LocationTestDataBuilder();

    @Nested
    class create {
        @Test
        void withValidArguments() {
            // Given
            Location location = locationBuilder.create();

            // When
            SpaceStation spaceStation = SpaceStation.create("A", "AJ", "Japan", location);

            // Then
            assertNotNull(spaceStation.getId());
            assertEquals("A", spaceStation.getName());
            assertEquals("AJ", spaceStation.getCode());
            assertEquals("Japan", spaceStation.getCountry());
            assertEquals(location, spaceStation.getLocation());
        }

        @Test
        void whenNameIsMissing() {
            // Given
            Location location = locationBuilder.create();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceStation.create(null, "AJ", "Japan", location));

            assertEquals("The name of a space station is missing.", exception.getMessage());
        }

        @Test
        void whenCodeIsMissing() {
            // Given
            Location location = locationBuilder.create();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceStation.create("A", null, "Japan", location));

            assertEquals("The code of a space station is missing.", exception.getMessage());
        }

        @Test
        void whenLocationIsMissing() {
            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceStation.create("A", "AJ", "Japan", null));

            assertEquals("The location of a space station is missing.", exception.getMessage());
        }
    }

    @Nested
    class reconstruct {
        @Test
        void withValidArguments() {
            // Given
            String id = UUID.randomUUID().toString();
            Location location = locationBuilder.create();

            // When
            SpaceStation spaceStation = SpaceStation.reconstruct(id, "A", "AJ", "Japan", location);

            // Then
            assertEquals(id, spaceStation.getId());
            assertEquals("A", spaceStation.getName());
            assertEquals("AJ", spaceStation.getCode());
            assertEquals("Japan", spaceStation.getCountry());
            assertEquals(location, spaceStation.getLocation());
        }

        @Test
        void whenIdIsMissing() {
            // Given
            Location location = locationBuilder.create();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceStation.reconstruct(null, "A", "AJ", "Japan", location));

            assertEquals("The ID of a space station is missing.", exception.getMessage());
        }

        @Test
        void whenNameIsMissing() {
            // Given
            String id = UUID.randomUUID().toString();
            Location location = locationBuilder.create();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceStation.reconstruct(id, null, "AJ", "Japan", location));

            assertEquals("The name of a space station is missing.", exception.getMessage());
        }

        @Test
        void whenCodeIsMissing() {
            // Given
            String id = UUID.randomUUID().toString();
            Location location = locationBuilder.create();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceStation.reconstruct(id, "A", null, "Japan", location));

            assertEquals("The code of a space station is missing.", exception.getMessage());
        }

        @Test
        void whenLocationIsMissing() {
            // Given
            String id = UUID.randomUUID().toString();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceStation.reconstruct(id, "A", "AJ", "Japan", null));

            assertEquals("The location of a space station is missing.", exception.getMessage());
        }
    }

    @Nested
    class equals {
        @Test
        void whenItMatches() {
            // Given
            Location location = locationBuilder.create();
            SpaceStation spaceStation = SpaceStation.create("A", "AJ", "Japan", location);
            SpaceStation duplicatedSpaceStation = SpaceStation.reconstruct(spaceStation.getId(), spaceStation.getName(),
                    spaceStation.getCode(), spaceStation.getCountry(), spaceStation.getLocation());

            // Then
            assertEquals(spaceStation, duplicatedSpaceStation);
        }

        @Test
        void whenItDoesNotMatch() {
            // Given
            Location location = locationBuilder.create();
            SpaceStation spaceStationA = SpaceStation.create("A", "AJ", "Japan", location);
            SpaceStation spaceStationB = SpaceStation.create("B", "AJ", "Japan", location);

            // Then
            assertNotEquals(spaceStationA, spaceStationB);
        }
    }
}
