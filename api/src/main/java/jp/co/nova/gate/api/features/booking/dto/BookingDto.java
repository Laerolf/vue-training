package jp.co.nova.gate.api.features.booking.dto;

import jp.co.nova.gate.api.features.booking.domain.Booking;
import jp.co.nova.gate.api.features.booking.exception.BookingError;
import jp.co.nova.gate.api.features.booking.exception.BookingException;
import jp.co.nova.gate.api.features.passenger.domain.Passenger;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.ZonedDateTime;
import java.util.List;

import static jp.co.nova.gate.api.shared.openApi.Examples.*;

/**
 * A POJO representing a DTO of a {@link Booking}.
 */
@Schema(name = "Booking", description = "The details of a booking.")
public class BookingDto {
    /**
     * Creates a {@link BookingDto} based on a {@link Booking}.
     *
     * @param booking The base booking.
     * @return A {@link BookingDto}.
     * @throws BookingException When the booking is missing or the user, voyage or passengers of the booking are missing.
     */
    public static BookingDto create(Booking booking) throws BookingException {
        if (booking == null) {
            throw new BookingException(BookingError.MISSING);
        } else if (booking.getUser() == null) {
            throw new BookingException(BookingError.MISSING_USER);
        } else if (booking.getVoyage() == null) {
            throw new BookingException(BookingError.MISSING_VOYAGE);
        } else if (booking.getPassengers() == null) {
            throw new BookingException(BookingError.MISSING_PASSENGERS);
        }

        return new BookingDto(
                booking.getId(),
                booking.getCreationDate(),
                booking.getStatus().getLabel(),
                booking.getUser().getId(),
                booking.getVoyage().getId(),
                booking.getPassengers().stream().map(Passenger::getId).toList()
        );
    }

    /**
     * The ID of the booking.
     */
    @Schema(description = "The ID of the booking.", example = ID_EXAMPLE)
    public String id;

    /**
     * The creation date of the booking.
     */
    @Schema(description = "The creation date of the booking.", example = CREATION_DATE_EXAMPLE)
    public ZonedDateTime creationDate;

    /**
     * The status of the booking.
     */
    @Schema(description = "The status of the booking.", example = BOOKING_STATUS_EXAMPLE)
    public String status;

    /**
     * The user of the booking.
     */
    @Schema(description = "The user ID of the booking.", example = ID_EXAMPLE)
    public String userId;

    /**
     * The voyage of the booking.
     */
    @Schema(description = "The voyage ID of the booking.", example = VOYAGE_ID_EXAMPLE)
    public String voyageId;

    /**
     * The passengers of the booking.
     */
    @Schema(description = "The passenger IDs of the booking.", example = BOOKING_PASSENGER_ID_EXAMPLE)
    public List<String> passengerIds;

    protected BookingDto() {
    }

    protected BookingDto(String id, ZonedDateTime creationDate, String status, String userId, String voyageId, List<String> passengerIds) throws BookingException {
        if (id == null) {
            throw new BookingException(BookingError.MISSING_ID);
        } else if (creationDate == null) {
            throw new BookingException(BookingError.MISSING_CREATION_DATE);
        } else if (status == null) {
            throw new BookingException(BookingError.MISSING_STATUS);
        } else if (userId == null) {
            throw new BookingException(BookingError.MISSING_USER_ID);
        } else if (voyageId == null) {
            throw new BookingException(BookingError.MISSING_VOYAGE_ID);
        } else if (passengerIds == null) {
            throw new BookingException(BookingError.MISSING_PASSENGER_IDS);
        }

        this.id = id;
        this.creationDate = creationDate;
        this.status = status;
        this.userId = userId;
        this.voyageId = voyageId;
        this.passengerIds = passengerIds;
    }
}
