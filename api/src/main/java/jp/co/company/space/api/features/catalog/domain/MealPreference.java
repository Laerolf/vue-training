package jp.co.company.space.api.features.catalog.domain;

import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.passenger.domain.Passenger;

import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * An enum representing the meal preference of a {@link Passenger}'s {@link Booking}.
 */
public enum MealPreference implements CatalogItem {
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

    private static final String CATALOG_PREFIX = "mealPreference";

    /**
     * Searches a {@link MealPreference} by its key.
     * @param key The key search for a meal preference.
     * @return An {@link Optional} {@link MealPreference}.
     */
    public static Optional<MealPreference> findByKey(String key) {
        return Arrays.stream(MealPreference.values()).filter(mealPreference -> mealPreference.key.equals(key)).findFirst();
    }

    /**
     * The key of the meal preference.
     */
    private final String key;

    /**
     * The label of the meal preference.
     */
    private final String label;

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
        this(key, 0, PackageType.ECONOMY, PackageType.ECONOMY);
    }

    MealPreference(String key, double additionalCostPerDay) {
        this(key, additionalCostPerDay, PackageType.ECONOMY, PackageType.ECONOMY);
    }

    MealPreference(String key, double additionalCostPerDay, PackageType availableFrom, PackageType freeFrom) {
        this.key = key;
        this.label = new StringJoiner(".").add(CATALOG_PREFIX).add(key).toString();
        this.additionalCostPerDay = additionalCostPerDay;
        this.availableFrom = availableFrom;
        this.freeFrom = freeFrom;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getLabel() {
        return label;
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
