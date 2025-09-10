package jp.co.nova.gate.api.features.route.exception;

import jp.co.nova.gate.api.shared.exception.DomainRuntimeException;

/**
 * A {@link DomainRuntimeException} for route exceptions.
 */
public class RouteRuntimeException extends DomainRuntimeException {
    public RouteRuntimeException(RouteError error) {
        super(error);
    }

    public RouteRuntimeException(RouteError error, Throwable throwable) {
        super(error, throwable);
    }
}
