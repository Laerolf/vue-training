package jp.co.company.space.api.features.booking.endpoint;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.booking.domain.BookingStatus;
import jp.co.company.space.api.features.booking.dto.BookingDto;
import jp.co.company.space.api.features.booking.exception.BookingError;
import jp.co.company.space.api.features.booking.input.BookingCreationForm;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.shared.dto.DomainErrorDto;
import jp.co.company.space.utils.features.passenger.PassengerCreationFormTestDataBuilder;
import jp.co.company.space.utils.features.user.UserTestDataBuilder;
import jp.co.company.space.utils.features.voyage.user.VoyageTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link BookingEndpoint} class.
 */
@Transactional(Transactional.TxType.REQUIRED)
@HelidonTest
class BookingEndpointTest {

    private final User USER = new UserTestDataBuilder().create();
    private final Voyage VOYAGE  = new VoyageTestDataBuilder().create();
    private final Booking BOOKING = Booking.create(USER, VOYAGE);

    @Inject
    private WebTarget target;

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    @BeforeEach
    void beforeEach() {
        if (entityManager.find(User.class, USER.getId()) == null) {
            entityManager.merge(USER);
        }

        if (entityManager.find(SpaceShuttleModel.class, VOYAGE.getSpaceShuttle().getModel().getId()) == null) {
            entityManager.merge(VOYAGE.getSpaceShuttle().getModel());
        }

        if (entityManager.find(Booking.class, BOOKING.getId()) == null) {
            entityManager.merge(VOYAGE.getSpaceShuttle());
            entityManager.merge(VOYAGE.getSpaceShuttle().getModel());
            entityManager.merge(VOYAGE.getRoute().getOrigin().getLocation());
            entityManager.merge(VOYAGE.getRoute().getOrigin());
            entityManager.merge(VOYAGE.getRoute().getDestination().getLocation());
            entityManager.merge(VOYAGE.getRoute().getDestination());
            entityManager.merge(VOYAGE.getRoute().getShuttleModel());
            entityManager.merge(VOYAGE.getRoute());
            entityManager.merge(VOYAGE);

            entityManager.merge(BOOKING);
        }
    }

    @Test
    void createBooking() {
        // Given
        BookingCreationForm creationForm = new BookingCreationForm(USER.getId(), VOYAGE.getId(), List.of(new PassengerCreationFormTestDataBuilder().create()));

        // When
        Response response = target.path("bookings").request().post(Entity.json(creationForm));

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        BookingDto newBooking = response.readEntity(BookingDto.class);

        assertNotNull(newBooking.id);
        assertEquals(ZonedDateTime.now().getDayOfYear(), newBooking.creationDate.getDayOfYear());
        assertEquals(BookingStatus.CREATED.getKey(), newBooking.status);
        assertEquals(USER.getId(), newBooking.userId);
        assertEquals(VOYAGE.getId(), newBooking.voyageId);
        assertFalse(newBooking.passengerIds.isEmpty());

        BookingDto newSavedBooking = target.path(String.format("bookings/%s", newBooking.id)).request().get().readEntity(BookingDto.class);
        assertNotNull(newSavedBooking);
    }

    @Test
    void createBooking_withNoPassengers() {
        BookingCreationForm creationForm = new BookingCreationForm(USER.getId(), VOYAGE.getId(), List.of());

        // When
        Response response = target.path("bookings").request().post(Entity.json(creationForm));

        // Then
        assertNotNull(response);
        assertEquals(Status.INTERNAL_SERVER_ERROR_500.code(), response.getStatus());

        DomainErrorDto error = response.readEntity(DomainErrorDto.class);
        assertEquals(BookingError.INVALID_PASSENGER_COUNT.getKey(), error.key);
        assertEquals(BookingError.INVALID_PASSENGER_COUNT.getDescription(), error.message);
    }

    @Test
    void findBookingById() {
        // When
        Response response = target.path(String.format("bookings/%s", BOOKING.getId())).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        BookingDto foundBooking = response.readEntity(BookingDto.class);

        assertEquals(BOOKING.getId(), foundBooking.id);
        assertEquals(BOOKING.getCreationDate().getDayOfYear(), foundBooking.creationDate.getDayOfYear());
        assertEquals(BOOKING.getStatus().getKey(), foundBooking.status);
        assertEquals(BOOKING.getUser().getId(), foundBooking.userId);
        assertEquals(BOOKING.getVoyage().getId(), foundBooking.voyageId);
        assertEquals(new ArrayList<>(), foundBooking.passengerIds);
    }
}