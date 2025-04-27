package jp.co.company.space.api.features.booking.exception;

import jp.co.company.space.api.shared.exception.DomainException;

/**
 * A {@link DomainException} instance for booking exceptions.
 */
public class BookingException extends DomainException {
    public BookingException(BookingError error) {
        super(error);
    }

    public BookingException(BookingError error, Throwable throwable) {
        super(error, throwable);
    }
}
