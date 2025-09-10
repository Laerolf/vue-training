package jp.co.company.space.api.shared.dto;

import jakarta.annotation.Nonnull;
import jakarta.json.bind.annotation.JsonbNillable;
import jp.co.company.space.api.shared.exception.ApplicationError;
import jp.co.company.space.api.shared.exception.ApplicationException;
import jp.co.company.space.api.shared.openApi.Examples;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Map;

/**
 * A POJO representing a DTO of an {@link ApplicationError}.
 */
@Schema(name = "Application", description = "The details of an application error.")
public class ApplicationErrorDto {

    /**
     * Creates a {@link ApplicationErrorDto} based on a {@link ApplicationException}.
     *
     * @param exception The base for the {@link ApplicationErrorDto}.
     * @return A {@link ApplicationErrorDto}.
     */
    public static ApplicationErrorDto create(@Nonnull ApplicationException exception) {
        return new ApplicationErrorDto(exception.getKey(), exception.getMessage());
    }

    /**
     * Creates a {@link ApplicationErrorDto} based on a {@link ApplicationError}.
     *
     * @param error The base for the {@link ApplicationErrorDto}.
     * @return A {@link ApplicationErrorDto}.
     */
    public static ApplicationErrorDto create(@Nonnull ApplicationError error) {
        return new ApplicationErrorDto(error.getKey(), error.getDescription());
    }

    /**
     * Creates a {@link ApplicationErrorDto} based on the provided error key and error message.
     *
     * @param errorKey     The key of the error.
     * @param errorMessage The message of the error.
     * @return A {@link ApplicationErrorDto}.
     */
    public static ApplicationErrorDto create(@Nonnull String errorKey, @Nonnull String errorMessage) {
        return new ApplicationErrorDto(errorKey, errorMessage);
    }

    /**
     * The key of the error.
     */
    @Schema(description = "The key of the error.", example = Examples.ERROR_KEY_EXAMPLE)
    public String key;

    /**
     * The message of the error.
     */
    @Schema(description = "The message of the error.", example = Examples.ERROR_KEY_MESSAGE)
    public String message;

    /**
     * The properties of the error.
     */
    @JsonbNillable(false)
    @Schema(description = "The properties of the error.", example = Examples.ERROR_PROPERTIES)
    public Map<String, Object> properties;

    protected ApplicationErrorDto() {
    }

    protected ApplicationErrorDto(@Nonnull String key, @Nonnull String message) {
        this.key = key;
        this.message = message;
    }
}
