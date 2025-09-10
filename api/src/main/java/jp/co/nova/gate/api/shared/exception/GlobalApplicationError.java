package jp.co.nova.gate.api.shared.exception;

/**
 * An enum with application error messages.
 */
public enum GlobalApplicationError implements ApplicationError {
    UNSPECIFIED("global.unspecified", "Something went wrong.");

    private final String key;
    private final String description;

    GlobalApplicationError(String key, String description) {
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
