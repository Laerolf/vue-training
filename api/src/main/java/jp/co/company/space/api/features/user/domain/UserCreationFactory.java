package jp.co.company.space.api.features.user.domain;

import jp.co.company.space.api.features.user.exception.UserError;
import jp.co.company.space.api.features.user.exception.UserException;
import jp.co.company.space.api.features.user.input.UserCreationForm;

/**
 * A factory used to create a new {@link User} instance.
 */
public class UserCreationFactory {

    private final UserCreationForm creationForm;

    public UserCreationFactory(UserCreationForm creationForm) throws IllegalArgumentException {
        if (creationForm == null) {
            throw new IllegalArgumentException("The user creation form is missing.");
        }

        this.creationForm = creationForm;
    }

    /**
     * Returns a new {@link User} instance based on the provided {@link UserCreationForm} instance.
     *
     * @return A new {@link User} instance.
     */
    public User create() throws UserException {
        try {
            String hashedPassword = new PasswordHashFactory(creationForm.password).hash();
            return User.create(creationForm.lastName, creationForm.firstName, creationForm.emailAddress, hashedPassword);
        } catch (IllegalArgumentException exception) {
            throw new UserException(UserError.CREATE);
        }
    }
}
