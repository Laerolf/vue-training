package jp.co.company.space.shared;

/**
 * A domain specific {@link IllegalArgumentException}.
 */
public class DomainException extends IllegalArgumentException {

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable throwable) {
        super(message, throwable);
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
