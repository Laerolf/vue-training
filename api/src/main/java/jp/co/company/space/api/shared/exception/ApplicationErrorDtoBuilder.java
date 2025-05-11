package jp.co.company.space.api.shared.exception;

import jakarta.annotation.Nonnull;
import jp.co.company.space.api.shared.dto.ApplicationErrorDto;

import java.util.HashMap;
import java.util.Map;

/**
 * A builder for the {@link ApplicationErrorDto} class.
 */
public class ApplicationErrorDtoBuilder {

    /**
     * The key of the error.
     */
    private final String errorKey;

    /**
     * The message of the error.
     */
    private final String errorMessage;

    /**
     * The error causing the {@link ApplicationException} instance.
     */
    private ApplicationException errorCause;

    /**
     * The properties of the error.
     */
    private final Map<String, Object> properties = new HashMap<>();

    /**
     * When true, include the cause of the exception, if any. Don't otherwise.
     */
    private boolean includeCause = false;

    public ApplicationErrorDtoBuilder(@Nonnull ApplicationException exception) {
        errorKey = exception.getKey();
        errorMessage = exception.getMessage();

        if (exception.getCause() instanceof ApplicationException) {
            errorCause = (ApplicationException) exception.getCause();
        }
    }

    public ApplicationErrorDtoBuilder(@Nonnull ApplicationError error) {
        errorKey = error.getKey();
        errorMessage = error.getDescription();
    }

    /**
     * Adds a property to the error DTO.
     *
     * @param key   The key of the property to use.
     * @param value The value of the property to use.
     * @return A {@link ApplicationErrorDtoBuilder} instance.
     */
    public ApplicationErrorDtoBuilder withProperty(String key, String value) {
        properties.put(key, value);
        return this;
    }

    /**
     * Makes sure the cause of a {@link ApplicationException} instance is included into the properties if there is any exception.
     *
     * @return A {@link ApplicationErrorDtoBuilder} instance.
     */
    public ApplicationErrorDtoBuilder withCause() {
        this.includeCause = true;
        return this;
    }

    /**
     * Creates a {@link ApplicationErrorDto} instance based on the provided values.
     *
     * @return A {@link ApplicationErrorDto} instance.
     */
    public ApplicationErrorDto build() {
        ApplicationErrorDto errorDto = ApplicationErrorDto.create(errorKey, errorMessage);

        if (includeCause && errorCause != null) {
            properties.put("cause", new ApplicationErrorDtoBuilder(errorCause).build());
        }

        if (!properties.isEmpty()) {
            errorDto.properties = properties;
        }

        return errorDto;
    }
}
