package jp.co.company.space.api.features.user.endpoint;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.AddBean;
import io.helidon.microprofile.testing.junit5.AddBeans;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.authentication.exception.AuthenticationError;
import jp.co.company.space.api.features.booking.dto.BookingDto;
import jp.co.company.space.api.features.user.dto.UserDto;
import jp.co.company.space.api.shared.dto.DomainErrorDto;
import jp.co.company.space.utils.features.booking.PersistedBookingTestScenario;
import jp.co.company.space.utils.features.route.PersistedRouteTestScenario;
import jp.co.company.space.utils.features.spaceShuttle.PersistedSpaceShuttleTestScenario;
import jp.co.company.space.utils.features.spaceShuttleModel.PersistedSpaceShuttleModelTestScenario;
import jp.co.company.space.utils.features.user.PersistedUserTestScenario;
import jp.co.company.space.utils.features.voyage.PersistedVoyageTestScenario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link UserEndpoint} class.
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
class UserEndpointTest {

    @Inject
    WebTarget target;

    @Inject
    private PersistedBookingTestScenario persistedBookingTestScenario;

    @BeforeEach
    void beforeEach() {
        persistedBookingTestScenario.setup();
    }

    /**
     * Tests the properties of a {@link UserDto} instance.
     *
     * @param userDto The user to test.
     */
    private void testUserDto(UserDto userDto) {
        assertNotNull(userDto);

        assertNotNull(userDto.id);
        assertNotNull(userDto.lastName);
        assertNotNull(userDto.firstName);
        assertNotNull(userDto.bookings);
    }

    @Test
    void findByRequestContext() {
        // When
        Response response = target.path("users").request()
                .header(HttpHeaders.AUTHORIZATION, persistedBookingTestScenario.getAuthenticationHeader())
                .get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        UserDto foundUser = response.readEntity(UserDto.class);
        assertNotNull(foundUser);

        testUserDto(foundUser);

        BookingDto selectedBooking = foundUser.bookings.stream().filter(booking -> booking.id.equals(persistedBookingTestScenario.getPersistedBooking().getId())).findFirst().orElseThrow();
        assertEquals(persistedBookingTestScenario.getPersistedBooking().getId(), selectedBooking.id);
        assertEquals(persistedBookingTestScenario.getPersistedBooking().getCreationDate().getDayOfYear(), selectedBooking.creationDate.getDayOfYear());
        assertEquals(persistedBookingTestScenario.getPersistedBooking().getStatus().getKey(), selectedBooking.status);
        assertEquals(persistedBookingTestScenario.getPersistedUser().getId(), selectedBooking.userId);
        assertEquals(persistedBookingTestScenario.getPersistedVoyage().getId(), selectedBooking.voyageId);
    }

    @Test
    void findBookingsByUserId() {
        // When
        Response response = target.path(String.format("users/%s/bookings", persistedBookingTestScenario.getPersistedUser().getId())).request()
                .header(HttpHeaders.AUTHORIZATION, persistedBookingTestScenario.getAuthenticationHeader())
                .get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<BookingDto> foundBookings = response.readEntity(new GenericType<>(){});
        assertNotNull(foundBookings);
        assertFalse(foundBookings.isEmpty());
    }

    @Test
    void findBookingsByUserId_forDifferentUser() {
        // When
        Response response = target.path(String.format("users/%s/bookings", UUID.randomUUID())).request()
                .header(HttpHeaders.AUTHORIZATION, persistedBookingTestScenario.getAuthenticationHeader())
                .get();

        // Then
        assertNotNull(response);
        assertEquals(Status.FORBIDDEN_403.code(), response.getStatus());

        DomainErrorDto errorDto = response.readEntity(DomainErrorDto.class);
        assertNotNull(errorDto);

        assertEquals(AuthenticationError.FORBIDDEN.getKey(), errorDto.key);
        assertEquals(AuthenticationError.FORBIDDEN.getDescription(), errorDto.message);
        assertNull(errorDto.properties);
    }
}