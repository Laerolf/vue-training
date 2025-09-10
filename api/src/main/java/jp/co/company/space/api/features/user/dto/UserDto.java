package jp.co.company.space.api.features.user.dto;

import jp.co.company.space.api.features.booking.dto.BookingDto;
import jp.co.company.space.api.features.user.domain.User;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

import static jp.co.company.space.api.shared.openApi.Examples.*;

/**
 * A POJO representing a DTO of a {@link User}.
 */
@Schema(name = "User", description = "The details of a user.")
public class UserDto {

    /**
     * Creates a {@link UserDto} based on a {@link User}.
     * @param user The base user.
     * @return A {@link UserDto}.
     */
    public static UserDto create(User user) {
        return new UserDto(user.getId(), user.getLastName(), user.getFirstName(), user.getBookings().stream().map(BookingDto::create).toList());
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
     * The bookings of the user.
     */
    @Schema(description = "The bookings of the user.")
    public List<BookingDto> bookings;

    protected UserDto() {}

    protected UserDto(String id, String lastName, String firstName, List<BookingDto> bookings) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the user is missing.");
        } else if (lastName == null) {
            throw new IllegalArgumentException("The last name of the user is missing.");
        } else if (firstName == null) {
            throw new IllegalArgumentException("The first name of the user is missing.");
        } else if (bookings == null) {
            throw new IllegalArgumentException("The bookings of the user are missing.");
        }

        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.bookings = bookings;
    }
}
