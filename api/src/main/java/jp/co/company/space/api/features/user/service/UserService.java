package jp.co.company.space.api.features.user.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.user.repository.UserRepository;

import java.util.List;
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
     * Gets a {@link List} of all existing {@link User} instances.
     *
     * @return A {@link List} of {@link User} instances.
     */
    public List<User> getAll() {
        return userRepository.getAll();
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
}
