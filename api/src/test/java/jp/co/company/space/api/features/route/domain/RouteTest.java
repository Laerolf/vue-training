package jp.co.company.space.api.features.route.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import jp.co.company.space.utils.features.location.LocationTestDataBuilder;
import jp.co.company.space.utils.features.spaceShuttleModel.SpaceShuttleModelTestDataBuilder;
import jp.co.company.space.utils.features.spaceStation.SpaceStationTestDataBuilder;

/**
 * Unit tests for the {@link Route} class.
 */
public class RouteTest {
    /**
     * The test data builder for locations.
     */
    private static final LocationTestDataBuilder locationBuilder = new LocationTestDataBuilder();

    /**
     * The test data builder for space station.
     */
    private static final SpaceStationTestDataBuilder spaceStationBuilder = new SpaceStationTestDataBuilder();

    /**
     * The test data builder for space shuttle models.
     */
    private static final SpaceShuttleModelTestDataBuilder spaceShuttleModelBuilder = new SpaceShuttleModelTestDataBuilder();

    @Nested
    class create {
        @Test
        void withValidArguments() {
            // Given
            Location earth = locationBuilder.create();
            SpaceStation origin = spaceStationBuilder.create(earth);
            SpaceStation destination = spaceStationBuilder.create(earth);
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();

            // When
            Route route = Route.create(origin, destination, shuttleModel);

            // Then
            assertNotNull(route.getId());
            assertEquals(origin, route.getOrigin());
            assertEquals(destination, route.getDestination());
            assertEquals(shuttleModel, route.getShuttleModel());
        }

        @Test
        void whenOriginIsMissing() {
            // Given
            Location earth = locationBuilder.create();
            SpaceStation destination = spaceStationBuilder.create(earth);
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> Route.create(null, destination, shuttleModel));

            assertEquals("The origin of the route is missing.", exception.getMessage());
        }

        @Test
        void whenDestinationIsMissing() {
            // Given
            Location earth = locationBuilder.create();
            SpaceStation origin = spaceStationBuilder.create(earth);
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> Route.create(origin, null, shuttleModel));

            assertEquals("The destination of the route is missing.", exception.getMessage());
        }

        @Test
        void whenSpaceShuttleModelIsMissing() {
            // Given
            Location earth = locationBuilder.create();
            SpaceStation origin = spaceStationBuilder.create(earth);
            SpaceStation destination = spaceStationBuilder.create(earth);

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> Route.create(origin, destination, null));

            assertEquals("The space shuttle model for the route is missing.", exception.getMessage());
        }
    }

    @Nested
    class reconstruct {
        @Test
        void withValidArguments() {
            // Given
            String id = UUID.randomUUID().toString();
            Location earth = locationBuilder.create();
            SpaceStation origin = spaceStationBuilder.create(earth);
            SpaceStation destination = spaceStationBuilder.create(earth);
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();

            // When
            Route route = Route.reconstruct(id, origin, destination, shuttleModel);

            // Then
            assertEquals(id, route.getId());
            assertEquals(origin, route.getOrigin());
            assertEquals(destination, route.getDestination());
            assertEquals(shuttleModel, route.getShuttleModel());
        }

        @Test
        void whenIdIsMissing() {
            // Given
            Location earth = locationBuilder.create();
            SpaceStation origin = spaceStationBuilder.create(earth);
            SpaceStation destination = spaceStationBuilder.create(earth);
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> Route.reconstruct(null, origin, destination, shuttleModel));

            assertEquals("The ID of the route is missing.", exception.getMessage());
        }

        @Test
        void whenOriginIsMissing() {
            // Given
            String id = UUID.randomUUID().toString();
            Location earth = locationBuilder.create();
            SpaceStation destination = spaceStationBuilder.create(earth);
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> Route.reconstruct(id, null, destination, shuttleModel));

            assertEquals("The origin of the route is missing.", exception.getMessage());
        }

        @Test
        void whenDestinationIsMissing() {
            // Given
            String id = UUID.randomUUID().toString();
            Location earth = locationBuilder.create();
            SpaceStation origin = spaceStationBuilder.create(earth);
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> Route.reconstruct(id, origin, null, shuttleModel));

            assertEquals("The destination of the route is missing.", exception.getMessage());
        }

        @Test
        void whenSpaceShuttleModelIsMissing() {
            // Given
            String id = UUID.randomUUID().toString();
            Location earth = locationBuilder.create();
            SpaceStation origin = spaceStationBuilder.create(earth);
            SpaceStation destination = spaceStationBuilder.create(earth);

            // Then
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> Route.reconstruct(id, origin, destination, null));

            assertEquals("The space shuttle model for the route is missing.", exception.getMessage());
        }

        @Test
        void whenMatched() {
            // Given
            Location earth = locationBuilder.create();
            Location moon = locationBuilder.create();
            SpaceStation earthStation = spaceStationBuilder.create(earth);
            SpaceStation moonStation = spaceStationBuilder.create(moon);
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();

            Route routeToTheMoon = Route.create(earthStation, moonStation, shuttleModel);
            Route duplicatedRouteToTheMoon = Route.reconstruct(routeToTheMoon.getId(), earthStation, moonStation,
                    shuttleModel);

            // Then
            assertEquals(routeToTheMoon, duplicatedRouteToTheMoon);
        }

        @Test
        void whenNotMatched() {
            // Given
            Location earth = locationBuilder.create();
            Location moon = locationBuilder.create();
            SpaceStation earthStation = spaceStationBuilder.create(earth);
            SpaceStation moonStation = spaceStationBuilder.create(moon);
            SpaceShuttleModel shuttleModel = spaceShuttleModelBuilder.create();

            Route routeToTheMoon = Route.create(earthStation, moonStation, shuttleModel);
            Route routeToEarth = Route.create(moonStation, earthStation, shuttleModel);

            // Then
            assertNotEquals(routeToTheMoon, routeToEarth);
        }
    }
}
