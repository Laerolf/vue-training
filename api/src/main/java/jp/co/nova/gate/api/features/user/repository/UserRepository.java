package jp.co.nova.gate.api.features.user.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jp.co.nova.gate.api.features.user.domain.User;
import jp.co.nova.gate.api.features.user.exception.UserError;
import jp.co.nova.gate.api.features.user.exception.UserException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

/**
 * The class for {@link User} DB actions.
 */
@ApplicationScoped
public class UserRepository {

    // TODO: add a way to remove existing users

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected UserRepository() {
    }

    /**
     * Searches a {@link User} by its ID.
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
     * Searches a {@link User} by its email address.
     *
     * @param emailAddress The email address of the user to search for.
     * @return An {@link Optional} {@link User}.
     */
    public Optional<User> findByEmailAddress(String emailAddress) throws UserException {
        try {
            return entityManager.createNamedQuery("User.selectByEmailAddress", User.class).setParameter("emailAddress", emailAddress).getResultStream().findFirst();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException |
                 NullPointerException exception) {
            throw new UserException(UserError.FIND_BY_EMAIL_ADDRESS, exception);
        }
    }

    /**
     * Saves a {@link User}.
     *
     * @param user The {@link User} to save.
     * @return The saved {@link User}.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public User save(User user) throws UserException {
        if (findById(user.getId()).isEmpty()) {
            try {
                entityManager.persist(user);
                entityManager.flush();
                return user;
            } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException exception) {
                throw new UserException(UserError.SAVE, exception);
            } catch (Exception exception) {
                if (exception.getCause() != null && exception.getCause() instanceof SQLIntegrityConstraintViolationException && exception.getCause().getMessage().contains("email")) {
                    throw new UserException(UserError.EMAIL_ALREADY_IN_USE, exception);
                }

                throw new UserException(UserError.SAVE, exception);
            }
        } else {
            return merge(user);
        }
    }

    /**
     * Merges a persisted {@link User}.
     *
     * @param user The {@link User} to merge.
     * @return The merged {@link User}.
     */
    public User merge(User user) throws UserException {
        try {
            return entityManager.merge(user);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new UserException(UserError.MERGE, exception);
        }
    }

    /**
     * Persists a {@link List} of {@link User}s.
     *
     * @param users The {@link List} of {@link User}s to save.
     * @return A {@link List} of persisted {@link User}s
     */
    public List<User> save(List<User> users) throws UserException {
        try {
            return users.stream().map(this::save).toList();
        } catch (UserException exception) {
            throw new UserException(UserError.SAVE_LIST, exception);
        }
    }
}
