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
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link UserEndpoint} class.
 */
@Transactional(Transactional.TxType.REQUIRED)
@HelidonTest
class UserEndpointTest {

    @Inject
    private WebTarget target;

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    @BeforeEach
    void beforeEach() {
        loadTestUsers();
    }

    /**
     * Loads all test {@link User} instances into the database.
     */
    private void loadTestUsers() {
        try (JsonReader reader = Json.createReader(UserEndpointTest.class.getResourceAsStream("/static/users.json"))) {
            reader.readArray().stream()
                    .map(userJsonValue -> {
                        JsonObject shuttleJson = userJsonValue.asJsonObject();

                        String id = shuttleJson.getString("id");
                        String lastName = shuttleJson.getString("lastName");
                        String firstName = shuttleJson.getString("firstName");
                        String emailAddress = shuttleJson.getString("emailAddress");
                        String password = shuttleJson.getString("password");

                        return User.reconstruct(id, lastName, firstName, emailAddress, password);
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
    }

    @Test
    void getAllUsers() {
        // When
        Response response = target.path("users").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<UserDto> foundUsers = response.readEntity(new GenericType<>() {
        });
        assertNotNull(foundUsers);
        assertFalse(foundUsers.isEmpty());

        testUserDto(foundUsers.getFirst());
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
}