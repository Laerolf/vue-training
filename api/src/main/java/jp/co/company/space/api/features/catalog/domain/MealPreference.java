package jp.co.company.space.api.features.catalog.domain;

import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.passenger.domain.Passenger;

import java.util.Arrays;
import java.util.Optional;

/**
 * An enum representing the meal preference of a {@link Passenger}'s {@link Booking}.
 */
public enum MealPreference {
    BRING_OWN_MEAL("bringOwnMeal", -500),
    STANDARD("standard"),
    VEGETARIAN("vegetarian"),
    VEGAN("vegan"),
    ASIAN("asian"),
    GLUTEN_FREE("glutenFree"),
    HALAL("halal"),
    KOSHER("kosher"),
    CHEFS_CHOICE("chefsChoice", 1000, PackageType.BUSINESS, PackageType.FIRST_CLASS),
    HYDRATION_BOOST("hydrationBoost", 500, PackageType.BUSINESS, PackageType.FIRST_CLASS),
    STASIS_READY("stasisReady", 250, PackageType.BUSINESS, PackageType.FIRST_CLASS);

    /**
     * Searches a {@link MealPreference} instance by its key.
     * @param key The key search for a meal preference.
     * @return An {@link Optional} {@link MealPreference} instance.
     */
    public static Optional<MealPreference> findByKey(String key) {
        return Arrays.stream(MealPreference.values()).filter(mealPreference -> mealPreference.key.equals(key)).findFirst();
    }

    /**
     * The key of the meal preference.
     */
    private final String key;

    /**
     * The additional cost of the meal preference per day if the meal preference is not included in a package type.
     */
    private final double additionalCostPerDay;

    /**
     * The minimum package type for the meal preference.
     */
    private final PackageType availableFrom;

    /**
     * The minimum package type where the meal preference is free of charge.
     */
    private final PackageType freeFrom;

    MealPreference(String key) {
        this.key = key;
        this.additionalCostPerDay = 0;
        this.availableFrom = PackageType.ECONOMY;
        this.freeFrom = PackageType.ECONOMY;
    }

    MealPreference(String key, double additionalCostPerDay) {
        this.key = key;
        this.additionalCostPerDay = additionalCostPerDay;
        this.availableFrom = PackageType.ECONOMY;
        this.freeFrom = PackageType.ECONOMY;
    }

    MealPreference(String key, double additionalCostPerDay, PackageType availableFrom, PackageType freeFrom) {
        this.key = key;
        this.additionalCostPerDay = additionalCostPerDay;
        this.availableFrom = availableFrom;
        this.freeFrom = freeFrom;
    }

    public String getKey() {
        return key;
    }

    public double getAdditionalCostPerDay() {
        return additionalCostPerDay;
    }

    public PackageType getAvailableFrom() {
        return availableFrom;
    }

    public PackageType getFreeFrom() {
        return freeFrom;
    }
}
