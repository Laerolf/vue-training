package jp.co.company.space.api.features.booking.endpoint;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.AddBean;
import io.helidon.microprofile.testing.junit5.AddBeans;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.booking.domain.BookingStatus;
import jp.co.company.space.api.features.booking.dto.BookingDto;
import jp.co.company.space.api.features.booking.exception.BookingError;
import jp.co.company.space.api.features.booking.input.BookingCreationForm;
import jp.co.company.space.api.shared.dto.DomainErrorDto;
import jp.co.company.space.utils.features.booking.PersistedBookingTestScenario;
import jp.co.company.space.utils.features.passenger.PassengerCreationFormTestDataBuilder;
import jp.co.company.space.utils.features.route.PersistedRouteTestScenario;
import jp.co.company.space.utils.features.spaceShuttle.PersistedSpaceShuttleTestScenario;
import jp.co.company.space.utils.features.spaceShuttleModel.PersistedSpaceShuttleModelTestScenario;
import jp.co.company.space.utils.features.user.PersistedUserTestScenario;
import jp.co.company.space.utils.features.voyage.PersistedVoyageTestScenario;
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
@AddBeans({
        @AddBean(PersistedBookingTestScenario.class),
        @AddBean(PersistedVoyageTestScenario.class),
        @AddBean(PersistedUserTestScenario.class),
        @AddBean(PersistedSpaceShuttleTestScenario.class),
        @AddBean(PersistedSpaceShuttleModelTestScenario.class),
        @AddBean(PersistedRouteTestScenario.class)
})
class BookingEndpointTest {

    @Inject
    PersistedBookingTestScenario persistedBookingTestScenario;

    @Inject
    private WebTarget target;

    @BeforeEach
    void beforeEach() {
        persistedBookingTestScenario.setup();
    }

    @Test
    void createBooking() {
        // Given
        BookingCreationForm creationForm = new BookingCreationForm(
                persistedBookingTestScenario.getPersistedVoyage().getId(),
                List.of(new PassengerCreationFormTestDataBuilder().create())
        );

        // When
        Response response = target.path("bookings").request()
                .header(HttpHeaders.AUTHORIZATION, persistedBookingTestScenario.getAuthenticationHeader())
                .post(Entity.json(creationForm));

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        BookingDto newBooking = response.readEntity(BookingDto.class);

        assertNotNull(newBooking.id);
        assertEquals(ZonedDateTime.now().getDayOfYear(), newBooking.creationDate.getDayOfYear());
        assertEquals(BookingStatus.CREATED.getKey(), newBooking.status);
        assertEquals(persistedBookingTestScenario.getPersistedUser().getId(), newBooking.userId);
        assertEquals(persistedBookingTestScenario.getPersistedVoyage().getId(), newBooking.voyageId);
        assertFalse(newBooking.passengerIds.isEmpty());

        BookingDto newSavedBooking = target.path(String.format("bookings/%s", newBooking.id)).request()
                .header(HttpHeaders.AUTHORIZATION, persistedBookingTestScenario.getAuthenticationHeader())
                .get()
                .readEntity(BookingDto.class);

        assertNotNull(newSavedBooking);
    }

    @Test
    void createBooking_withNoPassengers() {
        BookingCreationForm creationForm = new BookingCreationForm(
                persistedBookingTestScenario.getPersistedUser().getId(),
                List.of()
        );

        // When
        Response response = target.path("bookings").request()
                .header(HttpHeaders.AUTHORIZATION, persistedBookingTestScenario.getAuthenticationHeader())
                .post(Entity.json(creationForm));

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
        Response response = target.path(String.format("bookings/%s", persistedBookingTestScenario.getPersistedBooking().getId())).request()
                .header(HttpHeaders.AUTHORIZATION, persistedBookingTestScenario.getAuthenticationHeader())
                .get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        BookingDto foundBooking = response.readEntity(BookingDto.class);

        assertEquals(persistedBookingTestScenario.getPersistedBooking().getId(), foundBooking.id);
        assertEquals(persistedBookingTestScenario.getPersistedBooking().getCreationDate().getDayOfYear(), foundBooking.creationDate.getDayOfYear());
        assertEquals(persistedBookingTestScenario.getPersistedBooking().getStatus().getKey(), foundBooking.status);
        assertEquals(persistedBookingTestScenario.getPersistedUser().getId(), foundBooking.userId);
        assertEquals(persistedBookingTestScenario.getPersistedBooking().getVoyage().getId(), foundBooking.voyageId);
        assertEquals(new ArrayList<>(), foundBooking.passengerIds);
    }
}