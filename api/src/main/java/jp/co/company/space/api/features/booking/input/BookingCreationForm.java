package jp.co.company.space.api.features.booking.input;

import jp.co.company.space.api.features.booking.domain.Booking;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * A POJO representing a form for creating a new {@link Booking} instance.
 */
@Schema(description = "A form with details for a new booking.")
public class BookingCreationForm {
    /**
     * The ID of the user making this booking.
     */
    @Schema(description = "The ID of the user making the booking.", example = "1")
    public String userId;

    /**
     * The ID of the voyage of this booking.
     */
    @Schema(description = "The ID of the voyage for the new booking.", example = "5f485136-20a8-41f3-9073-4156d32c9c36")
    public String voyageId;

    protected BookingCreationForm() {}

    public BookingCreationForm(String userId, String voyageId) {
        this.userId = userId;
        this.voyageId = voyageId;
    }
}
