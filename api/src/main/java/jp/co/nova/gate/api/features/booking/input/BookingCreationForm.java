package jp.co.nova.gate.api.features.booking.input;

import jp.co.nova.gate.api.features.booking.domain.Booking;
import jp.co.nova.gate.api.features.passenger.input.PassengerCreationForm;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

import static jp.co.nova.gate.api.shared.openApi.Examples.VOYAGE_ID_EXAMPLE;

/**
 * A POJO representing a form for creating a new {@link Booking}.
 */
@Schema(description = "A form with details for a new booking.")
public class BookingCreationForm {
    /**
     * The ID of the voyage for the new booking.
     */
    @Schema(description = "The ID of the voyage for the new booking.", required = true, example = VOYAGE_ID_EXAMPLE)
    public String voyageId;

    /**
     * The passengers for the new booking.
     */
    @Schema(description = "The passengers for the new booking.", required = true)
    public List<PassengerCreationForm> passengers;

    protected BookingCreationForm() {
    }

    public BookingCreationForm(String voyageId, List<PassengerCreationForm> passengers) {
        this.voyageId = voyageId;
        this.passengers = passengers;
    }
}
