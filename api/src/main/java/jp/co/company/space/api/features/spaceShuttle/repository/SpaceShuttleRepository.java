package jp.co.company.space.api.features.spaceShuttle.repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TransactionRequiredException;
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;

/**
 * The class for {@link SpaceShuttle} DB actions.
 */
@ApplicationScoped
public class SpaceShuttleRepository {

    /**
     * The space shuttles entity manager.
     */
    @PersistenceContext(unitName = "spaceShuttles")
    private EntityManager entityManager;

    protected SpaceShuttleRepository() {}

    /**
     * Searches an {@link Optional} instance of the {@link SpaceShuttle} class by its ID.
     * 
     * @param id The ID of the space shuttle to search for.
     * @return An {@link Optional} {@link SpaceShuttle}.
     */
    public Optional<SpaceShuttle> findById(String id) {
        return Optional.ofNullable(entityManager.find(SpaceShuttle.class, id));
    }

    /**
     * Gets all the saved {@link SpaceShuttle} instances.
     * 
     * @return A {@link List} of {@link SpaceShuttle} instances.
     */
    public List<SpaceShuttle> getAll() {
        return entityManager.createNamedQuery("selectAllSpaceShuttles", SpaceShuttle.class).getResultList();
    }

    /**
     * Saves a {@link SpaceStation} instance.
     * 
     * @param spaceShuttle The {@link SpaceStation} instance to save.
     * @return The saved {@link SpaceStation} instance.
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public SpaceShuttle save(SpaceShuttle spaceShuttle) {
        if (!findById(spaceShuttle.getId()).isPresent()) {
            try {
                entityManager.persist(spaceShuttle);
                return findById(spaceShuttle.getId()).orElseThrow();
            } catch (TransactionRequiredException | EntityExistsException | NoSuchElementException
                    | IllegalArgumentException exception) {
                throw new IllegalArgumentException("Failed to save a space shuttle instance.", exception);
            }
        } else {
            return merge(spaceShuttle);
        }
    }

    /**
     * Merges a persisted {@link SpaceShuttle} instance with the provided instance.
     * 
     * @param spaceShuttle The {@link SpaceShuttle} instance to merge.
     * @return The merged {@link SpaceShuttle} instance.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public SpaceShuttle merge(SpaceShuttle spaceShuttle) {
        try {
            return entityManager.merge(spaceShuttle);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to merge a space shuttle instance.", exception);
        }
    }

    /**
     * Saves a {@link List} of {@link SpaceShuttle} instances.
     * 
     * @param spaceShuttles The {@link List} of {@link SpaceShuttle} to save.
     * @return The {@link List} of saved {@link SpaceShuttle} instances.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public List<SpaceShuttle> save(List<SpaceShuttle> spaceShuttles) {
        try {
            return spaceShuttles.stream().map(this::save).toList();
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to save a list of space shuttles.", exception);
        }
    }
}
