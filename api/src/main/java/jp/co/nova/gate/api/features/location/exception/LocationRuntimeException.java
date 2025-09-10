package jp.co.nova.gate.api.features.location.exception;

import jp.co.nova.gate.api.shared.exception.DomainRuntimeException;

/**
 * A {@link DomainRuntimeException} for location exceptions.
 */
public class LocationRuntimeException extends DomainRuntimeException {
    public LocationRuntimeException(LocationError error) {
        super(error);
    }

    public LocationRuntimeException(LocationError error, Throwable throwable) {
        super(error, throwable);
    }
}
