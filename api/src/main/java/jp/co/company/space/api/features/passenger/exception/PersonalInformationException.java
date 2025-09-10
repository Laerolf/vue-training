package jp.co.company.space.api.features.passenger.exception;

import jp.co.company.space.api.shared.exception.DomainException;

/**
 * A {@link DomainException} for passenger personal information exceptions.
 */
public class PersonalInformationException extends DomainException {
    public PersonalInformationException(PersonalInformationError error) {
        super(error);
    }

    public PersonalInformationException(PersonalInformationError error, Throwable throwable) {
        super(error, throwable);
    }
}
