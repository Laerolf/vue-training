package jp.co.company.space.api.shared.dto;

import jp.co.company.space.api.shared.exception.DomainError;
import jp.co.company.space.api.shared.exception.DomainException;
import jp.co.company.space.api.shared.openApi.Examples;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * A POJO representing a DTO of a {@link DomainError} instance.
 */
@Schema(name = "DomainError", description = "The details of an error.")
public class DomainErrorDto {

    /**
     * Creates a {@link DomainErrorDto} instance based on a {@link DomainException} instance.
     *
     * @param exception The base for the {@link DomainErrorDto} instance.
     * @return A {@link DomainErrorDto} instance.
     */
    public static DomainErrorDto create(DomainException exception) {
        return new DomainErrorDto(exception.getKey(), exception.getMessage());
    }

    /**
     * Creates a {@link DomainErrorDto} instance based on a {@link DomainError} instance.
     *
     * @param error The base for the {@link DomainErrorDto} instance.
     * @return A {@link DomainErrorDto} instance.
     */
    public static DomainErrorDto create(DomainError error) {
        return new DomainErrorDto(error.getKey(), error.getDescription());
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

    protected DomainErrorDto() {
    }

    protected DomainErrorDto(String key, String message) {
        this.key = key;
        this.message = message;
    }
}
