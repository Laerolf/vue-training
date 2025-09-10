package jp.co.nova.gate.api.features.location.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jp.co.nova.gate.api.features.location.domain.Location;
import jp.co.nova.gate.api.features.location.exception.LocationError;
import jp.co.nova.gate.api.features.location.exception.LocationException;

import java.util.List;
import java.util.Optional;

/**
 * The class for {@link Location} DB actions.
 */
@ApplicationScoped
public class LocationRepository {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected LocationRepository() {
    }

    /**
     * Searches an {@link Optional} {@link Location} with the provided ID.
     *
     * @param id The ID of the location to search for.
     * @return An {@link Optional} {@link Location}.
     */
    public Optional<Location> findById(String id) throws LocationException {
        try {
            return entityManager
                    .createNamedQuery("Location.selectById", Location.class)
                    .setParameter("id", id)
                    .getResultStream()
                    .findFirst();
        } catch (IllegalArgumentException | PersistenceException | NullPointerException exception) {
            throw new LocationException(LocationError.FIND_BY_ID, exception);
        }
    }

    /**
     * Gets all the saved {@link Location}s.
     *
     * @return A {@link List} of {@link Location}s.
     */
    public List<Location> getAll() throws LocationException {
        try {
            return entityManager.createNamedQuery("Location.selectAll", Location.class).getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException |
                 NullPointerException exception) {
            throw new LocationException(LocationError.GET_ALL, exception);
        }
    }

    /**
     * Saves a {@link Location}.
     *
     * @param location The {@link Location} to save.
     * @return The saved {@link Location}.
     */
    public Location save(Location location) throws LocationException {
        if (findById(location.getId()).isEmpty()) {
            try {
                entityManager.persist(location);
                return location;
            } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException exception) {
                throw new LocationException(LocationError.SAVE, exception);
            }
        } else {
            return merge(location);
        }
    }

    /**
     * Merges a persisted {@link Location}.
     *
     * @param location The {@link Location} to merge.
     * @return The merged {@link Location}.
     */
    public Location merge(Location location) throws LocationException {
        try {
            return entityManager.merge(location);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new LocationException(LocationError.MERGE, exception);
        }
    }

    /**
     * Saves a {@link List} of {@link Location}s.
     *
     * @param locations The {@link List} of {@link Location} to save.
     * @return The {@link List} of saved {@link Location}s.
     */
    public List<Location> save(List<Location> locations) throws LocationException {
        try {
            return locations.stream().map(this::save).toList();
        } catch (IllegalArgumentException exception) {
            throw new LocationException(LocationError.SAVE_LIST, exception);
        }
    }
}
