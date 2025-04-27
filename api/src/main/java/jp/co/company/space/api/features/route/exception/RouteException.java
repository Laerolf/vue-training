package jp.co.company.space.api.features.route.exception;

import jp.co.company.space.api.shared.exception.DomainException;

/**
 * A {@link DomainException} instance for route exceptions.
 */
public class RouteException extends DomainException {
    public RouteException(RouteError error) {
        super(error);
    }

    public RouteException(RouteError error, Throwable throwable) {
        super(error, throwable);
    }
}
