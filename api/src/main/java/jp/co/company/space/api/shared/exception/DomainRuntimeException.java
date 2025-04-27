package jp.co.company.space.api.shared.exception;

/**
 * An abstract class representing a domain specific {@link RuntimeException} instance.
 */
public abstract class DomainRuntimeException extends RuntimeException {

    private final DomainError error;

    public DomainRuntimeException(DomainError error) {
        super(error.getDescription());
        this.error = error;
    }

    public DomainRuntimeException(DomainError error, Throwable throwable) {
        super(error.getDescription(), throwable);
        this.error = error;
    }

    public String getKey() {
        return error.getKey();
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
