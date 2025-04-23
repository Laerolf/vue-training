package jp.co.company.space.api.features.passenger.input;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.examples.*;

/**
 * A DTO representing a form with details for a new passenger.
 */
public class PassengerCreationForm {

    /**
     * The code of the new passenger's pod.
     */
    @Schema(description = "The code of the new passenger's pod.", example = POD_CODE_EXAMPLE)
    public String podCode;

    /**
     * The package type of the new passenger.
     */
    @Schema(description = "The package type of the new passenger.", example = PACKAGE_TYPE_KEY_EXAMPLE)
    public String packageType;

    /**
     * The meal preference of the new passenger.
     */
    @Schema(description = "The meal preference of the new passenger.", example = MEAL_PREFERENCE_KEY_EXAMPLE)
    public String mealPreference;

    protected PassengerCreationForm() {
    }

    public PassengerCreationForm(String podCode, String packageType, String mealPreference) {
        this.podCode = podCode;
        this.packageType = packageType;
        this.mealPreference = mealPreference;
    }
}
