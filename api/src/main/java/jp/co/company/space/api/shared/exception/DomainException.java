package jp.co.company.space.api.shared.exception;

/**
 * An abstract class representing a domain specific {@link IllegalArgumentException} instance.
 */
public abstract class DomainException extends ApplicationException {

    private final String key;

    public DomainException(DomainError error) {
        super(error);
        this.key = error.getKey();
    }

    public DomainException(DomainError error, Throwable throwable) {
        super(error, throwable);
        this.key = error.getKey();
    }

    public String getKey() {
        return key;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }
}
