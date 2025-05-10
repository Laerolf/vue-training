package jp.co.company.space.api.features.authentication.endpoint;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.AddBean;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.authentication.exception.AuthenticationError;
import jp.co.company.space.api.features.authentication.input.LoginRequestForm;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.user.dto.NewUserDto;
import jp.co.company.space.api.features.user.exception.UserError;
import jp.co.company.space.api.features.user.input.UserCreationForm;
import jp.co.company.space.api.shared.dto.DomainErrorDto;
import jp.co.company.space.utils.features.user.PersistedUserTestScenario;
import jp.co.company.space.utils.features.user.UserTestDataBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the {@link AuthenticationEndpoint} class.
 */
@HelidonTest
@AddBean(PersistedUserTestScenario.class)
class AuthenticationEndpointTest {

    @Inject
    private PersistedUserTestScenario persistedUserTestScenario;

    @Inject
    private WebTarget target;

    @Inject
    @ConfigProperty(name = "mp.jwt.token.cookie")
    private String authenticationCookieName;

    @BeforeEach
    public void beforeEach() {
        persistedUserTestScenario.setup();
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
    void register() {
        // Given
        String lastName = "Laerolf";
        String firstName = "Salocin";
        String emailAddress = "laerolf.salocin@test.test";

        UserCreationForm form = UserCreationForm.create(lastName, firstName, emailAddress, UUID.randomUUID().toString());

        // When
        Response response = target.path("auth/register").request().post(Entity.json(form));

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

    @Test
    void register_doubleRegistration() {
        // Given
        User user = new UserTestDataBuilder().withEmailAddress("laerolf.salocin@test.be").create();
        UserCreationForm userCreationForm = UserCreationForm.create(user.getLastName(), user.getFirstName(), user.getEmailAddress(), UUID.randomUUID().toString());

        // When
        Response firstRegistrationResponse = target.path("auth/register").request().post(Entity.json(userCreationForm));
        Response secondRegistrationResponse = target.path("auth/register").request().post(Entity.json(userCreationForm));

        // Then
        assertNotNull(firstRegistrationResponse);
        assertEquals(Status.OK_200.code(), firstRegistrationResponse.getStatus());

        assertNotNull(secondRegistrationResponse);
        assertEquals(Status.INTERNAL_SERVER_ERROR_500.code(), secondRegistrationResponse.getStatus());

        DomainErrorDto errorDto = secondRegistrationResponse.readEntity(DomainErrorDto.class);
        assertNotNull(errorDto);

        assertEquals(AuthenticationError.REGISTER.getKey(), errorDto.key);
        assertEquals(AuthenticationError.REGISTER.getDescription(), errorDto.message);

        Map<String, Object> properties = errorDto.properties;
        assertNotNull(properties);

        HashMap<String, Object> causeDto = (HashMap<String, Object>) properties.get("cause");

        assertEquals(UserError.EMAIL_ALREADY_IN_USE.getKey(), causeDto.get("key"));
        assertEquals(UserError.EMAIL_ALREADY_IN_USE.getDescription(), causeDto.get("message"));
    }

    @Test
    void login_happyFlow() {
        // Given
        LoginRequestForm loginForm = new LoginRequestForm(
                persistedUserTestScenario.getUnpersistedUser().getEmailAddress(),
                persistedUserTestScenario.getUnpersistedUser().getPassword()
        );

        // Then
        Response response = target.path("auth/login").request().post(Entity.json(loginForm));

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        NewCookie authenticationCookie = response.getCookies().get(authenticationCookieName);
        assertNotNull(authenticationCookie);
    }

    @Test
    void login_withWrongCredentials() {
        // Given
        LoginRequestForm loginForm = new LoginRequestForm(
                persistedUserTestScenario.getUnpersistedUser().getEmailAddress(),
                "helloWorld!"
        );

        // Then
        Response response = target.path("auth/login").request().post(Entity.json(loginForm));

        // Then
        assertNotNull(response);
        assertEquals(Status.UNAUTHORIZED_401.code(), response.getStatus());

        DomainErrorDto errorDto = response.readEntity(DomainErrorDto.class);
        assertNotNull(errorDto);

        assertEquals(AuthenticationError.LOGIN.getKey(), errorDto.key);
        assertEquals(AuthenticationError.LOGIN.getDescription(), errorDto.message);

        Map<String, Object> properties = errorDto.properties;
        assertNotNull(properties);

        HashMap<String, Object> causeDto = (HashMap<String, Object>) properties.get("cause");

        assertEquals(AuthenticationError.LOGIN_MISMATCH_CREDENTIALS.getKey(), causeDto.get("key"));
        assertEquals(AuthenticationError.LOGIN_MISMATCH_CREDENTIALS.getDescription(), causeDto.get("message"));
    }
}