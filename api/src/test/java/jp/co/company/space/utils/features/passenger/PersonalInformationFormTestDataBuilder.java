package jp.co.company.space.utils.features.passenger;

import jp.co.company.space.api.features.passenger.exception.PassengerException;
import jp.co.company.space.api.features.passenger.exception.PersonalInformationError;
import jp.co.company.space.api.features.passenger.exception.PersonalInformationException;
import jp.co.company.space.api.features.passenger.input.PersonalInformationCreationForm;
import jp.co.company.space.api.shared.exception.DomainException;
import jp.co.company.space.api.shared.openApi.Examples;

import java.time.LocalDate;

/**
 * A test data builder that creates a {@link PersonalInformationCreationForm} instance for testing purposes.
 */
public class PersonalInformationFormTestDataBuilder {

    private static final String DEFAULT_LAST_NAME = Examples.USER_LAST_NAME_EXAMPLE;
    private static final String DEFAULT_MIDDLE_NAME = Examples.PASSENGER_MIDDLE_NAME_EXAMPLE;
    private static final String DEFAULT_FIRST_NAME = Examples.USER_FIRST_NAME_EXAMPLE;
    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.parse(Examples.PASSENGER_BIRTHDATE_EXAMPLE);
    private static final String DEFAULT_NATIONALITY = Examples.NATIONALITY_INPUT_EXAMPLE;
    private static final String DEFAULT_GENDER = Examples.GENDER_EXAMPLE;
    private static final String DEFAULT_PASSPORT_NUMBER = Examples.PASSENGER_PASSPORT_NUMBER_EXAMPLE;

    public PersonalInformationFormTestDataBuilder() {
    }

    /**
     * Creates a new test {@link PersonalInformationCreationForm} instance.
     *
     * @return A {@link PersonalInformationCreationForm} instance.
     */
    public PersonalInformationCreationForm create() throws PassengerException {
        try {
            return new PersonalInformationCreationForm(
                    DEFAULT_LAST_NAME,
                    DEFAULT_MIDDLE_NAME,
                    DEFAULT_FIRST_NAME,
                    DEFAULT_BIRTHDATE,
                    DEFAULT_NATIONALITY,
                    DEFAULT_GENDER,
                    DEFAULT_PASSPORT_NUMBER
            );
        } catch (DomainException exception) {
            throw new PersonalInformationException(PersonalInformationError.CREATE, exception);
        }
    }
}
