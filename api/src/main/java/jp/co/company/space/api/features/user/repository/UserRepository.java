package jp.co.company.space.api.features.user.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.user.exception.UserError;
import jp.co.company.space.api.features.user.exception.UserException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * The class for {@link User} DB actions.
 */
@ApplicationScoped
public class UserRepository {

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
    public Optional<User> findById(String id) throws UserException {
        try {
            return entityManager.createNamedQuery("User.selectById", User.class).setParameter("id", id).getResultStream().findFirst();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException |
                 NullPointerException exception) {
            throw new UserException(UserError.FIND_BY_ID, exception);
        }
    }

    /**
     * Saves a {@link User} instance.
     *
     * @param user The {@link User} instance to save.
     * @return The saved {@link User} instance.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public User save(User user) throws UserException {
        if (findById(user.getId()).isEmpty()) {
            try {
                entityManager.persist(user);
                return findById(user.getId()).orElseThrow();
            } catch (TransactionRequiredException | EntityExistsException | NoSuchElementException
                     | IllegalArgumentException exception) {
                throw new UserException(UserError.SAVE, exception);
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
    public User merge(User user) throws UserException {
        try {
            return entityManager.merge(user);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new UserException(UserError.MERGE, exception);
        }
    }

    /**
     * Persists a {@link List} of {@link User} instances.
     *
     * @param users The {@link List} of {@link User} instances to save.
     * @return A {@link List} of persisted {@link User} instances
     */
    public List<User> save(List<User> users) throws UserException {
        try {
            return users.stream().map(this::save).toList();
        } catch (UserException exception) {
            throw new UserException(UserError.SAVE_LIST, exception);
        }
    }
}
