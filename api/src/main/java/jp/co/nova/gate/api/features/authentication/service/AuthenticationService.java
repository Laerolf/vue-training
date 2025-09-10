package jp.co.nova.gate.api.features.authentication.service;

import com.nimbusds.jwt.SignedJWT;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jp.co.nova.gate.api.features.authentication.domain.PasswordValidationFactory;
import jp.co.nova.gate.api.features.authentication.exception.AuthenticationError;
import jp.co.nova.gate.api.features.authentication.exception.AuthenticationException;
import jp.co.nova.gate.api.features.authentication.input.LoginRequestForm;
import jp.co.nova.gate.api.features.user.domain.User;
import jp.co.nova.gate.api.features.user.exception.UserException;
import jp.co.nova.gate.api.features.user.input.UserCreationForm;
import jp.co.nova.gate.api.features.user.service.UserService;
import jp.co.nova.gate.api.shared.util.LogBuilder;

import java.util.logging.Logger;

/**
 * A service class handling the authentication topic.
 */
@ApplicationScoped
public class AuthenticationService {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationService.class.getName());

    @Inject
    private UserService userService;

    @Inject
    private AuthenticationTokenService authenticationTokenService;

    protected AuthenticationService() {
    }

    /**
     * Registers a new user by creating it.
     *
     * @param form The details of the user registration.
     * @return A {@link User}.
     */
    public User registerUser(UserCreationForm form) throws AuthenticationException {
        try {
            return userService.create(form);
        } catch (UserException exception) {
            LOGGER.warning(new LogBuilder(AuthenticationError.REGISTER).withException(exception).build());
            throw new AuthenticationException(AuthenticationError.REGISTER, exception);
        }
    }

    /**
     * Handles a login attempt and returns an authentication token when the login attempt is successful.
     *
     * @param loginForm A form with the details for a login attempt.
     * @return An authentication token when the login attempt is successful.
     */
    public SignedJWT login(LoginRequestForm loginForm) throws AuthenticationException {
        try {
            if (loginForm == null) {
                throw new AuthenticationException(AuthenticationError.MISSING_LOGIN_FORM);
            }

            User selectedUser = userService.findByEmailAddress(loginForm.emailAddress).orElseThrow(() -> new AuthenticationException(AuthenticationError.MISSING_LOGIN_USER));

            if (!new PasswordValidationFactory(loginForm.password, selectedUser.getPassword()).validate()) {
                throw new AuthenticationException(AuthenticationError.LOGIN_MISMATCH_CREDENTIALS);
            }

            return authenticationTokenService.generateToken(selectedUser.getId());
        } catch (IllegalArgumentException exception) {
            LOGGER.warning(new LogBuilder(AuthenticationError.LOGIN).withException(exception).build());
            throw new AuthenticationException(AuthenticationError.LOGIN, exception);
        }
    }
}
