package jp.co.nova.gate.api.features.locationCharacteristic.exception;

import jp.co.nova.gate.api.shared.exception.DomainException;

/**
 * A {@link DomainException} for location characteristic exceptions.
 */
public class LocationCharacteristicException extends DomainException {
    public LocationCharacteristicException(LocationCharacteristicError error) {
        super(error);
    }

    public LocationCharacteristicException(LocationCharacteristicError error, Throwable throwable) {
        super(error, throwable);
    }
}
