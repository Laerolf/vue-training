package jp.co.nova.gate.api.features.pod.exception;

import jp.co.nova.gate.api.shared.exception.DomainException;

/**
 * A {@link DomainException} for pod reservation exceptions.
 */
public class PodReservationException extends DomainException {
    public PodReservationException(PodReservationError error) {
        super(error);
    }

    public PodReservationException(PodReservationError error, Throwable throwable) {
        super(error, throwable);
    }
}