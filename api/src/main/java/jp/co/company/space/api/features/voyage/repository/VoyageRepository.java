package jp.co.company.space.api.features.voyage.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TransactionRequiredException;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.shared.interfaces.PersistenceRepository;
import jp.co.company.space.api.shared.interfaces.QueryRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * The class for {@link Voyage} DB actions.
 */
@ApplicationScoped
public class VoyageRepository implements QueryRepository<Voyage>, PersistenceRepository<Voyage> {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected VoyageRepository() {}

    /**
     * Returns an {@link Optional} {@link Voyage} instance for the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link Voyage} instance.
     */
    @Override
    public Optional<Voyage> findById(String id) {
        return Optional.ofNullable(entityManager.find(Voyage.class, id));
    }

    /**
     * Returns a {@link List} of all existing {@link Voyage} instances.
     *
     * @return A {@link List} of {@link Voyage} instances.
     */
    @Override
    public List<Voyage> getAll() {
        return entityManager.createNamedQuery("Voyage.getAll", Voyage.class).getResultList();
    }

    /**
     * Returns a {@link List} of {@link Voyage} instances having an origin space station matching the provided space station
     * ID.
     *
     * @param originId The space station ID to search with.
     * @return A {@link List} of {@link Voyage} instances.
     */
    public List<Voyage> getAllVoyagesFromOriginId(String originId) {
        return entityManager.createNamedQuery("Voyage.getAllFromOriginId", Voyage.class).setParameter("originId", originId).getResultList();
    }

    /**
     * Returns a {@link List} of {@link Voyage} instances having an destination space station matching the provided space
     * station ID.
     *
     * @param destinationId The space station ID to search with.
     * @return A {@link List} of {@link Voyage} instances.
     */
    public List<Voyage> getAllVoyagesToDestinationId(String destinationId) {
        return entityManager.createNamedQuery("Voyage.getAllToDestinationId", Voyage.class).setParameter("destinationId", destinationId).getResultList();
    }

    /**
     * Returns a {@link List} of {@link Voyage} instances having an origin space station matching the provided origin space
     * station ID and a destination space station matching the provided destination space station ID.
     *
     * @param originId      The origin space station ID to search with.
     * @param destinationId The destination space station ID to search with.
     * @return A {@link List} of {@link Voyage} instances.
     */
    public List<Voyage> getAllVoyagesFromOriginIdToDestinationId(String originId, String destinationId) {
        return entityManager.createNamedQuery("Voyage.getAllFromOriginIdToDestinationId", Voyage.class).setParameter("originId", originId).setParameter("destinationId", destinationId).getResultList();
    }

    /**
     * Persists a {@link Voyage} instance.
     *
     * @param voyage The voyage instance to persist.
     * @return A persisted {@link Voyage} instance.
     */
    @Override
    public Voyage save(Voyage voyage) {
        if (findById(voyage.getId()).isEmpty()) {
            try {
                entityManager.persist(voyage);
                return findById(voyage.getId()).orElseThrow();
            } catch (TransactionRequiredException | EntityExistsException | NoSuchElementException | IllegalArgumentException exception) {
                throw new IllegalArgumentException("Failed to save a voyage instance.", exception);
            }
        } else {
            return merge(voyage);
        }
    }

    /**
     * Merges a {@link Voyage} instance.
     *
     * @param voyage The voyage instance to merge.
     * @return A merged {@link Voyage} instance.
     */
    @Override
    public Voyage merge(Voyage voyage) {
        try {
            return entityManager.merge(voyage);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to merge a voyage instance.", exception);
        }
    }

    /**
     * Persists a {@link List} of {@link Voyage} instance.
     *
     * @param voyages The list of voyage instances to persist.
     * @return A persisted {@link List} of {@link Voyage} instances.
     */
    @Override
    public List<Voyage> save(List<Voyage> voyages) {
        try {
            return voyages.stream().map(this::save).toList();
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to save a list of voyages.", exception);
        }
    }
}
