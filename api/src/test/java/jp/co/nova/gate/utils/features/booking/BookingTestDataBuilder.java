package jp.co.nova.gate.utils.features.booking;

import jp.co.nova.gate.api.features.booking.domain.Booking;
import jp.co.nova.gate.api.features.booking.exception.BookingException;
import jp.co.nova.gate.api.features.user.domain.User;
import jp.co.nova.gate.api.features.voyage.domain.Voyage;

/**
 * A test data builder that creates a {@link Booking} for testing purposes.
 */
public class BookingTestDataBuilder {
    /**
     * Creates a new {@link Booking}.
     *
     * @return a new {@link Booking}
     */
    public Booking create(User user, Voyage voyage) throws BookingException {
        return Booking.create(user, voyage);
    }
}
