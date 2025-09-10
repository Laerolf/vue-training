package jp.co.nova.gate.api.features.authentication.exception;

import jp.co.nova.gate.api.shared.exception.DomainRuntimeException;

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
