package jp.co.company.space.api.features.spaceShuttle.exception;

import jp.co.company.space.api.shared.exception.DomainException;

/**
 * A {@link DomainException} instance for space shuttle exceptions.
 */
public class SpaceShuttleException extends DomainException {
    public SpaceShuttleException(SpaceShuttleError error) {
        super(error);
    }

    public SpaceShuttleException(SpaceShuttleError error, Throwable throwable) {
        super(error, throwable);
    }
}
