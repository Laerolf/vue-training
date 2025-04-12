package jp.co.company.space.api.features.spaceShuttle.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TransactionRequiredException;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import jp.co.company.space.api.shared.interfaces.PersistenceRepository;
import jp.co.company.space.api.shared.interfaces.QueryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * The class for {@link SpaceShuttle} DB actions.
 */
@ApplicationScoped
public class SpaceShuttleRepository implements QueryRepository<SpaceShuttle>, PersistenceRepository<SpaceShuttle> {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected SpaceShuttleRepository() {}

    /**
     * Searches an {@link Optional} instance of the {@link SpaceShuttle} class by its ID.
     *
     * @param id The ID of the space shuttle to search for.
     * @return An {@link Optional} {@link SpaceShuttle}.
     */
    @Override
    public Optional<SpaceShuttle> findById(String id) {
        return Optional.ofNullable(entityManager.find(SpaceShuttle.class, id));
    }

    /**
     * Gets all the saved {@link SpaceShuttle} instances.
     *
     * @return A {@link List} of {@link SpaceShuttle} instances.
     */
    @Override
    public List<SpaceShuttle> getAll() {
        return entityManager.createNamedQuery("selectAllSpaceShuttles", SpaceShuttle.class).getResultList();
    }

    /**
     * Saves a {@link SpaceStation} instance.
     *
     * @param spaceShuttle The {@link SpaceStation} instance to save.
     * @return The saved {@link SpaceStation} instance.
     */
    @Override
    public SpaceShuttle save(SpaceShuttle spaceShuttle) {
        if (findById(spaceShuttle.getId()).isEmpty()) {
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
    @Override
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
    @Override
    public List<SpaceShuttle> save(List<SpaceShuttle> spaceShuttles) {
        try {
            return spaceShuttles.stream().map(this::save).toList();
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to save a list of space shuttles.", exception);
        }
    }
}
