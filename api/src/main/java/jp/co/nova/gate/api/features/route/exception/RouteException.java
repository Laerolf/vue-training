package jp.co.nova.gate.api.features.route.exception;

import jp.co.nova.gate.api.shared.exception.DomainException;

/**
 * A {@link DomainException} for route exceptions.
 */
public class RouteException extends DomainException {
    public RouteException(RouteError error) {
        super(error);
    }

    public RouteException(RouteError error, Throwable throwable) {
        super(error, throwable);
    }
}
