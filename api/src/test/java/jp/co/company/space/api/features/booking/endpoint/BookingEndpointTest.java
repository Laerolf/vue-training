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
import jp.co.company.space.api.features.booking.dto.BookingBasicDto;
import jp.co.company.space.api.features.booking.dto.BookingDto;
import jp.co.company.space.api.features.booking.input.BookingCreationForm;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.utils.features.user.UserTestDataBuilder;
import jp.co.company.space.utils.features.voyage.user.VoyageTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    void createBooking() {
        BookingCreationForm creationForm = new BookingCreationForm(USER.getId(), VOYAGE.getId());

        // When
        Response response = target.path("bookings").request().post(Entity.json(creationForm));

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        BookingBasicDto newBooking = response.readEntity(BookingBasicDto.class);

        assertNotNull(newBooking.id);
        assertEquals(ZonedDateTime.now().getDayOfYear(), newBooking.creationDate.getDayOfYear());
        assertEquals(BookingStatus.DRAFT.getKey(), newBooking.status);
        assertEquals(USER.getId(), newBooking.userId);
        assertEquals(VOYAGE.getId(), newBooking.voyageId);

        BookingDto newSavedBooking = target.path(String.format("bookings/%s", newBooking.id)).request().get().readEntity(BookingDto.class);
        assertNotNull(newSavedBooking);
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
        assertEquals(BOOKING.getUser().getId(), foundBooking.user.id);
        assertEquals(BOOKING.getVoyage().getId(), foundBooking.voyage.id);
    }
}