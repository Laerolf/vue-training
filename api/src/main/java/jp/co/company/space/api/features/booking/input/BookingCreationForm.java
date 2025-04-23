package jp.co.company.space.api.features.booking.input;

import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.passenger.input.PassengerCreationForm;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

import static jp.co.company.space.api.shared.openApi.examples.ID_EXAMPLE;
import static jp.co.company.space.api.shared.openApi.examples.VOYAGE_ID_EXAMPLE;

/**
 * A POJO representing a form for creating a new {@link Booking} instance.
 */
@Schema(description = "A form with details for a new booking.")
public class BookingCreationForm {
    /**
     * The ID of the user making this booking.
     */
    @Schema(description = "The ID of the user making the booking.", example = ID_EXAMPLE)
    public String userId;

    /**
     * The ID of the voyage for the new booking.
     */
    @Schema(description = "The ID of the voyage for the new booking.", example = VOYAGE_ID_EXAMPLE)
    public String voyageId;

    /**
     * The passengers for the new booking.
     */
    @Schema(description = "The passengers for the new booking.")
    public List<PassengerCreationForm> passengers;

    protected BookingCreationForm() {}

    public BookingCreationForm(String userId, String voyageId, List<PassengerCreationForm> passengers) {
        this.userId = userId;
        this.voyageId = voyageId;
        this.passengers = passengers;
    }
}
