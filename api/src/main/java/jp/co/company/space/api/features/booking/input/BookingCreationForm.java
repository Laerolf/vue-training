package jp.co.company.space.api.features.booking.input;

import jp.co.company.space.api.features.booking.domain.Booking;

/**
 * A POJO representing a form for creating a new {@link Booking} instance.
 */
public class BookingCreationForm {
    /**
     * The ID of the user making this booking.
     */
    public String userId;

    /**
     * The ID of the voyage of this booking.
     */
    public String voyageId;

    protected BookingCreationForm() {}

    public BookingCreationForm(String userId, String voyageId) {
        this.userId = userId;
        this.voyageId = voyageId;
    }
}
