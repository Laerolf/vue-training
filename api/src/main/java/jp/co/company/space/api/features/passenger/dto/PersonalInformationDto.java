package jp.co.company.space.api.features.passenger.dto;

import jp.co.company.space.api.features.passenger.domain.PersonalInformation;
import jp.co.company.space.api.features.passenger.exception.PersonalInformationError;
import jp.co.company.space.api.features.passenger.exception.PersonalInformationException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

import static jp.co.company.space.api.shared.openApi.Examples.*;

/**
 * A POJO representing a DTO of {@link PersonalInformation}.
 */
@Schema(name = "PersonalInformation", description = "The personal information of a passenger.")
public class PersonalInformationDto {

    /**
     * Creates {@link PersonalInformationDto} based on a {@link PersonalInformation} instance.
     *
     * @param personalInformation The base for the personal information DTO.
     * @return A {@link PersonalInformationDto} instance.
     */
    public static PersonalInformationDto create(PersonalInformation personalInformation) throws PersonalInformationException {
        if (personalInformation == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING);
        }

        return new PersonalInformationDto(
                personalInformation.getLastName(),
                personalInformation.getMiddleName(),
                personalInformation.getFirstName(),
                personalInformation.getBirthdate(),
                personalInformation.getNationality().getLabel(),
                personalInformation.getGender().getKey(),
                personalInformation.getPassportNumber()
        );
    }

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
    @Schema(description = "The nationality of the passenger.", example = NATIONALITY_EXAMPLE)
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

    protected PersonalInformationDto() {
    }

    protected PersonalInformationDto(String lastName, String middleName, String firstName, LocalDate birthdate, String nationality, String gender, String passportNumber) {
        if (lastName == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_LAST_NAME);
        } else if (middleName == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_MIDDLE_NAME);
        } else if (firstName == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_FIRST_NAME);
        } else if (birthdate == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_BIRTHDATE);
        } else if (nationality == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_NATIONALITY);
        } else if (gender == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_GENDER);
        } else if (passportNumber == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_PASSPORT_NUMBER);
        }

        this.lastName = lastName;
        this.middleName = middleName;
        this.firstName = firstName;
        this.birthdate = birthdate;
        this.nationality = nationality;
        this.gender = gender;
        this.passportNumber = passportNumber;
    }
}
