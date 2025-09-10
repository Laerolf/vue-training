package jp.co.company.space.api.features.passenger.domain;

import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.catalog.domain.MealPreference;
import jp.co.company.space.api.features.catalog.domain.PackageType;
import jp.co.company.space.api.features.passenger.exception.PassengerException;

/**
 * A POJO representing a factory creating {@link Passenger}s.
 */
public class PassengerCreationFactory {

    private final Booking booking;
    private final PackageType packageType;
    private final MealPreference mealPreference;

    public PassengerCreationFactory(Booking booking, PackageType packageType, MealPreference mealPreference) {
        this.booking = booking;
        this.packageType = packageType;
        this.mealPreference = mealPreference;
    }

    /**
     * Creates a new {@link Passenger}.
     *
     * @return A {@link Passenger}.
     */
    public Passenger create() throws PassengerException {
        return Passenger.create(mealPreference, packageType, booking, booking.getVoyage());
    }
}
