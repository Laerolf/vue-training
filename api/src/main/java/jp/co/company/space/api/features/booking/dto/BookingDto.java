package jp.co.company.space.api.features.booking.dto;

import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.user.dto.UserDto;
import jp.co.company.space.api.features.voyage.dto.VoyageDto;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.ZonedDateTime;

/**
 * A POJO representing a DTO of a {@link Booking} instance.
 */
@Schema(description = "The details of a booking.")
public class BookingDto {
    /**
     * Creates a {@link BookingDto} instance based on a {@link Booking} instance.
     *
     * @param booking The base booking instance.
     * @return A {@link BookingDto} instance.
     */
    public static BookingDto create(Booking booking) {
        return new BookingDto(booking.getId(), booking.getCreationDate(), booking.getStatus().getKey(), UserDto.create(booking.getUser()), VoyageDto.create(booking.getVoyage()));
    }

    /**
     * The ID of the booking.
     */
    @Schema(description = "The ID of the booking.", example = "1")
    public String id;

    /**
     * The creation date of the booking.
     */
    @Schema(description = "The creation date of the booking.", example = "2025-04-14T08:49:20.507334+09:00")
    public ZonedDateTime creationDate;

    /**
     * The status of the booking.
     */
    @Schema(description = "The status of the booking.", example = "created")
    public String status;

    /**
     * The user of the booking.
     */
    @Schema(description = "The user of the booking.")
    public UserDto user;

    /**
     * The voyage of the booking.
     */
    @Schema(description = "The voyage of the booking.")
    public VoyageDto voyage;

    protected BookingDto() {
    }

    protected BookingDto(String id, ZonedDateTime creationDate, String status, UserDto user, VoyageDto voyage) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the booking is missing.");
        } else if (creationDate == null) {
            throw new IllegalArgumentException("The creation date of the booking is missing.");
        } else if (status == null) {
            throw new IllegalArgumentException("The status of the booking is missing.");
        } else if (user == null) {
            throw new IllegalArgumentException("The user of the booking is missing.");
        } else if (voyage == null) {
            throw new IllegalArgumentException("The voyage of the booking is missing.");
        }

        this.id = id;
        this.creationDate = creationDate;
        this.status = status;
        this.user = user;
        this.voyage = voyage;
    }
}
