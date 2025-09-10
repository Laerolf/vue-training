package jp.co.nova.gate.utils.features.user;

import jp.co.nova.gate.api.features.user.domain.PasswordHashFactory;
import jp.co.nova.gate.api.features.user.domain.User;

import java.util.Optional;

/**
 * A test data builder that creates a {@link User} for testing purposes.
 */
public class UserTestDataBuilder {
    private static final String DEFAULT_ID = "test";
    private static final String DEFAULT_LAST_NAME = "Jekyll";
    private static final String DEFAULT_FIRST_NAME = "Henry";
    private static final String DEFAULT_EMAIL_ADDRESS = "henry.jekyll@example.com";
    private static final String DEFAULT_PASSWORD = "test";

    private String id;
    private String lastName;
    private String firstName;
    private String emailAddress;
    private String password;

    public UserTestDataBuilder() {
    }

    public UserTestDataBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public UserTestDataBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserTestDataBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserTestDataBuilder withEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public UserTestDataBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserTestDataBuilder withHashPassword() {
        password = new PasswordHashFactory(Optional.ofNullable(password).orElse(DEFAULT_PASSWORD)).hash();
        return this;
    }

    public User create() {
        return User.reconstruct(
                Optional.ofNullable(id).orElse(DEFAULT_ID),
                Optional.ofNullable(lastName).orElse(DEFAULT_LAST_NAME),
                Optional.ofNullable(firstName).orElse(DEFAULT_FIRST_NAME),
                Optional.ofNullable(emailAddress).orElse(DEFAULT_EMAIL_ADDRESS),
                Optional.ofNullable(password).orElse(DEFAULT_PASSWORD)
        );
    }
}
