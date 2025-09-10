package jp.co.nova.gate.utils.features.passenger;

import jp.co.nova.gate.api.features.passenger.exception.PassengerException;
import jp.co.nova.gate.api.features.passenger.exception.PersonalInformationError;
import jp.co.nova.gate.api.features.passenger.exception.PersonalInformationException;
import jp.co.nova.gate.api.features.passenger.input.PersonalInformationCreationForm;
import jp.co.nova.gate.api.shared.exception.DomainException;
import jp.co.nova.gate.api.shared.openApi.Examples;

import java.time.LocalDate;

/**
 * A test data builder that creates a {@link PersonalInformationCreationForm} for testing purposes.
 */
public class PersonalInformationFormTestDataBuilder {

    private static final String DEFAULT_LAST_NAME = Examples.USER_LAST_NAME_EXAMPLE;
    private static final String DEFAULT_MIDDLE_NAME = Examples.PASSENGER_MIDDLE_NAME_EXAMPLE;
    private static final String DEFAULT_FIRST_NAME = Examples.USER_FIRST_NAME_EXAMPLE;
    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.parse(Examples.PASSENGER_BIRTHDATE_EXAMPLE);
    private static final String DEFAULT_NATIONALITY = Examples.NATIONALITY_CODE_EXAMPLE;
    private static final String DEFAULT_GENDER = Examples.GENDER_EXAMPLE;
    private static final String DEFAULT_PASSPORT_NUMBER = Examples.PASSENGER_PASSPORT_NUMBER_EXAMPLE;

    public PersonalInformationFormTestDataBuilder() {
    }

    /**
     * Creates a new test {@link PersonalInformationCreationForm}.
     *
     * @return A {@link PersonalInformationCreationForm}.
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
