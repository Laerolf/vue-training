package jp.co.company.space.api.features.user.endpoint;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.booking.dto.BookingDto;
import jp.co.company.space.api.features.user.domain.PasswordHashFactory;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.user.dto.NewUserDto;
import jp.co.company.space.api.features.user.dto.UserDto;
import jp.co.company.space.api.features.user.input.UserCreationForm;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.utils.features.user.UserTestDataBuilder;
import jp.co.company.space.utils.features.voyage.user.VoyageTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the {@link UserEndpoint} class.
 */
@Transactional(Transactional.TxType.REQUIRED)
@HelidonTest
class UserEndpointTest {

    private final User USER = new UserTestDataBuilder().create();
    private final Voyage VOYAGE  = new VoyageTestDataBuilder().create();
    private final Booking BOOKING = Booking.create(USER, VOYAGE);

    @Inject
    WebTarget target;

    @PersistenceContext(unitName = "domain")
    EntityManager entityManager;

    @BeforeEach
    void beforeEach() {
        loadTestUsers();

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
     * Loads all test {@link User} instances into the database.
     */
    private void loadTestUsers() {
        try (JsonReader reader = Json.createReader(UserEndpointTest.class.getResourceAsStream("/static/users.json"))) {
            reader.readArray().stream()
                    .map(userJsonValue -> {
                        JsonObject userJson = userJsonValue.asJsonObject();

                        String id = userJson.getString("id");
                        String lastName = userJson.getString("lastName");
                        String firstName = userJson.getString("firstName");
                        String emailAddress = userJson.getString("emailAddress");
                        String password = userJson.getString("password");

                        return User.reconstruct(id, lastName, firstName, emailAddress, new PasswordHashFactory(password).hash());
                    })
                    .filter(user -> Optional.ofNullable(entityManager.find(User.class, user.getId())).isEmpty())
                    .forEach(user -> entityManager.persist(user));

        } catch (JsonException | NullPointerException exception) {
            throw new RuntimeException("Failed to load the test users into the database", exception);
        }
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

    /**
     * Tests the properties of a {@link NewUserDto} instance.
     *
     * @param userDto The user to test.
     */
    private void testUserDto(NewUserDto userDto) {
        assertNotNull(userDto);

        assertNotNull(userDto.id);
        assertNotNull(userDto.lastName);
        assertNotNull(userDto.firstName);
    }

    @Test
    void findUserById() {
        // Given
        String selectedUserId = "test";

        // When
        Response response = target.path(String.format("users/%s", selectedUserId)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        UserDto foundUser = response.readEntity(UserDto.class);
        assertNotNull(foundUser);

        testUserDto(foundUser);
    }

    @Test
    void findUserById_withBookings() {
        // When
        Response response = target.path(String.format("users/%s", USER.getId())).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        UserDto foundUser = response.readEntity(UserDto.class);
        assertNotNull(foundUser);

        testUserDto(foundUser);

        BookingDto selectedBooking = foundUser.bookings.getFirst();
        assertEquals(BOOKING.getId(), selectedBooking.id);
        assertEquals(BOOKING.getCreationDate().getDayOfYear(), selectedBooking.creationDate.getDayOfYear());
        assertEquals(BOOKING.getStatus().getKey(), selectedBooking.status);
        assertEquals(USER.getId(), selectedBooking.userId);
        assertEquals(VOYAGE.getId(), selectedBooking.voyageId);
    }

    @Test
    void createUser() {
        // Given
        String lastName = "Laerolf";
        String firstName = "Salocin";
        String emailAddress = "laerolf.salocin@test.test";

        UserCreationForm form = UserCreationForm.create(lastName, firstName, emailAddress, UUID.randomUUID().toString());

        // When
        Response response = target.path("users").request().post(Entity.json(form));

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        NewUserDto newUser = response.readEntity(NewUserDto.class);
        assertNotNull(newUser);

        assertEquals(lastName, newUser.lastName);
        assertEquals(firstName, newUser.firstName);
        assertEquals(emailAddress, newUser.emailAddress);

        testUserDto(newUser);
    }
}