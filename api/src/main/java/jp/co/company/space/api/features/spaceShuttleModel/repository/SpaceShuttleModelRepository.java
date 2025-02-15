package jp.co.company.space.api.features.spaceShuttleModel.repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TransactionRequiredException;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.shared.PersistenceRepository;
import jp.co.company.space.shared.QueryRepository;

/**
 * The class for {@link SpaceShuttleModel} DB actions.
 */
@ApplicationScoped
public class SpaceShuttleModelRepository
        implements QueryRepository<SpaceShuttleModel>, PersistenceRepository<SpaceShuttleModel> {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected SpaceShuttleModelRepository() {}

    /**
     * Searches an {@link Optional} instance of the {@link SpaceShuttleModel} class by its ID.
     * 
     * @param id The ID of the space shuttle model to search for.
     * @return An {@link Optional} {@link SpaceShuttleModel}.
     */
    @Override
    public Optional<SpaceShuttleModel> findById(String id) {
        return Optional.ofNullable(entityManager.find(SpaceShuttleModel.class, id));
    }

    /**
     * Gets all the saved {@link SpaceShuttleModel} instances.
     * 
     * @return A {@link List} of {@link SpaceShuttleModel} instances.
     */
    @Override
    public List<SpaceShuttleModel> getAll() {
        return entityManager.createNamedQuery("selectAllSpaceShuttleModels", SpaceShuttleModel.class).getResultList();
    }

    /**
     * Saves a {@link SpaceShuttleModel} instance.
     * 
     * @param spaceShuttleModel The {@link SpaceShuttleModel} instance to save.
     * @return The saved {@link SpaceShuttleModel} instance.
     */
    @Override
    public SpaceShuttleModel save(SpaceShuttleModel spaceShuttleModel) {
        if (!findById(spaceShuttleModel.getId()).isPresent()) {
            try {
                entityManager.persist(spaceShuttleModel);
                return findById(spaceShuttleModel.getId()).orElseThrow();
            } catch (NoSuchElementException | EntityExistsException | TransactionRequiredException
                    | IllegalArgumentException exception) {
                throw new IllegalArgumentException("Failed to save a space shuttle model instance.", exception);
            }
        } else {
            return merge(spaceShuttleModel);
        }
    }

    /**
     * Merges a persisted {@link SpaceShuttleModel} instance with the provided instance.
     * 
     * @param spaceShuttleModel The {@link SpaceShuttleModel} instance to merge.
     * @return The merged {@link SpaceShuttleModel} instance.
     */
    @Override
    public SpaceShuttleModel merge(SpaceShuttleModel spaceShuttleModel) {
        try {
            return entityManager.merge(spaceShuttleModel);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to merge a space shuttle model instance.", exception);
        }
    }

    /**
     * Saves a {@link List} of {@link SpaceShuttleModel} instances.
     * 
     * @param spaceShuttleModels The {@link List} of {@link SpaceShuttleModel} to save.
     * @return The {@link List} of saved {@link SpaceShuttleModel} instances.
     */
    @Override
    public List<SpaceShuttleModel> save(List<SpaceShuttleModel> spaceShuttleModels) {
        try {
            return spaceShuttleModels.stream().map(this::save).toList();
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to save a list of space shuttle models.", exception);
        }
    }
}
