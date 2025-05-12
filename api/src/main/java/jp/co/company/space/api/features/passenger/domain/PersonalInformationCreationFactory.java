package jp.co.company.space.api.features.passenger.domain;

import jp.co.company.space.api.features.catalog.domain.Gender;
import jp.co.company.space.api.features.catalog.domain.Nationality;
import jp.co.company.space.api.features.passenger.exception.PersonalInformationError;
import jp.co.company.space.api.features.passenger.exception.PersonalInformationException;
import jp.co.company.space.api.features.passenger.input.PersonalInformationCreationForm;

/**
 * A POJO representing a factory creating {@link PersonalInformation} instances.
 */
public class PersonalInformationCreationFactory {

    private final Passenger passenger;
    private final PersonalInformationCreationForm creationForm;

    public PersonalInformationCreationFactory(Passenger passenger, PersonalInformationCreationForm creationForm) throws PersonalInformationException {
        if (passenger == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_PASSENGER);
        } else if (creationForm == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_CREATION_FORM);
        }

        this.passenger = passenger;
        this.creationForm = creationForm;
    }

    /**
     * Creates a new {@link PersonalInformation} instance.
     *
     * @return A {@link PersonalInformation} instance.
     */
    public PersonalInformation create() throws PersonalInformationException {
        return PersonalInformation.create(
                creationForm.lastName,
                creationForm.middleName,
                creationForm.firstName,
                creationForm.birthdate,
                Nationality.create(creationForm.nationality),
                Gender.findByKey(creationForm.gender).orElseThrow(() -> new PersonalInformationException(PersonalInformationError.MISSING_GENDER)),
                creationForm.passportNumber,
                passenger
        );
    }
}
