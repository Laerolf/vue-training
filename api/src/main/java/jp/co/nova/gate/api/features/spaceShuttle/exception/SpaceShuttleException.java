package jp.co.nova.gate.api.features.spaceShuttle.exception;

import jp.co.nova.gate.api.shared.exception.DomainException;

/**
 * A {@link DomainException} for space shuttle exceptions.
 */
public class SpaceShuttleException extends DomainException {
    public SpaceShuttleException(SpaceShuttleError error) {
        super(error);
    }

    public SpaceShuttleException(SpaceShuttleError error, Throwable throwable) {
        super(error, throwable);
    }
}
