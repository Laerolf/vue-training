package jp.co.company.space.api.features.authentication.exception;

import jp.co.company.space.api.shared.exception.DomainError;

/**
 * An enum with authentication error messages.
 */
public enum AuthenticationError implements DomainError {
    MISSING_TOKEN_PUBLIC_KEY("authentication.missingTokenPublicKey", "The public key for a new authentication token is missing."),
    MISSING_TOKEN_PRIVATE_KEY("authentication.missingTokenPrivateKey", "The private key for a new authentication token is missing."),

    MISSING_LOGIN_FORM("authentication.missingLoginForm", "A login form is missing."),
    MISSING_LOGIN_USER("authentication.missingLoginUser", "A user is missing during login."),

    TOKEN_CREATE("authentication.tokenCreate", "Unable to create a new authentication token."),
    TOKEN_CREATE_INVALID_LIFE_SPAN("authentication.tokenCreateInvalidLifeSpan", "The life span for a authentication token is invalid."),
    TOKEN_COOKIE_MISSING_TOKEN("authentication.tokenCookieMissingToken", "The token for the authentication cookie is missing."),
    TOKEN_COOKIE_MISSING_TOKEN_CLAIMS_SET("authentication.tokenCookieMissingTokenClaimsSet", "The token claims set for the authentication cookie is missing."),
    TOKEN_CREATE_INVALID_EXPIRATION_MINUTES("authentication.tokenCreateInvalidExpirationTime", "The provided authentication token expiration time in minutes is invalid."),
    TOKEN_COOKIE_CREATE("authentication.tokenCookieCreate", "Unable to create a new authentication token cookie."),

    TOKEN_SERVICE_START_UP("authentication.tokenServiceStartUp", "Failed to start up the authentication token service."),
    TOKEN_SERVICE_LOAD_INITIAL_DATA("authentication.tokenServiceLoadInitialData", "Failed to load the initial authentication token information."),

    LOGIN("authentication.login", "A login request has been rejected."),
    LOGIN_MISMATCH_CREDENTIALS("authentication.loginMisMatchCredentials", "The provided credentials don't match the existing credentials."),

    REGISTER("authentication.register", "A user registration request has been rejected."),

    FORBIDDEN("authentication.forbidden", "Access denied.");

    private final String key;
    private final String description;

    AuthenticationError(String key, String description) {
        this.key = key;
        this.description = description;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
