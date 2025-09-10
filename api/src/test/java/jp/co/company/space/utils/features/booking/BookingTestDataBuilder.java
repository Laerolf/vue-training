package jp.co.company.space.utils.features.booking;

import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.booking.exception.BookingException;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.voyage.domain.Voyage;

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
