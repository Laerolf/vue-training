package jp.co.nova.gate.api.features.spaceStation.exception;

import jp.co.nova.gate.api.shared.exception.DomainRuntimeException;

/**
 * A {@link DomainRuntimeException} for space station exceptions.
 */
public class SpaceStationRuntimeException extends DomainRuntimeException {
    public SpaceStationRuntimeException(SpaceStationError error) {
        super(error);
    }

    public SpaceStationRuntimeException(SpaceStationError error, Throwable throwable) {
        super(error, throwable);
    }
}
