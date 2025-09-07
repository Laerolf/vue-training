package jp.co.company.space.api.features.spaceStation.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import jp.co.company.space.api.features.spaceStation.exception.SpaceStationError;
import jp.co.company.space.api.features.spaceStation.exception.SpaceStationException;

import java.util.List;
import java.util.Optional;

/**
 * The class for {@link SpaceStation} DB actions.
 */
@ApplicationScoped
public class SpaceStationRepository {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected SpaceStationRepository() {
    }

    /**
     * Searches an {@link Optional} instance of the {@link SpaceStation} class by its ID.
     *
     * @param id The ID of the space station to search for.
     * @return An {@link Optional} {@link SpaceStation}.
     */
    public Optional<SpaceStation> findById(String id) throws SpaceStationException {
        try {
            return entityManager.createNamedQuery("SpaceStation.selectById", SpaceStation.class)
                    .setParameter("id", id)
                    .getResultStream()
                    .findFirst();
        } catch (IllegalArgumentException exception) {
            throw new SpaceStationException(SpaceStationError.FIND_BY_ID, exception);
        }
    }

    /**
     * Gets all the saved {@link SpaceStation} instances.
     *
     * @return A {@link List} of {@link SpaceStation} instances.
     */
    public List<SpaceStation> getAll() throws SpaceStationException {
        try {
            return entityManager.createNamedQuery("SpaceStation.selectAll", SpaceStation.class).getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException |
                 NullPointerException exception) {
            throw new SpaceStationException(SpaceStationError.GET_ALL, exception);
        }
    }

    /**
     * Saves a {@link SpaceStation} instance.
     *
     * @param spaceStation The {@link SpaceStation} instance to save.
     * @return The saved {@link SpaceStation} instance.
     */
    public SpaceStation save(SpaceStation spaceStation) throws SpaceStationException {
        if (findById(spaceStation.getId()).isEmpty()) {
            try {
                entityManager.persist(spaceStation);
                return spaceStation;
            } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException exception) {
                throw new SpaceStationException(SpaceStationError.SAVE, exception);
            }
        } else {
            return merge(spaceStation);
        }
    }

    /**
     * Merges a persisted {@link SpaceStation} instance with the provided instance.
     *
     * @param spaceStation The {@link SpaceStation} instance to merge.
     * @return The merged {@link SpaceStation} instance.
     */
    public SpaceStation merge(SpaceStation spaceStation) throws SpaceStationException {
        try {
            return entityManager.merge(spaceStation);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new SpaceStationException(SpaceStationError.MERGE, exception);
        }
    }

    /**
     * Saves a {@link List} of {@link SpaceStation} instances.
     *
     * @param spaceStations The {@link List} of {@link SpaceStation} to save.
     * @return The {@link List} of saved {@link SpaceStation} instances.
     */
    public List<SpaceStation> save(List<SpaceStation> spaceStations) throws SpaceStationException {
        try {
            return spaceStations.stream().map(this::save).toList();
        } catch (IllegalArgumentException exception) {
            throw new SpaceStationException(SpaceStationError.SAVE_LIST, exception);
        }
    }

}
