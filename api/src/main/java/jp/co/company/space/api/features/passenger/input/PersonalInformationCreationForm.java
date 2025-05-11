package jp.co.company.space.api.features.passenger.input;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

import static jp.co.company.space.api.shared.openApi.Examples.*;

/**
 * A DTO representing a form with personal information for a new passenger.
 */
public class PersonalInformationCreationForm {

    /**
     * The last name of the passenger.
     */
    @Schema(description = "The last name of the passenger.", example = USER_LAST_NAME_EXAMPLE)
    public String lastName;

    /**
     * The middle name of the passenger.
     */
    @Schema(description = "The middle name of the passenger.", example = PASSENGER_MIDDLE_NAME_EXAMPLE)
    public String middleName;

    /**
     * The first name of the user.
     */
    @Schema(description = "The first name of the passenger.", example = USER_FIRST_NAME_EXAMPLE)
    public String firstName;

    /**
     * The birthdate of the passenger.
     */
    @Schema(description = "The birthdate of the passenger.", example = PASSENGER_BIRTHDATE_EXAMPLE)
    public LocalDate birthdate;

    /**
     * The nationality of the passenger.
     */
    @Schema(description = "The nationality of the passenger.", example = NATIONALITY_INPUT_EXAMPLE)
    public String nationality;

    /**
     * The gender of the passenger.
     */
    @Schema(description = "The gender of the passenger.", example = GENDER_EXAMPLE)
    public String gender;

    /**
     * The passport number of the passenger.
     */
    @Schema(description = "The passport number of the passenger.", example = PASSENGER_PASSPORT_NUMBER_EXAMPLE)
    public String passportNumber;

    protected PersonalInformationCreationForm() {
    }

    public PersonalInformationCreationForm(String lastName, String middleName, String firstName, LocalDate birthdate, String nationality, String gender, String passportNumber) {
        this.lastName = lastName;
        this.middleName = middleName;
        this.firstName = firstName;
        this.birthdate = birthdate;
        this.nationality = nationality;
        this.gender = gender;
        this.passportNumber = passportNumber;
    }
}
