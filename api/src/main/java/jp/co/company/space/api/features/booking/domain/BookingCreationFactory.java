package jp.co.company.space.api.features.booking.domain;

import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.voyage.domain.Voyage;

/**
 * A factory creating a new {@link Booking} instance.
 */
public class BookingCreationFactory {

    private final User user;
    private final Voyage voyage;

    public BookingCreationFactory(User user, Voyage voyage) {
        if (user == null) {
            throw new IllegalArgumentException("The new booking's user is missing.");
        } else if (voyage == null) {
            throw new IllegalArgumentException("The new booking's voyage is missing.");
        }

        this.user = user;
        this.voyage = voyage;
    }

    /**
     * Creates a new {@link Booking} instance based on the provided {@link User} and {@link Voyage} instance.
     * @return A new {@link Booking} instance.
     */
    public Booking create() {
        return Booking.create(user, voyage);
    }
}
