package jp.co.company.space.api.features.authentication.exception;

import jp.co.company.space.api.shared.exception.DomainException;

/**
 * A {@link DomainException} for authentication exceptions.
 */
public class AuthenticationException extends DomainException {
    public AuthenticationException(AuthenticationError error) {
        super(error);
    }

    public AuthenticationException(AuthenticationError error, Throwable throwable) {
        super(error, throwable);
    }
}
