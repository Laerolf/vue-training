package jp.co.nova.gate.api.features.passenger.validation;

import java.util.regex.Pattern;

/**
 * A validator for the format of a passport number.
 */
public class PassportNumberValidator {

    private static final Pattern PASSPORT_NUMBER_PATTERN = Pattern.compile("^[A-Z0-9]{6,9}$");

    public static boolean isValid(String passportNumber) {
        return PASSPORT_NUMBER_PATTERN.matcher(passportNumber.trim().toUpperCase()).matches();
    }
}
