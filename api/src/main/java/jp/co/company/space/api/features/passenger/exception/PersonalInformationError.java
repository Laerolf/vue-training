package jp.co.company.space.api.features.passenger.exception;

import jp.co.company.space.api.shared.exception.DomainError;

/**
 * An enum with passenger error messages.
 */
public enum PersonalInformationError implements DomainError {
    MISSING("personalInformation.missing", "The personal information of a passenger is missing."),
    MISSING_ID("personalInformation.missingId", "The ID of personal information of a passenger is missing."),
    MISSING_LAST_NAME("personalInformation.missingLastName", "The last name of personal information of a passenger is missing."),
    MISSING_MIDDLE_NAME("personalInformation.missingMiddleName", "The middle name of personal information of a passenger is missing."),
    MISSING_FIRST_NAME("personalInformation.missingFirstName", "The first name of personal information of a passenger is missing."),
    MISSING_BIRTHDATE("personalInformation.missingBirthdate", "The birthdate of personal information of a passenger is missing."),
    MISSING_NATIONALITY("personalInformation.missingNationality", "The nationality of personal information of a passenger is missing."),
    MISSING_GENDER("personalInformation.missingGender", "The gender of personal information of a passenger is missing."),
    MISSING_PASSPORT_NUMBER("personalInformation.missingPassportNumber", "The passport number of personal information of a passenger is missing."),
    MISSING_PASSENGER("personalInformation.missingPassenger", "The passenger of personal information is missing."),

    MISSING_CREATION_FORM("personalInformation.missingCreationForm", "The personal information of a new passenger is missing."),

    INVALID_NATIONALITY("personalInformation.invalidNationality", "The nationality of personal information of a passenger is invalid."),
    INVALID_PASSPORT_NUMBER("personalInformation.invalidPassportNumber", "The passport number of personal information of a passenger is invalid."),

    FIND_BY_ID("personalInformation.findById", "Failed to find personal information with the provided ID."),
    SAVE("personalInformation.save", "Failed to save personal information."),
    MERGE("personalInformation.merge", "Failed to merge personal information."),
    CREATE("personalInformation.create", "Failed to create personal information of a passenger.");

    private final String key;
    private final String description;

    PersonalInformationError(String key, String description) {
        this.key = key;
        this.description = description;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
