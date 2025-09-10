package jp.co.nova.gate.api.features.spaceShuttleModel.exception;

import jp.co.nova.gate.api.shared.exception.DomainRuntimeException;

/**
 * A {@link DomainRuntimeException} for space shuttle model runtime exceptions.
 */
public class SpaceShuttleModelRuntimeException extends DomainRuntimeException {
    public SpaceShuttleModelRuntimeException(SpaceShuttleModelError error) {
        super(error);
    }

    public SpaceShuttleModelRuntimeException(SpaceShuttleModelError error, Throwable throwable) {
        super(error, throwable);
    }
}
