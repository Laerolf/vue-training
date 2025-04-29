package jp.co.company.space.api.shared.exception;

import jakarta.annotation.Nonnull;
import jp.co.company.space.api.shared.dto.DomainErrorDto;

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

    private final Map<String, String> properties = new HashMap<>();

    public DomainErrorDtoBuilder(@Nonnull DomainException exception) {
        errorKey = exception.getKey();
        errorMessage = exception.getMessage();
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
     * Creates a {@link DomainErrorDto} instance based on the provided values.
     *
     * @return A {@link DomainErrorDto} instance.
     */
    public DomainErrorDto build() {
        DomainErrorDto errorDto = DomainErrorDto.create(errorKey, errorMessage);

        if (!properties.isEmpty()) {
            errorDto.properties = properties;
        }

        return errorDto;
    }
}
