package jp.co.company.space.api.features.voyage.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import jp.co.company.space.shared.DomainException;
import jp.co.company.space.utils.features.location.LocationTestDataBuilder;
import jp.co.company.space.utils.features.route.RouteTestDataBuilder;
import jp.co.company.space.utils.features.spaceShuttle.SpaceShuttleTestDataBuilder;
import jp.co.company.space.utils.features.spaceShuttleModel.SpaceShuttleModelTestDataBuilder;
import jp.co.company.space.utils.features.spaceStation.SpaceStationTestDataBuilder;

/**
 * Unit tests for the {@link Voyage} class.
 */
public class VoyageTest {

    private final static LocationTestDataBuilder LOCATION_BUILDER = new LocationTestDataBuilder();
    private final static SpaceStationTestDataBuilder SPACE_STATION_BUILDER = new SpaceStationTestDataBuilder();
    private final static SpaceShuttleModelTestDataBuilder SPACE_SHUTTLE_MODEL_BUILDER = new SpaceShuttleModelTestDataBuilder();
    private final static RouteTestDataBuilder ROUTE_BUILDER = new RouteTestDataBuilder();
    private final static SpaceShuttleTestDataBuilder SPACE_SHUTTLE_BUILDER = new SpaceShuttleTestDataBuilder();

    private final static Location EARTH = LOCATION_BUILDER.create();
    private final static Location MARS = LOCATION_BUILDER.withName("Mars").withLatitude(0).withLongitude(13.1).withRadialDistance(1.5).create();
    private final static SpaceStation EARTH_STATION = SPACE_STATION_BUILDER.create(EARTH);
    private final static SpaceStation MARS_STATION = SPACE_STATION_BUILDER.create(MARS);

    private final static SpaceShuttleModel MX_REDLINE = SPACE_SHUTTLE_MODEL_BUILDER.withMaxSpeed(60000).create();
    private final static SpaceShuttle EXAMPLE_SHUTTLE = SPACE_SHUTTLE_BUILDER.create(MX_REDLINE);

    private static final String EXAMPLE_ID = UUID.randomUUID().toString();
    private static final ZonedDateTime EXAMPLE_DEPARTURE_DATE = ZonedDateTime.now();
    private static final VoyageStatus EXAMPLE_STATUS = VoyageStatus.SCHEDULED;
    private static final Route EXAMPLE_ROUTE = ROUTE_BUILDER.create(EARTH_STATION, MARS_STATION, MX_REDLINE);

    private final static Duration EXAMPLE_VOYAGE_DURATION = VoyageDurationFactory.create(EXAMPLE_ROUTE, EXAMPLE_SHUTTLE).calculate();
    private final static ZonedDateTime EXAMPLE_ARRIVAL_DATE = EXAMPLE_DEPARTURE_DATE.plus(EXAMPLE_VOYAGE_DURATION);

    @Nested
    class create {
        @Test
        void withValidParameters() {
            // When
            Voyage voyage = Voyage.create(EXAMPLE_DEPARTURE_DATE, EXAMPLE_ROUTE, EXAMPLE_SHUTTLE);

            // Then
            assertNotNull(voyage.getId());
            assertEquals(EXAMPLE_DEPARTURE_DATE, voyage.getDepartureDate());
            assertEquals(EXAMPLE_ARRIVAL_DATE, voyage.getArrivalDate());
            assertEquals(EXAMPLE_STATUS, voyage.getStatus());
            assertEquals(EXAMPLE_ROUTE, voyage.getRoute());
            assertEquals(EXAMPLE_SHUTTLE, voyage.getSpaceShuttle());
        }

        @Test
        void withoutDepartureDate() {
            // When
            Exception exception = assertThrows(IllegalArgumentException.class, () -> Voyage.create(null, EXAMPLE_ROUTE, EXAMPLE_SHUTTLE));

            // Then
            assertEquals("The departure date of the voyage is missing.", exception.getMessage());
        }

        @Test
        void withoutRoute() {
            // When
            Exception exception = assertThrows(IllegalArgumentException.class, () -> Voyage.create(EXAMPLE_DEPARTURE_DATE, null, EXAMPLE_SHUTTLE));

            // Then
            assertEquals("The route of the voyage is missing.", exception.getMessage());
        }

        @Test
        void withoutSpaceShuttle() {
            // When
            Exception exception = assertThrows(IllegalArgumentException.class, () -> Voyage.create(EXAMPLE_DEPARTURE_DATE, EXAMPLE_ROUTE, null));

            // Then
            assertEquals("The space shuttle of the voyage is missing.", exception.getMessage());
        }
    }

