package jp.co.company.space.api.features.spaceShuttleModel.exception;

import jp.co.company.space.api.shared.exception.DomainRuntimeException;

/**
 * A {@link DomainRuntimeException} instance for space shuttle model runtime exceptions.
 */
public class SpaceShuttleModelRuntimeException extends DomainRuntimeException {
    public SpaceShuttleModelRuntimeException(SpaceShuttleModelError error) {
        super(error);
    }

    public SpaceShuttleModelRuntimeException(SpaceShuttleModelError error, Throwable throwable) {
        super(error, throwable);
    }
}
