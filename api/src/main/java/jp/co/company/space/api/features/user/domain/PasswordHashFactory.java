package jp.co.company.space.api.features.user.domain;

import org.mindrot.jbcrypt.BCrypt;

/**
 * A factory for hashing a password.
 */
public class PasswordHashFactory {

    private final String salt = BCrypt.gensalt();

    private final String rawPassword;

    public PasswordHashFactory(String rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("The password is missing.");
        }

        this.rawPassword = rawPassword;
    }

    /**
     * Hashes a password with salt.
     *
     * @return A hashed password.
     */
    public String hash() {
        return BCrypt.hashpw(rawPassword, salt);
    }
}
