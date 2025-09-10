package jp.co.nova.gate.api.features.spaceShuttle.exception;

import jp.co.nova.gate.api.shared.exception.DomainRuntimeException;

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
