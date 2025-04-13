package jp.co.company.space.api.features.user.input;

import jp.co.company.space.api.features.user.domain.User;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * A POJO representing a form for creating a new {@link User} instance.
 */
public class UserCreationForm {

    /**
     * Creates a new {@link UserCreationForm} instance based on the provided values.
     *
     * @param lastName     The last name of the user to create.
     * @param firstName    The first name of the user to create.
     * @param emailAddress The email address of the user to create.
     * @param password     The password of the user to create.
     * @return A new {@link UserCreationForm} instance.
     */
    public static UserCreationForm create(String lastName, String firstName, String emailAddress, String password) {
        return new UserCreationForm(lastName, firstName, emailAddress, password);
    }

    /**
     * The last name of the user to create.
     */
    @Schema(description = "The last name of the user to create.", example = "Jekyll")
    public String lastName;

    /**
     * The first name of the user to create.
     */
    @Schema(description = "The first name of the user to create.", example = "Henry")
    public String firstName;

    /**
     * The email address of the user to create.
     */
    @Schema(description = "The email address of the user to create.", example = "jekyll.henry@test.test")
    public String emailAddress;

    /**
     * The password of the user to create.
     */
    @Schema(description = "The password of the user to create.", example = "test")
    public String password;

    protected UserCreationForm() {
    }

    protected UserCreationForm(String lastName, String firstName, String emailAddress, String password) {
        if (lastName == null) {
            throw new IllegalArgumentException("The last name of the user to create is missing.");
        } else if (firstName == null) {
            throw new IllegalArgumentException("The first name of the user to create is missing.");
        } else if (emailAddress == null) {
            throw new IllegalArgumentException("The email address of the user to create is missing.");
        } else if (password == null) {
            throw new IllegalArgumentException("The password of the user to create is missing.");
        }

        this.lastName = lastName;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
