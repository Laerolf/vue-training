package jp.co.nova.gate.api.features.booking.domain;

import jp.co.nova.gate.api.features.booking.exception.BookingError;
import jp.co.nova.gate.api.features.booking.exception.BookingException;
import jp.co.nova.gate.api.features.user.domain.User;
import jp.co.nova.gate.api.features.voyage.domain.Voyage;

/**
 * A factory creating a new {@link Booking}.
 */
public class BookingCreationFactory {

    private final User user;
    private final Voyage voyage;

    public BookingCreationFactory(User user, Voyage voyage) throws BookingException {
        if (user == null) {
            throw new BookingException(BookingError.NEW_MISSING_USER);
        } else if (voyage == null) {
            throw new BookingException(BookingError.NEW_MISSING_VOYAGE);
        }

        this.user = user;
        this.voyage = voyage;
    }

    /**
     * Creates a new {@link Booking} based on the provided {@link User} and {@link Voyage}.
     *
     * @return A new {@link Booking}.
     * @throws BookingException When the user or voyage of the booking are missing.
     */
    public Booking create() throws BookingException {
        return Booking.create(user, voyage);
    }
}
