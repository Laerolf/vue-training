package jp.co.company.space.api.features.booking.dto;

import jp.co.company.space.api.features.booking.domain.Booking;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.ZonedDateTime;

/**
 * A POJO representing a brief DTO of a {@link Booking} instance.
 */
@Schema(description = "The brief details of a booking.")
public class BookingBasicDto {
    /**
     * Creates a {@link BookingBasicDto} instance based on a {@link Booking} instance.
     *
     * @param booking The base booking instance.
     * @return A {@link BookingBasicDto} instance.
     */
    public static BookingBasicDto create(Booking booking) {
        return new BookingBasicDto(booking.getId(), booking.getCreationDate(), booking.getStatus().getKey(), booking.getUser().getId(), booking.getVoyage().getId());
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
     * The user ID of the booking.
     */
    @Schema(description = "The user ID of the booking.")
    public String userId;

    /**
     * The voyage ID of the booking.
     */
    @Schema(description = "The voyage ID of the booking.")
    public String voyageId;

    protected BookingBasicDto() {
    }

    protected BookingBasicDto(String id, ZonedDateTime creationDate, String status, String userId, String voyageId) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the booking is missing.");
        } else if (creationDate == null) {
            throw new IllegalArgumentException("The creation date of the booking is missing.");
        } else if (status == null) {
            throw new IllegalArgumentException("The status of the booking is missing.");
        } else if (userId == null) {
            throw new IllegalArgumentException("The user ID of the booking is missing.");
        } else if (voyageId == null) {
            throw new IllegalArgumentException("The voyage ID of the booking is missing.");
        }

        this.id = id;
        this.creationDate = creationDate;
        this.status = status;
        this.userId = userId;
        this.voyageId = voyageId;
    }
}
