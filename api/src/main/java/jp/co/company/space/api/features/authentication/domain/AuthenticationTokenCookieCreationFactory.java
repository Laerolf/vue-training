package jp.co.company.space.api.features.authentication.domain;

import com.nimbusds.jwt.SignedJWT;
import jakarta.ws.rs.core.NewCookie;
import jp.co.company.space.api.features.authentication.exception.AuthenticationError;
import jp.co.company.space.api.features.authentication.exception.AuthenticationException;

import java.text.ParseException;

/**
 * A factory creating a new {@link NewCookie} for authentication purposes.
 */
public class AuthenticationTokenCookieCreationFactory {

    private final String propertyName;
    private final SignedJWT authenticationToken;

    public AuthenticationTokenCookieCreationFactory(String propertyName, SignedJWT authenticationToken) {
        this.propertyName = propertyName;
        this.authenticationToken = authenticationToken;
    }

    /**
     * Creates a new {@link NewCookie} for authentication purposes based on the provided values.
     *
     * @return A {@link NewCookie} for authentication purposes.
     */
    public NewCookie generate() throws AuthenticationException {
        try {
            if (authenticationToken == null) {
                throw new AuthenticationException(AuthenticationError.TOKEN_COOKIE_MISSING_TOKEN);
            } else if (authenticationToken.getJWTClaimsSet() == null) {
                throw new AuthenticationException(AuthenticationError.TOKEN_COOKIE_MISSING_TOKEN_CLAIMS_SET);
            }

            return new NewCookie.Builder(propertyName)
                    .value(authenticationToken.serialize())
                    .expiry(authenticationToken.getJWTClaimsSet().getExpirationTime())
                    .httpOnly(true)
                    .build();
        } catch (ParseException | IllegalArgumentException exception) {
            throw new AuthenticationException(AuthenticationError.TOKEN_COOKIE_CREATE, exception);
        }
    }
}
