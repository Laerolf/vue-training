package jp.co.company.space.api.features.user.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.user.domain.UserCreationFactory;
import jp.co.company.space.api.features.user.input.UserCreationForm;
import jp.co.company.space.api.features.user.repository.UserRepository;

import java.util.Optional;

/**
 * A service class handling the {@link User} topic.
 */
@ApplicationScoped
public class UserService {
    /**
     * The user repository.
     */
    @Inject
    private UserRepository userRepository;

    protected UserService() {
    }

    /**
     * Gets an {@link Optional} {@link User} instance for the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link User} instance.
     */
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    /**
     * Returns a new {@link User} instance based on a {@link UserCreationForm} instance.
     *
     * @param creationForm The base of the user.
     * @return A new {@link User} instance.
     */
    public User create(UserCreationForm creationForm) {
        User newUser = new UserCreationFactory(creationForm).create();
        return userRepository.save(newUser);
    }
}
