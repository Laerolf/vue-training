package jp.co.nova.gate.api.shared.exception;

/**
 * An abstract class representing an application {@link RuntimeException}.
 */
public abstract class ApplicationRuntimeException extends RuntimeException {

    private final String key;

    public ApplicationRuntimeException(ApplicationError error) {
        super(error.getDescription());
        this.key = error.getKey();
    }

    public ApplicationRuntimeException(ApplicationError error, Throwable throwable) {
        super(error.getDescription(), throwable);
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
