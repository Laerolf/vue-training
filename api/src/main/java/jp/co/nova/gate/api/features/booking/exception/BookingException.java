package jp.co.nova.gate.api.features.booking.exception;

import jp.co.nova.gate.api.shared.exception.DomainException;

/**
 * A {@link DomainException} for booking exceptions.
 */
public class BookingException extends DomainException {
    public BookingException(BookingError error) {
        super(error);
    }

    public BookingException(BookingError error, Throwable throwable) {
        super(error, throwable);
    }
}
