package jp.co.nova.gate.api.shared.exception;

/**
 * An interface for domain exceptions.
 */
public interface DomainError extends ApplicationError {
    String getKey();
    String getDescription();
}
