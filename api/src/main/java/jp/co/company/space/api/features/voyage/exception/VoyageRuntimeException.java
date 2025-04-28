package jp.co.company.space.api.features.voyage.exception;

import jp.co.company.space.api.shared.exception.DomainRuntimeException;

/**
 * A {@link DomainRuntimeException} instance for voyage exceptions.
 */
public class VoyageRuntimeException extends DomainRuntimeException {
    public VoyageRuntimeException(VoyageError error) {
        super(error);
    }

    public VoyageRuntimeException(VoyageError error, Throwable throwable) {
        super(error, throwable);
    }
}
