package jp.co.company.space.api.features.passenger.domain;

import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.catalog.domain.MealPreference;
import jp.co.company.space.api.features.catalog.domain.PackageType;

/**
 * A POJO representing a factory creating {@link Passenger} instances.
 */
public class PassengerCreationFactory {

    private final Booking booking;
    private final PackageType packageType;
    private final MealPreference mealPreference;

    public PassengerCreationFactory(Booking booking, PackageType packageType, MealPreference mealPreference) {
        // TODO: add validation to factories
        this.booking = booking;
        this.packageType = packageType;
        this.mealPreference = mealPreference;
    }

    /**
     * Creates a new {@link Passenger} instance.
     *
     * @return A {@link Passenger} instance.
     */
    public Passenger create() {
        return Passenger.create(mealPreference, packageType, booking, booking.getVoyage());
    }
}
