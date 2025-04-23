package jp.co.company.space.utils.features.passenger;

import jp.co.company.space.api.features.catalog.domain.MealPreference;
import jp.co.company.space.api.features.catalog.domain.PackageType;
import jp.co.company.space.api.features.catalog.domain.PodType;
import jp.co.company.space.api.features.passenger.input.PassengerCreationForm;
import jp.co.company.space.api.features.pod.domain.PodCodeFactory;

import java.util.Optional;

/**
 * A utility class that creates {@link PassengerCreationForm} instances for tests.
 */
public class PassengerCreationFormTestDataBuilder {

    private final static PackageType DEFAULT_PACKAGE_TYPE = PackageType.ECONOMY;
    private final static MealPreference DEFAULT_MEAL_PREFERENCE = MealPreference.STANDARD;

    private final static int DEFAULT_POD_DECK_NUMBER = 1;
    private final static int DEFAULT_POD_NUMBER = 1;

    private PackageType packageType;
    private MealPreference mealPreference;

    public PassengerCreationFormTestDataBuilder() {
    }

    public PassengerCreationFormTestDataBuilder withPackageType(PackageType packageType) {
        this.packageType = packageType;
        return this;
    }

    public PassengerCreationFormTestDataBuilder withMealPreference(MealPreference mealPreference) {
        this.mealPreference = mealPreference;
        return this;
    }

    /**
     * Creates a new test {@link PassengerCreationForm} instance.
     *
     * @return A {@link PassengerCreationForm} instance.
     */
    public PassengerCreationForm create() {
        PackageType selectedPackageType = Optional.ofNullable(packageType).orElse(DEFAULT_PACKAGE_TYPE);

        PodType selectedPodType = PodType.findByPackageType(selectedPackageType).orElseThrow();
        String podCode = new PodCodeFactory(selectedPodType.getPodCodePrefix(), DEFAULT_POD_DECK_NUMBER, DEFAULT_POD_NUMBER).create();

        PassengerCreationForm passengerCreationForm = new PassengerCreationForm(podCode, selectedPackageType.getKey(), Optional.ofNullable(mealPreference).orElse(DEFAULT_MEAL_PREFERENCE).getKey());
        cleanUp();

        return passengerCreationForm;
    }

    private void cleanUp() {
        this.packageType = null;
        this.mealPreference = null;
    }
}
