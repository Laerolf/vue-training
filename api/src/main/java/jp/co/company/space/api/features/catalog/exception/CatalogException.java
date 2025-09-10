package jp.co.company.space.api.features.catalog.exception;

import jp.co.company.space.api.shared.exception.DomainException;

/**
 * A {@link DomainException} for catalog exceptions.
 */
public class CatalogException extends DomainException {
    public CatalogException(CatalogError error) {
        super(error);
    }

    public CatalogException(CatalogError error, Throwable throwable) {
        super(error, throwable);
    }
}
