package jp.co.company.space.api.features.booking.dto;

import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.passenger.domain.Passenger;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.ZonedDateTime;
import java.util.List;

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
        return new BookingDto(
                booking.getId(),
                booking.getCreationDate(),
                booking.getStatus().getKey(),
                booking.getUser().getId(),
                booking.getVoyage().getId(),
                booking.getPassengers().stream().map(Passenger::getId).toList()
        );
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
     * TODO: Improve Open API documentation
     * Provide examples = jp.co.company.space.api.features.route.dto.RouteBasicDto
     */
    /**
     * The user of the booking.
     */
    @Schema(description = "The user ID of the booking.")
    public String userId;

    /**
     * The voyage of the booking.
     */
    @Schema(description = "The voyage ID of the booking.")
    public String voyageId;

    /**
     * The passengers of the booking.
     */
    @Schema(description = "The passenger IDs of the booking.")
    public List<String> passengerIds;

    protected BookingDto() {
    }

    protected BookingDto(String id, ZonedDateTime creationDate, String status, String userId, String voyageId, List<String> passengerIds) {
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
        } else if (passengerIds == null) {
            throw new IllegalArgumentException("The list of passenger IDs of the booking is missing.");
        }

        this.id = id;
        this.creationDate = creationDate;
        this.status = status;
        this.userId = userId;
        this.voyageId = voyageId;
        this.passengerIds = passengerIds;
    }
}