    @Nested
    class reconstruct {
        @Test
        void withValidParameters() {
            // When
            Voyage voyage = Voyage.reconstruct(EXAMPLE_ID, EXAMPLE_DEPARTURE_DATE, EXAMPLE_ARRIVAL_DATE, EXAMPLE_STATUS, EXAMPLE_ROUTE, EXAMPLE_SHUTTLE);

            // Then
            assertEquals(EXAMPLE_ID, voyage.getId());
            assertEquals(EXAMPLE_DEPARTURE_DATE, voyage.getDepartureDate());
            assertEquals(EXAMPLE_ARRIVAL_DATE, voyage.getArrivalDate());
            assertEquals(EXAMPLE_STATUS, voyage.getStatus());
            assertEquals(EXAMPLE_ROUTE, voyage.getRoute());
            assertEquals(EXAMPLE_SHUTTLE, voyage.getSpaceShuttle());
        }

        @Test
        void withoutId() {
            // When
            Exception exception = assertThrows(IllegalArgumentException.class,
                    () -> Voyage.reconstruct(null, EXAMPLE_DEPARTURE_DATE, EXAMPLE_ARRIVAL_DATE, EXAMPLE_STATUS, EXAMPLE_ROUTE, EXAMPLE_SHUTTLE));

            // Then
            assertEquals("The ID of the voyage is missing.", exception.getMessage());
        }

        @Test
        void withoutDepartureDate() {
            // When
            Exception exception = assertThrows(IllegalArgumentException.class, () -> Voyage.reconstruct(EXAMPLE_ID, null, EXAMPLE_ARRIVAL_DATE, EXAMPLE_STATUS, EXAMPLE_ROUTE, EXAMPLE_SHUTTLE));

            // Then
            assertEquals("The departure date of the voyage is missing.", exception.getMessage());
        }

        @Test
        void withoutArrivalDate() {
            // When
            Exception exception = assertThrows(IllegalArgumentException.class, () -> Voyage.reconstruct(EXAMPLE_ID, EXAMPLE_DEPARTURE_DATE, null, EXAMPLE_STATUS, EXAMPLE_ROUTE, EXAMPLE_SHUTTLE));

            // Then
            assertEquals("The arrival date of the voyage is missing.", exception.getMessage());
        }

        @Test
        void withoutStatus() {
            // When
            Exception exception = assertThrows(IllegalArgumentException.class,
                    () -> Voyage.reconstruct(EXAMPLE_ID, EXAMPLE_DEPARTURE_DATE, EXAMPLE_ARRIVAL_DATE, null, EXAMPLE_ROUTE, EXAMPLE_SHUTTLE));

            // Then
            assertEquals("The status of the voyage is missing.", exception.getMessage());
        }

        @Test
        void withoutRoute() {
            // When
            Exception exception = assertThrows(IllegalArgumentException.class,
                    () -> Voyage.reconstruct(EXAMPLE_ID, EXAMPLE_DEPARTURE_DATE, EXAMPLE_ARRIVAL_DATE, EXAMPLE_STATUS, null, EXAMPLE_SHUTTLE));

            // Then
            assertEquals("The route of the voyage is missing.", exception.getMessage());
        }

        @Test
        void withoutSpaceShuttle() {
            // When
            Exception exception = assertThrows(IllegalArgumentException.class, () -> Voyage.reconstruct(EXAMPLE_ID, EXAMPLE_DEPARTURE_DATE, EXAMPLE_ARRIVAL_DATE, EXAMPLE_STATUS, EXAMPLE_ROUTE, null));

            // Then
            assertEquals("The space shuttle of the voyage is missing.", exception.getMessage());
        }
    }

    @Test
    void withArrivalDateBeforeDepartureDate() {
        // When
        Exception exception = assertThrows(DomainException.class, () -> Voyage.reconstruct(EXAMPLE_ID, EXAMPLE_ARRIVAL_DATE, EXAMPLE_DEPARTURE_DATE, EXAMPLE_STATUS, EXAMPLE_ROUTE, EXAMPLE_SHUTTLE));

        // Then
        assertEquals("The arrival date of a voyage cannot be before its departure date.", exception.getMessage());
    }

    @Test
    void whenMatched() {
        // Given
        Voyage voyage = Voyage.create(EXAMPLE_DEPARTURE_DATE, EXAMPLE_ROUTE, EXAMPLE_SHUTTLE);
        Voyage duplicatedVoyage = Voyage.reconstruct(voyage.getId(), voyage.getDepartureDate(), voyage.getArrivalDate(), voyage.getStatus(), voyage.getRoute(), voyage.getSpaceShuttle());

        // Then
        assertEquals(voyage, duplicatedVoyage);
    }

    @Test
    void whenNotMatched() {
        // Given
        Voyage voyage = Voyage.create(EXAMPLE_DEPARTURE_DATE, EXAMPLE_ROUTE, EXAMPLE_SHUTTLE);
        Voyage voyageOneDayLater = Voyage.create(EXAMPLE_DEPARTURE_DATE.plusDays(1), EXAMPLE_ROUTE, EXAMPLE_SHUTTLE);

        // Then
        assertNotEquals(voyage, voyageOneDayLater);
    }
}
