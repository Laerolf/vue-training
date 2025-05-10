package jp.co.company.space.api.features.spaceShuttle.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttle.exception.SpaceShuttleError;
import jp.co.company.space.api.features.spaceShuttle.exception.SpaceShuttleException;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;

import java.util.List;
import java.util.Optional;

/**
 * The class for {@link SpaceShuttle} DB actions.
 */
@ApplicationScoped
public class SpaceShuttleRepository {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected SpaceShuttleRepository() {
    }

    /**
     * Searches an {@link Optional} instance of the {@link SpaceShuttle} class by its ID.
     *
     * @param id The ID of the space shuttle to search for.
     * @return An {@link Optional} {@link SpaceShuttle}.
     */
    public Optional<SpaceShuttle> findById(String id) throws SpaceShuttleException {
        try {
            return Optional.ofNullable(entityManager.find(SpaceShuttle.class, id));
        } catch (IllegalArgumentException exception) {
            throw new SpaceShuttleException(SpaceShuttleError.FIND_BY_ID, exception);
        }
    }

    /**
     * Gets all the saved {@link SpaceShuttle} instances.
     *
     * @return A {@link List} of {@link SpaceShuttle} instances.
     */
    public List<SpaceShuttle> getAll() throws SpaceShuttleException {
        try {
            return entityManager.createNamedQuery("SpaceShuttle.selectAll", SpaceShuttle.class).getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException |
                 NullPointerException exception) {
            throw new SpaceShuttleException(SpaceShuttleError.GET_ALL, exception);
        }
    }

    /**
     * Returns a {@link List} of {@link SpaceShuttle} instances matching the space shuttle model with the provided ID.
     *
     * @param id The ID to search with.
     * @return A {@link List} of {@link SpaceShuttle} instances.
     */
    public List<SpaceShuttle> getAllSpaceShuttlesByModelId(String id) throws SpaceShuttleException {
        try {
            return entityManager.createNamedQuery("SpaceShuttle.selectAllByModelId", SpaceShuttle.class).setParameter("id", id).getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException |
                 NullPointerException exception) {
            throw new SpaceShuttleException(SpaceShuttleError.GET_ALL_BY_MODEL_ID, exception);
        }
    }

    /**
     * Saves a {@link SpaceStation} instance.
     *
     * @param spaceShuttle The {@link SpaceStation} instance to save.
     * @return The saved {@link SpaceStation} instance.
     */
    public SpaceShuttle save(SpaceShuttle spaceShuttle) throws SpaceShuttleException {
        if (findById(spaceShuttle.getId()).isEmpty()) {
            try {
                entityManager.persist(spaceShuttle);
                return spaceShuttle;
            } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException exception) {
                throw new SpaceShuttleException(SpaceShuttleError.SAVE, exception);
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
    public SpaceShuttle merge(SpaceShuttle spaceShuttle) throws SpaceShuttleException {
        try {
            return entityManager.merge(spaceShuttle);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new SpaceShuttleException(SpaceShuttleError.MERGE, exception);
        }
    }

    /**
     * Saves a {@link List} of {@link SpaceShuttle} instances.
     *
     * @param spaceShuttles The {@link List} of {@link SpaceShuttle} to save.
     * @return The {@link List} of saved {@link SpaceShuttle} instances.
     */
    public List<SpaceShuttle> save(List<SpaceShuttle> spaceShuttles) throws SpaceShuttleException {
        try {
            return spaceShuttles.stream().map(this::save).toList();
        } catch (IllegalArgumentException exception) {
            throw new SpaceShuttleException(SpaceShuttleError.SAVE_LIST, exception);
        }
    }
}
