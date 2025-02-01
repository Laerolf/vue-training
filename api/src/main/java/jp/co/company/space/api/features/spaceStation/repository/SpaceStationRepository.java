package jp.co.company.space.api.features.spaceStation.repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TransactionRequiredException;
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;

/**
 * The class for {@link SpaceStation} DB actions.
 */
@ApplicationScoped
public class SpaceStationRepository {

    /**
     * The space stations entity manager.
     */
    @PersistenceContext(unitName = "locations")
    private EntityManager entityManager;

    protected SpaceStationRepository() {}

    /**
     * Searches an {@link Optional} instance of the {@link SpaceStation} class by its ID.
     * 
     * @param id The ID of the space station to search for.
     * @return An {@link Optional} {@link SpaceStation}.
     */
    public Optional<SpaceStation> findById(String id) {
        return Optional.ofNullable(entityManager.find(SpaceStation.class, id));
    }

    /**
     * Gets all the saved {@link SpaceStation} instances.
     * 
     * @return A {@link List} of {@link SpaceStation} instances.
     */
    public List<SpaceStation> getAll() {
        return entityManager.createNamedQuery("selectAllSpaceStations", SpaceStation.class).getResultList();
    }

    /**
     * Saves a {@link SpaceStation} instance.
     * 
     * @param spaceStation The {@link SpaceStation} instance to save.
     * @return The saved {@link SpaceStation} instance.
     */
    @Transactional(Transactional.TxType.SUPPORTS)
    public SpaceStation save(SpaceStation spaceStation) {
        if (!findById(spaceStation.getId()).isPresent()) {
            try {
                entityManager.persist(spaceStation);
                return findById(spaceStation.getId()).orElseThrow();
            } catch (TransactionRequiredException | EntityExistsException | NoSuchElementException
                    | IllegalArgumentException exception) {
                throw new IllegalArgumentException("Failed to save a space station instance.", exception);
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
    @Transactional(Transactional.TxType.REQUIRED)
    public SpaceStation merge(SpaceStation spaceStation) {
        try {
            return entityManager.merge(spaceStation);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to merge a space station instance.", exception);
        }
    }

    /**
     * Saves a {@link List} of {@link SpaceStation} instances.
     * 
     * @param spaceStations The {@link List} of {@link SpaceStation} to save.
     * @return The {@link List} of saved {@link SpaceStation} instances.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public List<SpaceStation> save(List<SpaceStation> spaceStations) {
        try {
            return spaceStations.stream().map(this::save).toList();
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to save a list of space stations.", exception);
        }
    }

}
