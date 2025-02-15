package jp.co.company.space.api.features.user.repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TransactionRequiredException;
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.shared.PersistenceRepository;
import jp.co.company.space.shared.QueryRepository;

/**
 * The class for {@link User} DB actions.
 */
@ApplicationScoped
public class UserRepository implements QueryRepository<User>, PersistenceRepository<User> {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected UserRepository() {}

    /**
     * Searches an {@link Optional} instance of the {@link User} class by its ID.
     * 
     * @param id The ID of the user to search for.
     * @return An {@link Optional} {@link User}.
     */
    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> getAll() {
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    /**
     * Saves a {@link User} instance.
     * 
     * @param user The {@link User} instance to save.
     * @return The saved {@link User} instance.
     */
    @Override
    @Transactional
    public User save(User user) {
        if (!findById(user.getId()).isPresent()) {
            try {
                entityManager.persist(user);
                return findById(user.getId()).orElseThrow();
            } catch (TransactionRequiredException | EntityExistsException | NoSuchElementException
                    | IllegalArgumentException exception) {
                throw new IllegalArgumentException("Failed to save a user instance.", exception);
            }
        } else {
            return merge(user);
        }
    }

    /**
     * Merges a persisted {@link User} instance with the provided instance.
     * 
     * @param user The {@link User} instance to merge.
     * @return The merged {@link User} instance.
     */
    @Override
    @Transactional
    public User merge(User user) {
        try {
            return entityManager.merge(user);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to merge a user instance.", exception);
        }
    }

    @Override
    @Transactional
    public List<User> save(List<User> users) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
}
