package jp.co.company.space.api.features.authentication.exception;

import jp.co.company.space.api.shared.exception.DomainRuntimeException;

/**
 * A {@link DomainRuntimeException} for authentication exceptions.
 */
public class AuthenticationRuntimeException extends DomainRuntimeException {
    public AuthenticationRuntimeException(AuthenticationError error) {
        super(error);
    }

    public AuthenticationRuntimeException(AuthenticationError error, Throwable throwable) {
        super(error, throwable);
    }
}
