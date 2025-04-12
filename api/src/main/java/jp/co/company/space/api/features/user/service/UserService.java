package jp.co.company.space.api.features.user.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jp.co.company.space.api.features.user.domain.User;
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
    private UserRepository repository;

    protected UserService() {}

    /**
     * Gets an {@link Optional} {@link User} instance for the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link User} instance.
     */
    public Optional<User> findById(String id) {
        return repository.findById(id);
    }
}
