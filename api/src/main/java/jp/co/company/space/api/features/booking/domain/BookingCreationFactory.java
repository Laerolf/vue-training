package jp.co.company.space.api.features.booking.domain;

import jp.co.company.space.api.features.booking.exception.BookingError;
import jp.co.company.space.api.features.booking.exception.BookingException;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.voyage.domain.Voyage;

/**
 * A factory creating a new {@link Booking} instance.
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
     * Creates a new {@link Booking} instance based on the provided {@link User} and {@link Voyage} instance.
     *
     * @return A new {@link Booking} instance.
     * @throws BookingException When the user or voyage of the booking are missing.
     */
    public Booking create() throws BookingException {
        return Booking.create(user, voyage);
    }
}
