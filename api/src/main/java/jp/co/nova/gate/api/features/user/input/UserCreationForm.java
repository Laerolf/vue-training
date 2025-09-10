package jp.co.nova.gate.api.features.user.input;

import jp.co.nova.gate.api.features.user.domain.User;
import jp.co.nova.gate.api.features.user.exception.UserError;
import jp.co.nova.gate.api.features.user.exception.UserException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.nova.gate.api.shared.openApi.Examples.*;

/**
 * A POJO representing a form for creating a new {@link User}.
 */
@Schema(description = "A form with details for a new user.")
public class UserCreationForm {

    /**
     * Creates a new {@link UserCreationForm} based on the provided values.
     *
     * @param lastName     The last name of the user to create.
     * @param firstName    The first name of the user to create.
     * @param emailAddress The email address of the user to create.
     * @param password     The password of the user to create.
     * @return A new {@link UserCreationForm}.
     */
    public static UserCreationForm create(String lastName, String firstName, String emailAddress, String password) throws UserException {
        return new UserCreationForm(lastName, firstName, emailAddress, password);
    }

    /**
     * The last name of the user to create.
     */
    @Schema(description = "The last name of the user to create.", required = true, example = USER_LAST_NAME_EXAMPLE)
    public String lastName;

    /**
     * The first name of the user to create.
     */
    @Schema(description = "The first name of the user to create.", required = true, example = USER_FIRST_NAME_EXAMPLE)
    public String firstName;

    /**
     * The email address of the user to create.
     */
    @Schema(description = "The email address of the user to create.", required = true, example = USER_EMAIL_ADDRESS_EXAMPLE)
    public String emailAddress;

    /**
     * The password of the user to create.
     */
    @Schema(description = "The password of the user to create.", required = true, example = USER_PASSWORD_EXAMPLE)
    public String password;

    protected UserCreationForm() {
    }

    protected UserCreationForm(String lastName, String firstName, String emailAddress, String password) throws UserException {
        if (lastName == null) {
            throw new UserException(UserError.NEW_MISSING_LAST_NAME);
        } else if (firstName == null) {
            throw new UserException(UserError.NEW_MISSING_FIRST_NAME);
        } else if (emailAddress == null) {
            throw new UserException(UserError.NEW_MISSING_EMAIL_ADDRESS);
        } else if (password == null) {
            throw new UserException(UserError.NEW_MISSING_PASSWORD);
        }

        this.lastName = lastName;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
