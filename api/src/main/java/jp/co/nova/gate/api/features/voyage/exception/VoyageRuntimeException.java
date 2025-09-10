package jp.co.nova.gate.api.features.voyage.exception;

import jp.co.nova.gate.api.shared.exception.DomainRuntimeException;

/**
 * A {@link DomainRuntimeException} for voyage exceptions.
 */
public class VoyageRuntimeException extends DomainRuntimeException {
    public VoyageRuntimeException(VoyageError error) {
        super(error);
    }

    public VoyageRuntimeException(VoyageError error, Throwable throwable) {
        super(error, throwable);
    }
}
