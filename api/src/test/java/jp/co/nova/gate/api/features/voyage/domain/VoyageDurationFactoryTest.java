package jp.co.nova.gate.api.features.voyage.domain;

import jp.co.nova.gate.api.features.location.domain.Location;
import jp.co.nova.gate.api.features.route.domain.Route;
import jp.co.nova.gate.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.nova.gate.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.nova.gate.api.features.spaceStation.domain.SpaceStation;
import jp.co.nova.gate.utils.features.location.LocationTestDataBuilder;
import jp.co.nova.gate.utils.features.route.RouteTestDataBuilder;
import jp.co.nova.gate.utils.features.spaceShuttle.SpaceShuttleTestDataBuilder;
import jp.co.nova.gate.utils.features.spaceShuttleModel.SpaceShuttleModelTestDataBuilder;
import jp.co.nova.gate.utils.features.spaceStation.SpaceStationTestDataBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link VoyageDurationFactory} class.
 */
public class VoyageDurationFactoryTest {

    private static final LocationTestDataBuilder LOCATION_BUILDER = new LocationTestDataBuilder();
    private static final SpaceStationTestDataBuilder SPACE_STATION_BUILDER = new SpaceStationTestDataBuilder();
    private static final SpaceShuttleModelTestDataBuilder SPACE_SHUTTLE_MODEL_BUILDER = new SpaceShuttleModelTestDataBuilder();
    private static final RouteTestDataBuilder ROUTE_BUILDER = new RouteTestDataBuilder();
    private static final SpaceShuttleTestDataBuilder SPACE_SHUTTLE_BUILDER = new SpaceShuttleTestDataBuilder();

    private static final Location EARTH = LOCATION_BUILDER.create();
    private static final Location MARS = LOCATION_BUILDER.withName("Mars").withLatitude(0).withLongitude(13.1).withRadialDistance(1.5).create();
    private static final SpaceStation EARTH_STATION = SPACE_STATION_BUILDER.create(EARTH);
    private static final SpaceStation MARS_STATION = SPACE_STATION_BUILDER.create(MARS);

    private static final SpaceShuttleModel MX_REDLINE = SPACE_SHUTTLE_MODEL_BUILDER.withMaxSpeed(250000).create();
    private static final SpaceShuttle MX_REDLINE_SPACE_SHUTTLE = SPACE_SHUTTLE_BUILDER.withModel(MX_REDLINE).create();

    private static final Route ROUTE_FROM_EARTH_TO_MARS = ROUTE_BUILDER.create(MARS_STATION, EARTH_STATION, MX_REDLINE);

    @Nested
    class create {
        @Test
        void withAllParameters() {
            // When
            VoyageDurationFactory voyageDurationFactory = VoyageDurationFactory.create(ROUTE_FROM_EARTH_TO_MARS, MX_REDLINE_SPACE_SHUTTLE);

            // Then
            assertNotNull(voyageDurationFactory);
        }

        @Test
        void whenRouteIsMissing() {
            // When
            Exception exception = assertThrows(IllegalArgumentException.class, () -> VoyageDurationFactory.create(null, MX_REDLINE_SPACE_SHUTTLE));

            // Then
            assertEquals("Unable to calculate the duration of a voyage.", exception.getMessage());
        }

        @Test
        void whenSpaceShuttleIsMissing() {
            // When
            Exception exception = assertThrows(IllegalArgumentException.class, () -> VoyageDurationFactory.create(ROUTE_FROM_EARTH_TO_MARS, null));

            // Then
            assertEquals("Unable to calculate the duration of a voyage.", exception.getMessage());
        }
    }

    @Nested
    class calculate {
        @Test
        void happyFlow() {
            // Given
            VoyageDurationFactory voyageDurationFactory = VoyageDurationFactory.create(ROUTE_FROM_EARTH_TO_MARS, MX_REDLINE_SPACE_SHUTTLE);

            // When
            Duration voyageDuration = voyageDurationFactory.calculate();

            // Then
            assertEquals(27, voyageDuration.toDays());
        }
    }
}
