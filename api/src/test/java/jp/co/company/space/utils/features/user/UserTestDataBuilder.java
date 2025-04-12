package jp.co.company.space.utils.features.user;

import jp.co.company.space.api.features.user.domain.User;

import java.util.Optional;

/**
 * A utility class that creates {@link User} instances for tests.
 */
public class UserTestDataBuilder {

    private static final String DEFAULT_LAST_NAME = "Jekyll";
    private static final String DEFAULT_FIRST_NAME = "Henry";
    private static final String DEFAULT_EMAIL_ADDRESS = "henry.jekyll@example.com";
    private static final String DEFAULT_PASSWORD = "******";

    private String lastName;
    private String firstName;
    private String emailAddress;
    private String password;

    public UserTestDataBuilder() {}

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

    public User create() {
        User user = User.create(Optional.ofNullable(lastName).orElse(DEFAULT_LAST_NAME),
                Optional.ofNullable(firstName).orElse(DEFAULT_FIRST_NAME),
                Optional.ofNullable(emailAddress).orElse(DEFAULT_EMAIL_ADDRESS),
                Optional.ofNullable(password).orElse(DEFAULT_PASSWORD));

        cleanUp();
        return user;
    }

    private void cleanUp() {
        lastName = null;
        firstName = null;
        emailAddress = null;
        password = null;
    }
}
