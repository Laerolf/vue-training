package jp.co.nova.gate.api.features.location.exception;

import jp.co.nova.gate.api.shared.exception.DomainException;

/**
 * A {@link DomainException} for location exceptions.
 */
public class LocationException extends DomainException {
    public LocationException(LocationError error) {
        super(error);
    }

    public LocationException(LocationError error, Throwable throwable) {
        super(error, throwable);
    }
}
