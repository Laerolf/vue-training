package jp.co.company.space.api.features.passenger.exception;

import jp.co.company.space.api.shared.exception.DomainException;

/**
 * A {@link DomainException} for passenger exceptions.
 */
public class PassengerException extends DomainException {
    public PassengerException(PassengerError error) {
        super(error);
    }

    public PassengerException(PassengerError error, Throwable throwable) {
        super(error, throwable);
    }
}
