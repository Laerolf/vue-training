package jp.co.nova.gate.api.shared.exception;

import jakarta.annotation.Nonnull;
import jp.co.nova.gate.api.shared.dto.DomainErrorDto;

import java.util.HashMap;
import java.util.Map;

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
     * The error causing the {@link DomainException}.
     */
    private DomainException errorCause;

    /**
     * The properties of the error.
     */
    private final Map<String, Object> properties = new HashMap<>();

    /**
     * When true, include the cause of the exception, if any. Don't otherwise.
     */
    private boolean includeCause = false;

    public DomainErrorDtoBuilder(@Nonnull DomainException exception) {
        errorKey = exception.getKey();
        errorMessage = exception.getMessage();

        if (exception.getCause() instanceof DomainException) {
            errorCause = (DomainException) exception.getCause();
        }
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
     * @return A {@link DomainErrorDtoBuilder}.
     */
    public DomainErrorDtoBuilder withProperty(String key, String value) {
        properties.put(key, value);
        return this;
    }

    /**
     * Makes sure the cause of a {@link DomainException} is included into the properties if there is any exception.
     *
     * @return A {@link DomainErrorDtoBuilder}.
     */
    public DomainErrorDtoBuilder withCause() {
        this.includeCause = true;
        return this;
    }

    /**
     * Creates a {@link DomainErrorDto} based on the provided values.
     *
     * @return A {@link DomainErrorDto}.
     */
    public DomainErrorDto build() {
        DomainErrorDto errorDto = DomainErrorDto.create(errorKey, errorMessage);

        if (includeCause && errorCause != null) {
            properties.put("cause", new DomainErrorDtoBuilder(errorCause).build());
        }

        if (!properties.isEmpty()) {
            errorDto.properties = properties;
        }

        return errorDto;
    }
}
