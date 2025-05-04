package jp.co.company.space.api.features.authentication.domain;

import org.mindrot.jbcrypt.BCrypt;

/**
 * A factory for validating a password.
 */
public class PasswordValidationFactory {

    private final String rawPassword;
    private final String expectedPassword;

    public PasswordValidationFactory(String rawPassword, String expectedPassword) {
        this.rawPassword = rawPassword;
        this.expectedPassword = expectedPassword;
    }

    /**
     * Tests whether a provided password matches with the excepted password.
     *
     * @return True when the passwords match, false otherwise.
     */
    public boolean validate() throws IllegalArgumentException {
        if (rawPassword == null) {
            throw new IllegalArgumentException("The provided password is missing.");
        } else if (expectedPassword == null) {
            throw new IllegalArgumentException("The expected password is missing.");
        }

        return BCrypt.checkpw(rawPassword, expectedPassword);
    }
}
