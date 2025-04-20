package jp.co.company.space.api.features.passenger.dto;

import jp.co.company.space.api.features.passenger.domain.Passenger;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.ZonedDateTime;

/**
 * A POJO representing a DTO of a {@link Passenger} instance.
 */
@Schema(description = "The details of a passenger.")
public class PassengerDto {

    /**
     * Creates a {@link PassengerDto} instance based on a {@link Passenger} instance.
     *
     * @param passenger The base for the passenger DTO.
     * @return A {@link PassengerDto} instance.
     */
    public static PassengerDto create(Passenger passenger) {
        if (passenger == null) {
            throw new IllegalArgumentException("The base passenger for this passenger dto is missing.");
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
    @Schema(description = "The ID of the passenger.", example = "1")
    public String id;

    /**
     * The creation date of the passenger.
     */
    @Schema(description = "The creation date of the passenger.", example = "2025-04-19T08:49:20.507334+09:00")
    public ZonedDateTime creationDate;

    /**
     * The meal preference of the user.
     */
    @Schema(description = "The meal preference of the user.", example = "standard")
    public String mealPreference;

    /**
     * The package type assigned to the passenger.
     */
    @Schema(description = "The pod assigned to the passenger on a space shuttle.", example = "economy")
    public String packageType;

    /**
     * The pod reservation of the passenger.
     */
    @Schema(description = "The pod reservation ID of the passenger.")
    public String podReservationId;

    /**
     * The booking ID of the passenger.
     */
    @Schema(description = "The booking ID of the passenger.")
    public String bookingId;

    /**
     * The voyage ID of the passenger.
     */
    @Schema(description = "The voyage ID of the passenger.")
    public String voyageId;

    protected PassengerDto() {
    }

    protected PassengerDto(String id, ZonedDateTime creationDate, String mealPreference, String packageType, String podReservationId, String bookingId, String voyageId) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the passenger DTO is missing.");
        } else if (creationDate == null) {
            throw new IllegalArgumentException("The creation date of the passenger DTO is missing.");
        } else if (mealPreference == null) {
            throw new IllegalArgumentException("The meal preference of the passenger DTO is missing.");
        } else if (packageType == null) {
            throw new IllegalArgumentException("The package type of the passenger DTO is missing.");
        } else if (podReservationId == null) {
            throw new IllegalArgumentException("The pod reservation ID of the passenger DTO is missing.");
        } else if (bookingId == null) {
            throw new IllegalArgumentException("The booking ID of the passenger DTO is missing.");
        } else if (voyageId == null) {
            throw new IllegalArgumentException("The voyage ID of the passenger DTO is missing.");
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
