package jp.co.nova.gate.api.features.pod.exception;

import jp.co.nova.gate.api.shared.exception.DomainError;

/**
 * An enum with pod error messages.
 */
public enum PodError implements DomainError {
    MISSING_CODE("pod.missingCode", "The code of the pod is missing."),
    MISSING_TYPE("pod.missingType", "The type of the pod is missing."),
    MISSING_STATUS("pod.missingStatus", "The status of the pod is missing."),

    INVALID_DECK_NUMBER("pod.invalidDeckNumber", "The deck number of a pod must be higher than 0."),
    INVALID_ROW_NUMBER("pod.invalidRowNumber", "The row number of a pod must be higher than 0."),
    INVALID_COLUMN_NUMBER("pod.invalidColumnNumber", "The column number of a pod must be higher than 0.");

    private final String key;
    private final String description;

    PodError(String key, String description) {
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
