package jp.co.nova.gate.api.shared.exception;

/**
 * An interface for application exceptions.
 */
public interface ApplicationError {
    String getKey();
    String getDescription();
}
