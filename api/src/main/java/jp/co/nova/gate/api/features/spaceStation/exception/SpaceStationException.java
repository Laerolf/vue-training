package jp.co.nova.gate.api.features.spaceStation.exception;

import jp.co.nova.gate.api.shared.exception.DomainException;

/**
 * A {@link DomainException} for space station exceptions.
 */
public class SpaceStationException extends DomainException {
    public SpaceStationException(SpaceStationError error) {
        super(error);
    }

    public SpaceStationException(SpaceStationError error, Throwable throwable) {
        super(error, throwable);
    }
}
