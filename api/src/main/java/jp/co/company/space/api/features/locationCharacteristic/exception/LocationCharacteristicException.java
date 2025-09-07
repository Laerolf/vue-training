package jp.co.company.space.api.features.locationCharacteristic.exception;

import jp.co.company.space.api.shared.exception.DomainException;

/**
 * A {@link DomainException} instance for location characteristic exceptions.
 */
public class LocationCharacteristicException extends DomainException {
    public LocationCharacteristicException(LocationCharacteristicError error) {
        super(error);
    }

    public LocationCharacteristicException(LocationCharacteristicError error, Throwable throwable) {
        super(error, throwable);
    }
}
