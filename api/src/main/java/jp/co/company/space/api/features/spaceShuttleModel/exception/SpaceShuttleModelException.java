package jp.co.company.space.api.features.spaceShuttleModel.exception;

import jp.co.company.space.api.shared.exception.DomainException;

/**
 * A {@link DomainException} instance for space shuttle model exceptions.
 */
public class SpaceShuttleModelException extends DomainException {
    public SpaceShuttleModelException(SpaceShuttleModelError error) {
        super(error);
    }

    public SpaceShuttleModelException(SpaceShuttleModelError error, Throwable throwable) {
        super(error, throwable);
    }
}
