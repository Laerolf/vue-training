package jp.co.company.space.api.features.user.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.user.domain.UserCreationFactory;
import jp.co.company.space.api.features.user.exception.UserError;
import jp.co.company.space.api.features.user.exception.UserException;
import jp.co.company.space.api.features.user.input.UserCreationForm;
import jp.co.company.space.api.features.user.repository.UserRepository;
import jp.co.company.space.api.shared.util.LogBuilder;

import java.security.Principal;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A service class handling the {@link User} topic.
 */
@ApplicationScoped
public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    /**
     * The user repository.
     */
    @Inject
    private UserRepository repository;

    protected UserService() {
    }

    /**
     * Gets an {@link Optional} {@link User} for the provided ID.
     *
     * @param userPrincipal The user principal of the session.
     * @return An {@link Optional} {@link User}.
     */
    public Optional<User> findByUserPrincipal(Principal userPrincipal) throws UserException {
        try {
            return repository.findById(userPrincipal.getName());
        } catch (UserException exception) {
            LOGGER.warning(new LogBuilder(UserError.FIND_BY_REQUEST_CONTEXT).withException(exception).build());
            throw exception;
        }
    }

    /**
     * Gets an {@link Optional} {@link User} for the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link User}.
     */
    public Optional<User> findById(String id) throws UserException {
        try {
            return repository.findById(id);
        } catch (UserException exception) {
            LOGGER.warning(new LogBuilder(UserError.FIND_BY_ID).withException(exception).withProperty("id", id).build());
            throw exception;
        }
    }

    /**
     * Gets an {@link Optional} {@link User} for the provided email address.
     *
     * @param emailAddress The email address to search with.
     * @return An {@link Optional} {@link User}.
     */
    public Optional<User> findByEmailAddress(String emailAddress) throws UserException {
        try {
            return repository.findByEmailAddress(emailAddress);
        } catch (UserException exception) {
            LOGGER.warning(new LogBuilder(UserError.FIND_BY_EMAIL_ADDRESS).withException(exception).withProperty("emailAddress", emailAddress).build());
            throw exception;
        }
    }

    /**
     * Returns a new {@link User} based on a {@link UserCreationForm}.
     *
     * @param creationForm The base of the user.
     * @return A new {@link User}.
     */
    public User create(UserCreationForm creationForm) throws UserException {
        try {
            User newUser = new UserCreationFactory(creationForm).create();
            newUser = repository.save(newUser);

            LOGGER.info(new LogBuilder("Created a new user").withProperty("user.id", newUser.getId()).build());
            return newUser;
        } catch (UserException exception) {
            LOGGER.warning(new LogBuilder(UserError.CREATE).withException(exception).build());
            throw exception;
        }
    }
}
