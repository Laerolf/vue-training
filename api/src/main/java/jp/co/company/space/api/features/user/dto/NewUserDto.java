package jp.co.company.space.api.features.user.dto;

import jp.co.company.space.api.features.user.domain.User;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.examples.*;

/**
 * A POJO representing a DTO of a newly created {@link User} instance.
 */
@Schema(name = "NewUser", description = "The details of a newly created user.")
public class NewUserDto {

    /**
     * Creates a {@link NewUserDto} instance based on a {@link User} instance.
     * @param user The base user.
     * @return A {@link NewUserDto} instance.
     */
    public static NewUserDto create(User user) {
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

    protected NewUserDto() {}

    protected NewUserDto(String id, String lastName, String firstName, String emailAddress) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the user is missing.");
        } else if (lastName == null) {
            throw new IllegalArgumentException("The last name of the user is missing.");
        } else if (firstName == null) {
            throw new IllegalArgumentException("The first name of the user is missing.");
        } else if (emailAddress == null) {
            throw new IllegalArgumentException("The email address of the newly created user is missing.");
        }

        this.id =id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
    }
}
