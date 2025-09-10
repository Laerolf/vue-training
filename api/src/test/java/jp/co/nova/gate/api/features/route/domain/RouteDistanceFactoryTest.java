package jp.co.nova.gate.api.features.route.domain;

import jp.co.nova.gate.api.features.location.domain.Location;
import jp.co.nova.gate.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.nova.gate.api.features.spaceStation.domain.SpaceStation;
import jp.co.nova.gate.utils.features.location.LocationTestDataBuilder;
import jp.co.nova.gate.utils.features.route.RouteTestDataBuilder;
import jp.co.nova.gate.utils.features.spaceShuttleModel.SpaceShuttleModelTestDataBuilder;
import jp.co.nova.gate.utils.features.spaceStation.SpaceStationTestDataBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link RouteDistanceFactory} class.
 */
public class RouteDistanceFactoryTest {

    private static final Location EARTH = new LocationTestDataBuilder().create();
    private static final Location MARS = new LocationTestDataBuilder().withName("Mars").withLatitude(0).withLongitude(13.1).withRadialDistance(1.5).create();
    private static final SpaceStation EARTH_STATION = new SpaceStationTestDataBuilder().create(EARTH);
    private static final SpaceStation MARS_STATION = new SpaceStationTestDataBuilder().create(MARS);

    private static final SpaceShuttleModel MX_REDLINE = new SpaceShuttleModelTestDataBuilder().withMaxSpeed(60000).create();

    private static final Route ROUTE_FROM_EARTH_TO_MARS = new RouteTestDataBuilder().create(MARS_STATION, EARTH_STATION, MX_REDLINE);

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
            assertEquals("The route to calculate the voyage distance with is missing.", exception.getMessage());
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
