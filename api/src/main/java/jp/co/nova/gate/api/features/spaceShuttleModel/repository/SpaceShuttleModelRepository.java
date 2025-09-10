package jp.co.nova.gate.api.features.spaceShuttleModel.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jp.co.nova.gate.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.nova.gate.api.features.spaceShuttleModel.exception.SpaceShuttleModelError;
import jp.co.nova.gate.api.features.spaceShuttleModel.exception.SpaceShuttleModelException;

import java.util.List;
import java.util.Optional;

/**
 * The class for {@link SpaceShuttleModel} DB actions.
 */
@ApplicationScoped
public class SpaceShuttleModelRepository {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected SpaceShuttleModelRepository() {
    }

    /**
     * Searches an {@link Optional} of the {@link SpaceShuttleModel} class by its ID.
     *
     * @param id The ID of the space shuttle model to search for.
     * @return An {@link Optional} {@link SpaceShuttleModel}.
     */
    public Optional<SpaceShuttleModel> findById(String id) throws SpaceShuttleModelException {
        try {
            return Optional.ofNullable(entityManager.find(SpaceShuttleModel.class, id));
        } catch (IllegalArgumentException exception) {
            throw new SpaceShuttleModelException(SpaceShuttleModelError.FIND_BY_ID, exception);
        }
    }

    /**
     * Gets all the saved {@link SpaceShuttleModel}s.
     *
     * @return A {@link List} of {@link SpaceShuttleModel}s.
     */
    public List<SpaceShuttleModel> getAll() throws SpaceShuttleModelException {
        try {
            return entityManager.createNamedQuery("SpaceShuttleModel.selectAll", SpaceShuttleModel.class).getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException |
                 NullPointerException exception) {
            throw new SpaceShuttleModelException(SpaceShuttleModelError.GET_ALL, exception);
        }
    }

    /**
     * Saves a {@link SpaceShuttleModel}.
     *
     * @param spaceShuttleModel The {@link SpaceShuttleModel} to save.
     * @return The saved {@link SpaceShuttleModel}.
     */
    public SpaceShuttleModel save(SpaceShuttleModel spaceShuttleModel) throws SpaceShuttleModelException {
        if (findById(spaceShuttleModel.getId()).isEmpty()) {
            try {
                entityManager.persist(spaceShuttleModel);
                return spaceShuttleModel;
            } catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException exception) {
                throw new SpaceShuttleModelException(SpaceShuttleModelError.SAVE, exception);
            }
        } else {
            return merge(spaceShuttleModel);
        }
    }

    /**
     * Merges a persisted {@link SpaceShuttleModel}.
     *
     * @param spaceShuttleModel The {@link SpaceShuttleModel} to merge.
     * @return The merged {@link SpaceShuttleModel}.
     */
    public SpaceShuttleModel merge(SpaceShuttleModel spaceShuttleModel) throws SpaceShuttleModelException {
        try {
            return entityManager.merge(spaceShuttleModel);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new SpaceShuttleModelException(SpaceShuttleModelError.MERGE, exception);
        }
    }

    /**
     * Saves a {@link List} of {@link SpaceShuttleModel}s.
     *
     * @param spaceShuttleModels The {@link List} of {@link SpaceShuttleModel} to save.
     * @return The {@link List} of saved {@link SpaceShuttleModel}s.
     */
    public List<SpaceShuttleModel> save(List<SpaceShuttleModel> spaceShuttleModels) throws SpaceShuttleModelException {
        try {
            return spaceShuttleModels.stream().map(this::save).toList();
        } catch (SpaceShuttleModelException exception) {
            throw new SpaceShuttleModelException(SpaceShuttleModelError.SAVE_LIST, exception);
        }
    }
}
