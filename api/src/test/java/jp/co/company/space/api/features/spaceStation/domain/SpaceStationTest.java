package jp.co.company.space.api.features.spaceStation.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.utils.features.location.LocationTestDataBuilder;

/**
 * Unit tests for the {@link SpaceStation} class.
 */
public class SpaceStationTest {

    /**
     * The builder to create {@link Location} instances with.
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

            assertEquals("The name of the space station is missing.", exception.getMessage());
        }

        @Test
        void whenCodeIsMissing() {
            // Given
            Location location = locationBuilder.create();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceStation.create("A", null, "Japan", location));

            assertEquals("The code of the space station is missing.", exception.getMessage());
        }

        @Test
        void whenLocationIsMissing() {
            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceStation.create("A", "AJ", "Japan", null));

            assertEquals("The location of the space station is missing.", exception.getMessage());
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

            assertEquals("The ID of the space station is missing.", exception.getMessage());
        }

        @Test
        void whenNameIsMissing() {
            // Given
            String id = UUID.randomUUID().toString();
            Location location = locationBuilder.create();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceStation.reconstruct(id, null, "AJ", "Japan", location));

            assertEquals("The name of the space station is missing.", exception.getMessage());
        }

        @Test
        void whenCodeIsMissing() {
            // Given
            String id = UUID.randomUUID().toString();
            Location location = locationBuilder.create();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceStation.reconstruct(id, "A", null, "Japan", location));

            assertEquals("The code of the space station is missing.", exception.getMessage());
        }

        @Test
        void whenLocationIsMissing() {
            // Given
            String id = UUID.randomUUID().toString();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> SpaceStation.reconstruct(id, "A", "AJ", "Japan", null));

            assertEquals("The location of the space station is missing.", exception.getMessage());
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
