package jp.co.nova.gate.api.features.pod.exception;

import jp.co.nova.gate.api.shared.exception.DomainError;

/**
 * An enum with pod code error messages.
 */
public enum PodCodeError implements DomainError {
    MISSING_CODE_PREFIX("pod.missingCodePrefix", "The prefix for the pod code is missing."),

    INVALID_NUMBER("pod.invalidNumber", "The number of a pod must be higher than 0."),
    INVALID_DECK_NUMBER("pod.invalidDeckNumber", "The deck number of a pod must be higher than 0."),;

    private final String key;
    private final String description;

    PodCodeError(String key, String description) {
        this.key = key;
        this.description = description;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
