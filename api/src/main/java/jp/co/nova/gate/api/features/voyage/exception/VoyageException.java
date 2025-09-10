package jp.co.nova.gate.api.features.voyage.exception;

import jp.co.nova.gate.api.shared.exception.DomainException;

/**
 * A {@link DomainException} for voyage exceptions.
 */
public class VoyageException extends DomainException {
    public VoyageException(VoyageError error) {
        super(error);
    }

    public VoyageException(VoyageError error, Throwable throwable) {
        super(error, throwable);
    }
}
