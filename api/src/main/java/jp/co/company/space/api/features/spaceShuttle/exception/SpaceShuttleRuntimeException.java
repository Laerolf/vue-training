package jp.co.company.space.api.features.spaceShuttle.exception;

import jp.co.company.space.api.shared.exception.DomainRuntimeException;

/**
 * A {@link DomainRuntimeException} for space shuttle exceptions.
 */
public class SpaceShuttleRuntimeException extends DomainRuntimeException {
    public SpaceShuttleRuntimeException(SpaceShuttleError error) {
        super(error);
    }

    public SpaceShuttleRuntimeException(SpaceShuttleError error, Throwable throwable) {
        super(error, throwable);
    }
}
