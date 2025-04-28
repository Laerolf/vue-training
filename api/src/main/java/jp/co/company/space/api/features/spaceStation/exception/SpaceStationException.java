package jp.co.company.space.api.features.spaceStation.exception;

import jp.co.company.space.api.shared.exception.DomainException;

/**
 * A {@link DomainException} instance for space station exceptions.
 */
public class SpaceStationException extends DomainException {
    public SpaceStationException(SpaceStationError error) {
        super(error);
    }

    public SpaceStationException(SpaceStationError error, Throwable throwable) {
        super(error, throwable);
    }
}
