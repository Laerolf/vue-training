package jp.co.company.space.api.features.passenger.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jp.co.company.space.api.features.passenger.exception.PersonalInformationError;
import jp.co.company.space.api.features.passenger.validation.ValidPassportNumberFormat;

import java.util.regex.Pattern;

/**
 * A {@link ConstraintValidator} for the format of a passport number.
 */
public class PassportNumberFormatValidator implements ConstraintValidator<ValidPassportNumberFormat, String> {

    private static final Pattern PASSPORT_NUMBER_PATTERN = Pattern.compile("^[A-Z0-9]{6,9}$");

    /**
     * Tests whether the provided passport number is valid or not.
     *
     * @param passportNumber The passport number to test.
     * @return True if the passport number is valid, false otherwise.
     */
    public static boolean isValid(String passportNumber) {
        if (passportNumber == null) {
            return false;
        }

        return PASSPORT_NUMBER_PATTERN.matcher(passportNumber.trim().toUpperCase()).matches();
    }

    @Override
    public boolean isValid(String passportNumber, ConstraintValidatorContext context) {
        if (!isValid(passportNumber)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(PersonalInformationError.INVALID_PASSPORT_NUMBER.getKey()).addConstraintViolation();
            return false;
        }

        return true;
    }
}
