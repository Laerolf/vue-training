package jp.co.company.space.api.features.passenger.dto;

import jp.co.company.space.api.features.passenger.domain.Passenger;
import jp.co.company.space.api.features.passenger.exception.PassengerError;
import jp.co.company.space.api.features.passenger.exception.PassengerException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.ZonedDateTime;

import static jp.co.company.space.api.shared.openApi.Examples.*;

/**
 * A POJO representing a DTO of a {@link Passenger} instance.
 */
@Schema(name = "Passenger", description = "The details of a passenger.")
public class PassengerDto {

    /**
     * Creates a {@link PassengerDto} instance based on a {@link Passenger} instance.
     *
     * @param passenger The base for the passenger DTO.
     * @return A {@link PassengerDto} instance.
     */
    public static PassengerDto create(Passenger passenger) throws PassengerException {
        if (passenger == null) {
            throw new PassengerException(PassengerError.MISSING);
        }

        return new PassengerDto(
                passenger.getId(),
                passenger.getCreationDate(),
                passenger.getMealPreference().getKey(),
                passenger.getPackageType().getKey(),
                passenger.getPodReservation().getId(),
                passenger.getBooking().getId(),
                passenger.getVoyage().getId()
        );
    }

    /**
     * The ID of the passenger.
     */
    @Schema(description = "The ID of the passenger.", example = ID_EXAMPLE)
    public String id;

    /**
     * The creation date of the passenger.
     */
    @Schema(description = "The creation date of the passenger.", example = CREATION_DATE_EXAMPLE)
    public ZonedDateTime creationDate;

    /**
     * The meal preference of the user.
     */
    @Schema(description = "The meal preference of the user.", example = MEAL_PREFERENCE_KEY_EXAMPLE)
    public String mealPreference;

    /**
     * The package type assigned to the passenger.
     */
    @Schema(description = "The pod assigned to the passenger on a space shuttle.", example = PACKAGE_TYPE_KEY_EXAMPLE)
    public String packageType;

    /**
     * The pod reservation of the passenger.
     */
    @Schema(description = "The pod reservation ID of the passenger.", example = ID_EXAMPLE)
    public String podReservationId;

    /**
     * The booking ID of the passenger.
     */
    @Schema(description = "The booking ID of the passenger.", example = ID_EXAMPLE)
    public String bookingId;

    /**
     * The voyage ID of the passenger.
     */
    @Schema(description = "The voyage ID of the passenger.", example = VOYAGE_ID_EXAMPLE)
    public String voyageId;

    protected PassengerDto() {
    }

    protected PassengerDto(String id, ZonedDateTime creationDate, String mealPreference, String packageType, String podReservationId, String bookingId, String voyageId) throws PassengerException {
        if (id == null) {
            throw new PassengerException(PassengerError.MISSING_ID);
        } else if (creationDate == null) {
            throw new PassengerException(PassengerError.MISSING_CREATION_DATE);
        } else if (mealPreference == null) {
            throw new PassengerException(PassengerError.MISSING_MEAL_PREFERENCE);
        } else if (packageType == null) {
            throw new PassengerException(PassengerError.MISSING_PACKAGE_TYPE);
        } else if (podReservationId == null) {
            throw new PassengerException(PassengerError.MISSING_POD_RESERVATION_ID);
        } else if (bookingId == null) {
            throw new PassengerException(PassengerError.MISSING_BOOKING_ID);
        } else if (voyageId == null) {
            throw new PassengerException(PassengerError.MISSING_VOYAGE_ID);
        }

        this.id = id;
        this.creationDate = creationDate;
        this.mealPreference = mealPreference;
        this.packageType = packageType;
        this.podReservationId = podReservationId;
        this.bookingId = bookingId;
        this.voyageId = voyageId;
    }
}
