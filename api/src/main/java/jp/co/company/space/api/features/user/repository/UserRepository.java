package jp.co.company.space.api.features.user.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TransactionRequiredException;
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.shared.interfaces.PersistenceRepository;
import jp.co.company.space.api.shared.interfaces.QueryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * The class for {@link User} DB actions.
 */
@ApplicationScoped
public class UserRepository implements QueryRepository<User>, PersistenceRepository<User> {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected UserRepository() {
    }

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
        return entityManager.createNamedQuery("User.selectAll", User.class).getResultList();
    }

    /**
     * Saves a {@link User} instance.
     *
     * @param user The {@link User} instance to save.
     * @return The saved {@link User} instance.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public User save(User user) {
        if (findById(user.getId()).isEmpty()) {
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
    public User merge(User user) {
        try {
            return entityManager.merge(user);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to merge a user instance.", exception);
        }
    }

    /**
     * Persists a {@link List} of {@link User} instances.
     *
     * @param users The {@link List} of {@link User} instances to save.
     * @return A {@link List} of persisted {@link User} instances
     */
    @Override
    public List<User> save(List<User> users) {
        return users.stream().map(this::save).toList();
    }
}
