package jp.co.nova.gate.api.shared.exception;

/**
 * An abstract class representing a domain specific {@link RuntimeException}.
 */
public abstract class DomainRuntimeException extends ApplicationRuntimeException {

    private final DomainError error;

    public DomainRuntimeException(DomainError error) {
        super(error);
        this.error = error;
    }

    public DomainRuntimeException(DomainError error, Throwable throwable) {
        super(error, throwable);
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
