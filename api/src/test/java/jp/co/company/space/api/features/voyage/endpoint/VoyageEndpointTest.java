package jp.co.company.space.api.features.voyage.endpoint;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.booking.dto.BookingBasicDto;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.features.voyage.dto.VoyageBasicDto;
import jp.co.company.space.api.features.voyage.dto.VoyageDto;
import jp.co.company.space.utils.features.user.UserTestDataBuilder;
import jp.co.company.space.utils.features.voyage.user.VoyageTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link VoyageEndpoint} class.
 */
@Transactional(Transactional.TxType.REQUIRED)
@HelidonTest
public class VoyageEndpointTest {

    private final static String JAPAN_SPACE_STATION_ID = "00000000-0000-1000-8000-000000000003";
    private final static String MARS_SPACE_STATION_ID = "00000000-0000-1000-8000-000000000012";

    private final User USER = new UserTestDataBuilder().create();
    private final Voyage VOYAGE  = new VoyageTestDataBuilder().create();
    private final Booking BOOKING = Booking.create(USER, VOYAGE);

    @Inject
    private WebTarget target;

    @PersistenceContext(unitName = "domain")
    EntityManager entityManager;

    @BeforeEach
    void beforeEach() {
        if (entityManager.find(User.class, USER.getId()) == null) {
            entityManager.persist(USER);
        }

        if (entityManager.find(Booking.class, BOOKING.getId()) == null) {
            entityManager.persist(VOYAGE.getSpaceShuttle().getModel());
            entityManager.persist(VOYAGE.getSpaceShuttle());
            entityManager.persist(VOYAGE.getRoute().getOrigin().getLocation());
            entityManager.persist(VOYAGE.getRoute().getOrigin());
            entityManager.persist(VOYAGE.getRoute().getDestination().getLocation());
            entityManager.persist(VOYAGE.getRoute().getDestination());
            entityManager.persist(VOYAGE.getRoute());
            entityManager.persist(VOYAGE);

            entityManager.persist(BOOKING);
        }
    }

    /**
     * Tests the properties of a {@link VoyageBasicDto} instance.
     * @param voyageDto The voyage to test.
     */
    private void testVoyageBasicDto(VoyageBasicDto voyageDto) {
        assertNotNull(voyageDto);

        assertNotNull(voyageDto.id);
        assertNotNull(voyageDto.departureDate);
        assertNotNull(voyageDto.arrivalDate);
        assertNotNull(voyageDto.routeId);
        assertNotNull(voyageDto.originId);
        assertNotNull(voyageDto.destinationId);
        assertTrue(voyageDto.duration > 0);
        assertNotNull(voyageDto.spaceShuttleId);
        assertNotNull(voyageDto.status);
    }

    @Test
    void getAllVoyages() {
        // When
        Response response = target.path("/voyages").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<VoyageBasicDto> voyages = response.readEntity(new GenericType<>() {});
        assertNotNull(voyages);

        testVoyageBasicDto(voyages.getFirst());
    }

    @Test
    void getVoyageById() {
        // Given
        String voyageId = "5f485136-20a8-41f3-9073-4156d32c9c36";

        // When
        Response response = target.path(String.format("/voyages/%s", voyageId)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        VoyageDto voyage = response.readEntity(VoyageDto.class);
        assertNotNull(voyage);

        assertEquals(voyageId, voyage.id);
        assertNotNull(voyage.departureDate);
        assertNotNull(voyage.arrivalDate);
        assertTrue(voyage.duration > 0);
        assertNotNull(voyage.spaceShuttle);
        assertNotNull(voyage.route);
        assertNotNull(voyage.status);
    }

    @Test
    void getVoyagesFrom() {
        // When
       Response response = target.path(String.format("/voyages/from/%s", JAPAN_SPACE_STATION_ID)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<VoyageBasicDto> voyages = response.readEntity(new GenericType<>() {});
        assertNotNull(voyages);

        voyages.forEach(voyage -> assertEquals(JAPAN_SPACE_STATION_ID, voyage.originId));

        testVoyageBasicDto(voyages.getFirst());
    }

    @Test
    void getVoyagesTo() {
        // When
        Response response = target.path(String.format("/voyages/to/%s", MARS_SPACE_STATION_ID)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<VoyageBasicDto> voyages = response.readEntity(new GenericType<>() {});
        assertNotNull(voyages);

        voyages.forEach(voyage -> assertEquals(MARS_SPACE_STATION_ID, voyage.destinationId));

        testVoyageBasicDto(voyages.getFirst());
    }

    @Test
    void getVoyagesFromTo() {
        // When
        Response response = target.path(String.format("/voyages/from/%s/to/%s", JAPAN_SPACE_STATION_ID, MARS_SPACE_STATION_ID)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<VoyageBasicDto> voyages = response.readEntity(new GenericType<>() {});
        assertNotNull(voyages);

        voyages.forEach(voyage -> {
            assertEquals(JAPAN_SPACE_STATION_ID, voyage.originId);
            assertEquals(MARS_SPACE_STATION_ID, voyage.destinationId);
        });

        testVoyageBasicDto(voyages.getFirst());
    }

    @Test
    void getAllBookingsByVoyageId() {
        // When
        Response response = target.path(String.format("users/%s/bookings", USER.getId())).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<BookingBasicDto> foundBookings = response.readEntity(new GenericType<>() {
        });
        assertNotNull(foundBookings);
        assertFalse(foundBookings.isEmpty());

        assertNotNull(foundBookings.getFirst());
        assertEquals(BOOKING.getId(), foundBookings.getFirst().id);
        assertEquals(BOOKING.getCreationDate().getDayOfYear(), foundBookings.getFirst().creationDate.getDayOfYear());
        assertEquals(BOOKING.getStatus().getKey(), foundBookings.getFirst().status);
        assertEquals(USER.getId(), foundBookings.getFirst().userId);
        assertEquals(VOYAGE.getId(), foundBookings.getFirst().voyageId);
    }
}
