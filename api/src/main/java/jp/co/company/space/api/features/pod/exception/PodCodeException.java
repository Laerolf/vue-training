package jp.co.company.space.api.features.pod.exception;

import jp.co.company.space.api.shared.exception.DomainException;

/**
 * A {@link DomainException} for pod code exceptions.
 */
public class PodCodeException extends DomainException {
    public PodCodeException(PodCodeError error) {
        super(error);
    }

    public PodCodeException(PodCodeError error, Throwable throwable) {
        super(error, throwable);
    }
}