package jp.co.company.space.api.features.location.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Location} class.
 */
public class LocationTest {

    @Nested
    class create {
        @Test
        void withValidArguments() {
            // When
            Location location = Location.create("Earth", 0, 0, 27000);

            // Then
            assertEquals("Earth", location.getName());
            assertEquals(0, location.getLatitude());
            assertEquals(0, location.getLongitude());
            assertEquals(27000, location.getRadialDistance());
        }

        @Test
        void whenNameIsMissing() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Location.create(null, 0, 0, 27000));

            // Then
            assertEquals("The name of the location is missing.", exception.getMessage());
        }
    }

    @Nested
    class reconstruct {
        @Test
        void withValidArguments() {
            // When
            Location location = Location.reconstruct("1", "Earth", 0, 0, 27000);

            // Then
            assertEquals("1", location.getId());
            assertEquals("Earth", location.getName());
            assertEquals(0, location.getLatitude());
            assertEquals(0, location.getLongitude());
            assertEquals(27000, location.getRadialDistance());
        }

        @Test
        void whenIdIsMissing() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Location.reconstruct(null, "Earth", 0, 0, 27000));

            // Then
            assertEquals("The ID of the location is missing.", exception.getMessage());
        }

        @Test
        void whenNameIsMissing() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Location.reconstruct("1", null, 0, 0, 27000));

            // Then
            assertEquals("The name of the location is missing.", exception.getMessage());
        }
    }

    @Test
    void whenMatched() {
        // Given
        Location earth = Location.create("Earth", 0, 0, 27000);
        Location duplicatedEarth = Location.reconstruct(earth.getId(), "Earth", 0, 0, 27000);

        // Then
        assertEquals(duplicatedEarth, earth);
    }

    @Test
    void whenNotMatched() {
        // Given
        Location earth = Location.create("Earth", 0, 326, 1);
        Location mars = Location.create("Mars", 0, 13.1, 1.5);

        // Then
        assertNotEquals(mars, earth);
    }
}
