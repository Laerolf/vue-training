package jp.co.company.space.api.features.user.dto;

import jp.co.company.space.api.features.user.domain.User;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * A POJO representing a DTO of a newly created {@link User} instance.
 */
@Schema(name = "NewUserDto", description = "The details of a newly created user.")
public class NewUserDto extends UserDto {

    /**
     * Creates a {@link NewUserDto} instance based on a {@link User} instance.
     * @param user The base user.
     * @return A {@link NewUserDto} instance.
     */
    public static NewUserDto create(User user) {
        return new NewUserDto(user.getId(), user.getLastName(), user.getFirstName(), user.getEmailAddress());
    }

    /**
     * The email address of the user.
     */
    @Schema(description = "The email address of the user.", example = "jekyll.henry@test.test")
    public String emailAddress;

    protected NewUserDto() {}

    protected NewUserDto(String id, String lastName, String firstName, String emailAddress) {
        super(id, lastName, firstName);

        if (emailAddress == null) {
            throw new IllegalArgumentException("The email address of the newly created user is missing.");
        }

        this.emailAddress = emailAddress;
    }
}
