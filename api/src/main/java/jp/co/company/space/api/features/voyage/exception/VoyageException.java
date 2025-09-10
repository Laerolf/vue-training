package jp.co.company.space.api.features.voyage.exception;

import jp.co.company.space.api.shared.exception.DomainException;

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
