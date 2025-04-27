package jp.co.company.space.api.shared.exception;

import jp.co.company.space.api.features.booking.exception.BookingError;

/**
 * An abstract class representing a domain specific {@link IllegalArgumentException} instance.
 */
// TODO: make this abstract
public class DomainException extends IllegalArgumentException {

    private final DomainError error;

    public DomainException(DomainError error) {
        super(error.getDescription());
        this.error = error;
    }

    // TODO: Remove
    public DomainException(String error) {
        super(error);
        this.error = BookingError.FIND_BY_ID;
    }

    // TODO: Remove
    public DomainException(String error, Throwable throwable) {
        super(error, throwable);
        this.error = BookingError.FIND_BY_ID;
    }

    public DomainException(DomainError error, Throwable throwable) {
        super(error.getDescription(), throwable);
        this.error = error;
    }

    public String getKey() {
        return error.getKey();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }
}
