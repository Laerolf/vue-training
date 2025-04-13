package jp.co.company.space.api.features.user.dto;

import jp.co.company.space.api.features.user.domain.User;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * A POJO representing a DTO of a {@link User} instance.
 */
@Schema(name = "UserDto", description = "The details of a user.")
public class UserDto {

    /**
     * Creates a {@link UserDto} instance based on a {@link User} instance.
     * @param user The base user.
     * @return A {@link UserDto} instance.
     */
    public static UserDto create(User user) {
        return new UserDto(user.getId(), user.getLastName(), user.getFirstName());
    }

    /**
     * The ID of the user.
     */
    @Schema(description = "The ID of the user.", required = true, example = "1")
    public String id;

    /**
     * The last name of the user.
     */
    @Schema(description = "The last name of the user.", example = "Jekyll")
    public String lastName;

    /**
     * The first name of the user.
     */
    @Schema(description = "The first name of the user.", example = "Henry")
    public String firstName;

    protected UserDto() {}

    protected UserDto(String id, String lastName, String firstName) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the user is missing.");
        } else if (lastName == null) {
            throw new IllegalArgumentException("The last name of the user is missing.");
        } else if (firstName == null) {
            throw new IllegalArgumentException("The first name of the user is missing.");
        }

        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }
}
