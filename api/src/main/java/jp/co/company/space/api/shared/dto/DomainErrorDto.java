package jp.co.company.space.api.shared.dto;

import jakarta.annotation.Nonnull;
import jakarta.json.bind.annotation.JsonbNillable;
import jp.co.company.space.api.shared.exception.DomainError;
import jp.co.company.space.api.shared.exception.DomainException;
import jp.co.company.space.api.shared.openApi.Examples;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Map;

/**
 * A POJO representing a DTO of a {@link DomainError}.
 */
@Schema(name = "DomainError", description = "The details of a domain specific error.")
public class DomainErrorDto {

    /**
     * Creates a {@link DomainErrorDto} based on a {@link DomainException}.
     *
     * @param exception The base for the {@link DomainErrorDto}.
     * @return A {@link DomainErrorDto}.
     */
    public static DomainErrorDto create(@Nonnull DomainException exception) {
        return new DomainErrorDto(exception.getKey(), exception.getMessage());
    }

    /**
     * Creates a {@link DomainErrorDto} based on a {@link DomainError}.
     *
     * @param error The base for the {@link DomainErrorDto}.
     * @return A {@link DomainErrorDto}.
     */
    public static DomainErrorDto create(@Nonnull DomainError error) {
        return new DomainErrorDto(error.getKey(), error.getDescription());
    }

    /**
     * Creates a {@link DomainErrorDto} based on the provided error key and error message.
     *
     * @param errorKey     The key of the error.
     * @param errorMessage The message of the error.
     * @return A {@link DomainErrorDto}.
     */
    public static DomainErrorDto create(@Nonnull String errorKey, @Nonnull String errorMessage) {
        return new DomainErrorDto(errorKey, errorMessage);
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

    protected DomainErrorDto() {
    }

    protected DomainErrorDto(@Nonnull String key, @Nonnull String message) {
        this.key = key;
        this.message = message;
    }
}
