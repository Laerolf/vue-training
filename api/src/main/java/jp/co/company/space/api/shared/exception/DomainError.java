package jp.co.company.space.api.shared.exception;

/**
 * An interface for domain exceptions.
 */
public interface DomainError {
    String getKey();
    String getDescription();
}
