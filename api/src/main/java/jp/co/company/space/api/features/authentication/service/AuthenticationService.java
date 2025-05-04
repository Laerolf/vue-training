package jp.co.company.space.api.features.authentication.service;

import com.nimbusds.jwt.SignedJWT;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jp.co.company.space.api.features.authentication.exception.AuthenticationError;
import jp.co.company.space.api.features.authentication.exception.AuthenticationException;
import jp.co.company.space.api.features.authentication.input.LoginRequestForm;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.user.service.UserService;
import jp.co.company.space.api.shared.util.LogBuilder;

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
            return authenticationTokenService.generateToken(selectedUser.getId());
        } catch (AuthenticationException exception) {
            LOGGER.warning(new LogBuilder(AuthenticationError.LOGIN).withException(exception).build());
            throw new AuthenticationException(AuthenticationError.LOGIN, exception);
        }
    }
}
