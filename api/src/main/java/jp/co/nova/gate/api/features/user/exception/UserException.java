package jp.co.nova.gate.api.features.user.exception;

import jp.co.nova.gate.api.shared.exception.DomainException;

/**
 * A {@link DomainException} for user exceptions.
 */
public class UserException extends DomainException {
    public UserException(UserError error) {
        super(error);
    }

    public UserException(UserError error, Throwable throwable) {
        super(error, throwable);
    }
}
