package jp.co.nova.gate.utils.features.passenger;

import jp.co.nova.gate.api.features.catalog.domain.MealPreference;
import jp.co.nova.gate.api.features.catalog.domain.PackageType;
import jp.co.nova.gate.api.features.catalog.domain.PodType;
import jp.co.nova.gate.api.features.passenger.exception.PassengerError;
import jp.co.nova.gate.api.features.passenger.exception.PassengerException;
import jp.co.nova.gate.api.features.passenger.input.PassengerCreationForm;
import jp.co.nova.gate.api.features.pod.domain.PodCodeFactory;
import jp.co.nova.gate.api.features.pod.exception.PodError;
import jp.co.nova.gate.api.features.pod.exception.PodException;
import jp.co.nova.gate.api.shared.exception.DomainException;

import java.util.Optional;

/**
 * A test data builder that creates a {@link PassengerCreationForm} for testing purposes.
 */
public class PassengerCreationFormTestDataBuilder {

    private static final PackageType DEFAULT_PACKAGE_TYPE = PackageType.ECONOMY;
    private static final MealPreference DEFAULT_MEAL_PREFERENCE = MealPreference.STANDARD;

    private static final int DEFAULT_POD_DECK_NUMBER = 1;
    private static final int DEFAULT_POD_NUMBER = 1;

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
     * Creates a new test {@link PassengerCreationForm}.
     *
     * @return A {@link PassengerCreationForm}.
     */
    public PassengerCreationForm create() throws PassengerException {
        try {
            PackageType selectedPackageType = Optional.ofNullable(packageType).orElse(DEFAULT_PACKAGE_TYPE);

            PodType selectedPodType = PodType.findByPackageType(selectedPackageType).orElseThrow(() -> new PodException(PodError.MISSING_TYPE));
            String podCode = new PodCodeFactory(selectedPodType.getPodCodePrefix(), DEFAULT_POD_DECK_NUMBER, DEFAULT_POD_NUMBER).create();

            return new PassengerCreationForm(
                    podCode,
                    selectedPackageType.getKey(),
                    Optional.ofNullable(mealPreference).orElse(DEFAULT_MEAL_PREFERENCE).getKey(),
                    new PersonalInformationFormTestDataBuilder().create()
            );
        } catch (DomainException exception) {
            throw new PassengerException(PassengerError.CREATE, exception);
        }
    }
}
