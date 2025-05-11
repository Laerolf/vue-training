package jp.co.company.space.api.shared.exception;

/**
 * An interface for application exceptions.
 */
public interface ApplicationError {
    String getKey();
    String getDescription();
}
