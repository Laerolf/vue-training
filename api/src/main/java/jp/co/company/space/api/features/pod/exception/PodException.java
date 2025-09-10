package jp.co.company.space.api.features.pod.exception;

import jp.co.company.space.api.shared.exception.DomainException;

/**
 * A {@link DomainException} for pod exceptions.
 */
public class PodException extends DomainException {
    public PodException(PodError error) {
        super(error);
    }

    public PodException(PodError error, Throwable throwable) {
        super(error, throwable);
    }
}