package jp.co.nova.gate.api.features.passenger.input;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.nova.gate.api.shared.openApi.Examples.*;

/**
 * A DTO representing a form with details for a new passenger.
 */
public class PassengerCreationForm {

    /**
     * The code of the new passenger's pod.
     */
    @Schema(description = "The code of the new passenger's pod.", required = true, example = POD_CODE_EXAMPLE)
    public String podCode;

    /**
     * The package type of the new passenger.
     */
    @Schema(description = "The package type of the new passenger.", required = true, example = PACKAGE_TYPE_KEY_EXAMPLE)
    public String packageType;

    /**
     * The meal preference of the new passenger.
     */
    @Schema(description = "The meal preference of the new passenger.", required = true, example = MEAL_PREFERENCE_KEY_EXAMPLE)
    public String mealPreference;

    /**
     * The personal information of the new passenger.
     */
    @Schema(description = "The personal information of the new passenger.", required = true)
    public PersonalInformationCreationForm personalInformation;

    protected PassengerCreationForm() {
    }

    public PassengerCreationForm(String podCode, String packageType, String mealPreference, PersonalInformationCreationForm personalInformation) {
        this.podCode = podCode;
        this.packageType = packageType;
        this.mealPreference = mealPreference;
        this.personalInformation = personalInformation;
    }
}
