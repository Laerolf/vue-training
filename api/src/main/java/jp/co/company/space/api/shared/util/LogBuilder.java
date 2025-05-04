package jp.co.company.space.api.shared.util;

import jp.co.company.space.api.shared.exception.DomainError;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A POJO representing a builder for a log message.
 */
public class LogBuilder {

    /**
     * The base message in the log message.
     */
    private final String message;

    /**
     * The key in the log message.
     */
    private String key;

    /**
     * The exception in the log message.
     */
    private Throwable exception;

    /**
     * The properties in the log message.
     */
    private final Map<String, String> properties = new LinkedHashMap<>();

    /**
     * Creates a new {@link LogBuilder} instance based on a message.
     */
    public LogBuilder(String message) throws IllegalArgumentException {
        if (message == null) {
            throw new IllegalArgumentException("The base message of the log builder is missing.");
        }

        this.message = message;
    }

    /**
     * Creates a new {@link LogBuilder} instance based on a {@link DomainError} instance, setting its key as the key of the log message to create.
     *
     * @param error The domain error to use.
     */
    public LogBuilder(DomainError error) throws IllegalArgumentException {
        if (error == null) {
            throw new IllegalArgumentException("The domain error of the log builder is missing.");
        } else if (error.getDescription() == null) {
            throw new IllegalArgumentException("The base message of the log builder is missing.");
        }

        this.message = error.getDescription();
        this.key = error.getKey();
    }

    /**
     * Adds an {@link Throwable} instance's message to the log message.
     *
     * @param exception The throwable to use.
     * @return A {@link LogBuilder} instance.
     */
    public LogBuilder withException(Throwable exception) throws IllegalArgumentException {
        if (exception == null) {
            throw new IllegalArgumentException("The exception of the log message is missing.");
        }

        this.exception = exception;
        return this;
    }

    /**
     * Adds a key to the log message.
     *
     * @param key The key to use.
     * @return A {@link LogBuilder} instance.
     */
    public LogBuilder withKey(String key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("The key of the log message is missing.");
        }

        this.key = key;
        return this;
    }

    /**
     * Adds a property to the log message.
     *
     * @param key   The key of the property to use.
     * @param value The value of the property to use.
     * @return A {@link LogBuilder} instance.
     */
    public LogBuilder withProperty(String key, String value) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("The key of the log message property is missing.");
        } else if (value == null) {
            throw new IllegalArgumentException("The value of the log message property is missing.");
        }

        properties.put(key, value);
        return this;
    }

    /**
     * Adds a property to the log message.
     *
     * @param key   The key of the property to use.
     * @param value The value of the property to use.
     * @return A {@link LogBuilder} instance.
     */
    public LogBuilder withProperty(String key, Integer value) throws IllegalArgumentException {
        return withProperty(key, String.valueOf(value));
    }

    /**
     * Creates a log message based on the provided values.
     *
     * @return A log message.
     */
    public String build() {
        String logMessage;

        if (key != null) {
            logMessage = "[" + key + "] " + message;
        } else {
            logMessage = message;
        }

        if (exception != null) {
            logMessage = logMessage.replaceAll("[!.,;:?\\s]+$", "");
            logMessage += ": " + exception.getMessage();
        }

        if (!properties.isEmpty()) {
            String detailString = properties.entrySet().stream()
                    .map(e -> e.getKey() + "=" + e.getValue())
                    .collect(Collectors.joining(", "));

            logMessage += " [" + detailString + "]";
        }

        return logMessage;
    }
}
