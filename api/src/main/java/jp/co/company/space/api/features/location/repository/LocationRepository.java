package jp.co.company.space.api.features.location.repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TransactionRequiredException;
import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.shared.PersistenceRepository;
import jp.co.company.space.shared.QueryRepository;

/**
 * The class for {@link Location} DB actions.
 */
@ApplicationScoped
public class LocationRepository implements QueryRepository<Location>, PersistenceRepository<Location> {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected LocationRepository() {}

    /**
     * Searches an {@link Optional} instance of the {@link Location} class by its ID.
     * 
     * @param id The ID of the location to search for.
     * @return An {@link Optional} {@link Location}.
     */
    @Override
    public Optional<Location> findById(String id) {
        return Optional.ofNullable(entityManager.find(Location.class, id));
    }

    /**
     * Gets all the saved {@link Location} instances.
     * 
     * @return A {@link List} of {@link Location} instances.
     */
    @Override
    public List<Location> getAll() {
        return entityManager.createNamedQuery("Location.getAll", Location.class).getResultList();
    }

    /**
     * Saves a {@link Location} instance.
     * 
     * @param location The {@link Location} instance to save.
     * @return The saved {@link Location} instance.
     */
    @Override
    public Location save(Location location) {
        if (findById(location.getId()).isEmpty()) {
            try {
                entityManager.persist(location);
                return findById(location.getId()).orElseThrow();
            } catch (TransactionRequiredException | EntityExistsException | NoSuchElementException
                    | IllegalArgumentException exception) {
                throw new IllegalArgumentException("Failed to save a location instance.", exception);
            }
        } else {
            return merge(location);
        }
    }

    /**
     * Merges a persisted {@link Location} instance with the provided instance.
     * 
     * @param location The {@link Location} instance to merge.
     * @return The merged {@link Location} instance.
     */
    @Override
    public Location merge(Location location) {
        try {
            return entityManager.merge(location);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to merge a location instance.", exception);
        }
    }

    /**
     * Saves a {@link List} of {@link Location} instances.
     * 
     * @param locations The {@link List} of {@link Location} to save.
     * @return The {@link List} of saved {@link Location} instances.
     */
    @Override
    public List<Location> save(List<Location> locations) {
        try {
            return locations.stream().map(this::save).toList();
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to save a list of locations.", exception);
        }
    }
}
