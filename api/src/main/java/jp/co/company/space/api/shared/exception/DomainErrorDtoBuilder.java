package jp.co.company.space.api.shared.exception;

import jakarta.annotation.Nonnull;
import jp.co.company.space.api.shared.dto.DomainErrorDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A builder for the {@link DomainErrorDto} class.
 */
public class DomainErrorDtoBuilder {

    /**
     * The key of the error.
     */
    private final String errorKey;

    /**
     * The message of the error.
     */
    private final String errorMessage;

    /**
     * The message of the error causing the {@link DomainException} instance.
     */
    private String errorCauseMessage;

    /**
     * The properties of the error.
     */
    private final Map<String, String> properties = new HashMap<>();

    /**
     * When true, include the cause of the exception, if any. Don't otherwise.
     */
    private boolean includeCause = false;

    public DomainErrorDtoBuilder(@Nonnull DomainException exception) {
        errorKey = exception.getKey();
        errorMessage = exception.getMessage();
        errorCauseMessage = Optional.ofNullable(exception.getCause()).map(Throwable::getMessage).orElse(null);
    }

    public DomainErrorDtoBuilder(@Nonnull DomainError error) {
        errorKey = error.getKey();
        errorMessage = error.getDescription();
    }

    /**
     * Adds a property to the error DTO.
     *
     * @param key   The key of the property to use.
     * @param value The value of the property to use.
     * @return A {@link DomainErrorDtoBuilder} instance.
     */
    public DomainErrorDtoBuilder withProperty(String key, String value) {
        properties.put(key, value);
        return this;
    }

    /**
     * Makes sure the cause of a {@link DomainException} instance is included into the properties if there is any exception.
     *
     * @return A {@link DomainErrorDtoBuilder} instance.
     */
    public DomainErrorDtoBuilder withCause() {
        this.includeCause = true;
        return this;
    }

    /**
     * Creates a {@link DomainErrorDto} instance based on the provided values.
     *
     * @return A {@link DomainErrorDto} instance.
     */
    public DomainErrorDto build() {
        DomainErrorDto errorDto = DomainErrorDto.create(errorKey, errorMessage);

        if (includeCause && errorCauseMessage != null) {
            properties.put("cause", errorCauseMessage);
        }

        if (!properties.isEmpty()) {
            errorDto.properties = properties;
        }

        return errorDto;
    }
}
