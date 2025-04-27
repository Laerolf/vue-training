package jp.co.company.space.api.features.pod.exception;

import jp.co.company.space.api.shared.exception.DomainException;

/**
 * A {@link DomainException} instance for pod reservation exceptions.
 */
public class PodReservationException extends DomainException {
    public PodReservationException(PodReservationError error) {
        super(error);
    }

    public PodReservationException(PodReservationError error, Throwable throwable) {
        super(error, throwable);
    }
}