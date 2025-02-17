package jp.co.company.space.api.features.route.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import jp.co.company.space.utils.features.location.LocationTestDataBuilder;
import jp.co.company.space.utils.features.route.RouteTestDataBuilder;
import jp.co.company.space.utils.features.spaceShuttleModel.SpaceShuttleModelTestDataBuilder;
import jp.co.company.space.utils.features.spaceStation.SpaceStationTestDataBuilder;

/**
 * Unit tests for the {@link RouteDistanceFactory} class.
 */
public class RouteDistanceFactoryTest {

    private final static LocationTestDataBuilder locationBuilder = new LocationTestDataBuilder();
    private final static SpaceStationTestDataBuilder spaceStationBuilder = new SpaceStationTestDataBuilder();
    private final static SpaceShuttleModelTestDataBuilder spaceShuttleModelBuilder = new SpaceShuttleModelTestDataBuilder();
    private final static RouteTestDataBuilder routeBuilder = new RouteTestDataBuilder();

    private final static Location EARTH = locationBuilder.create();
    private final static Location MARS = locationBuilder.withName("Mars").withLatitude(0).withLongitude(13.1).withRadialDistance(1.5).create();
    private final static SpaceStation EARTH_STATION = spaceStationBuilder.create(EARTH);
    private final static SpaceStation MARS_STATION = spaceStationBuilder.create(MARS);

    private final static SpaceShuttleModel MX_REDLINE = spaceShuttleModelBuilder.withMaxSpeed(60000).create();

    private final static Route ROUTE_FROM_EARTH_TO_MARS = routeBuilder.create(MARS_STATION, EARTH_STATION, MX_REDLINE);

    @Nested
    class create {
        @Test
        void withAllArguments() {
            // When
            RouteDistanceFactory voyageDistanceFactory = RouteDistanceFactory.create(ROUTE_FROM_EARTH_TO_MARS);

            // Then
            assertNotNull(voyageDistanceFactory);
        }

        @Test
        void whenRouteIsMissing() {
            // When
            Exception exception = assertThrows(IllegalArgumentException.class, () -> RouteDistanceFactory.create(null));

            // Then
            assertEquals("The route for the voyage distance factory is missing.", exception.getMessage());
        }
    }

    @Nested
    class calculate {
        @Test
        void happyFlow() {
            // Given
            RouteDistanceFactory routeDistanceFactory = RouteDistanceFactory.create(ROUTE_FROM_EARTH_TO_MARS);

            // When
            BigDecimal routeDistance = routeDistanceFactory.calculate();

            // Then
            assertEquals(new BigDecimal("164410536.8295705102836995"), routeDistance);
        }
    }
}
