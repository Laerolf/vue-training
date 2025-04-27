package jp.co.company.space.api.features.location.exception;

import jp.co.company.space.api.shared.exception.DomainException;

/**
 * A {@link DomainException} instance for location exceptions.
 */
public class LocationException extends DomainException {
    public LocationException(LocationError error) {
        super(error);
    }

    public LocationException(LocationError error, Throwable throwable) {
        super(error, throwable);
    }
}
