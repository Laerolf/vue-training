package jp.co.nova.gate.api.features.spaceShuttleModel.exception;

import jp.co.nova.gate.api.shared.exception.DomainException;

/**
 * A {@link DomainException} for space shuttle model exceptions.
 */
public class SpaceShuttleModelException extends DomainException {
    public SpaceShuttleModelException(SpaceShuttleModelError error) {
        super(error);
    }

    public SpaceShuttleModelException(SpaceShuttleModelError error, Throwable throwable) {
        super(error, throwable);
    }
}
