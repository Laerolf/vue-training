package jp.co.nova.gate.api.features.user.dto;

import jp.co.nova.gate.api.features.user.domain.User;
import jp.co.nova.gate.api.features.user.exception.UserError;
import jp.co.nova.gate.api.features.user.exception.UserException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.nova.gate.api.shared.openApi.Examples.*;

/**
 * A POJO representing a DTO of a newly created {@link User}.
 */
@Schema(name = "NewUser", description = "The details of a newly created user.")
public class NewUserDto {

    /**
     * Creates a {@link NewUserDto} based on a {@link User}.
     *
     * @param user The base user.
     * @return A {@link NewUserDto}.
     */
    public static NewUserDto create(User user) throws UserException {
        return new NewUserDto(user.getId(), user.getLastName(), user.getFirstName(), user.getEmailAddress());
    }

    /**
     * The ID of the user.
     */
    @Schema(description = "The ID of the user.", example = ID_EXAMPLE)
    public String id;

    /**
     * The last name of the user.
     */
    @Schema(description = "The last name of the user.", example = USER_LAST_NAME_EXAMPLE)
    public String lastName;

    /**
     * The first name of the user.
     */
    @Schema(description = "The first name of the user.", example = USER_FIRST_NAME_EXAMPLE)
    public String firstName;

    /**
     * The email address of the user.
     */
    @Schema(description = "The email address of the user.", example = USER_EMAIL_ADDRESS_EXAMPLE)
    public String emailAddress;

    protected NewUserDto() {
    }

    protected NewUserDto(String id, String lastName, String firstName, String emailAddress) throws UserException {
        if (id == null) {
            throw new UserException(UserError.NEW_MISSING_ID);
        } else if (lastName == null) {
            throw new UserException(UserError.NEW_MISSING_LAST_NAME);
        } else if (firstName == null) {
            throw new UserException(UserError.NEW_MISSING_FIRST_NAME);
        } else if (emailAddress == null) {
            throw new UserException(UserError.NEW_MISSING_EMAIL_ADDRESS);
        }

        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
    }
}
