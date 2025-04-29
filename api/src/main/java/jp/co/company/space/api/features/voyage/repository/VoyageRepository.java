package jp.co.company.space.api.features.voyage.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.features.voyage.exception.VoyageError;
import jp.co.company.space.api.features.voyage.exception.VoyageException;

import java.util.List;
import java.util.Optional;

/**
 * The class for {@link Voyage} DB actions.
 */
@ApplicationScoped
public class VoyageRepository {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected VoyageRepository() {
    }

    /**
     * Returns an {@link Optional} {@link Voyage} instance for the provided ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link Voyage} instance.
     */
    public Optional<Voyage> findById(String id) throws VoyageException {
        try {
            return Optional.ofNullable(entityManager.find(Voyage.class, id));
        } catch (IllegalArgumentException exception) {
            throw new VoyageException(VoyageError.FIND_BY_ID, exception);
        }
    }

    /**
     * Returns a {@link List} of all existing {@link Voyage} instances.
     *
     * @return A {@link List} of {@link Voyage} instances.
     */
    public List<Voyage> getAll() throws VoyageException {
        try {
            return entityManager.createNamedQuery("Voyage.selectAll", Voyage.class).getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException |
                 NullPointerException exception) {
            throw new VoyageException(VoyageError.FIND_BY_ID, exception);
        }
    }

    /**
     * Returns a {@link List} of {@link Voyage} instances having an origin space station matching the provided space station
     * ID.
     *
     * @param originId The space station ID to search with.
     * @return A {@link List} of {@link Voyage} instances.
     */
    public List<Voyage> getAllVoyagesByOriginId(String originId) throws VoyageException {
        try {
            return entityManager.createNamedQuery("Voyage.selectAllByOriginId", Voyage.class)
                    .setParameter("originId", originId)
                    .getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException |
                 NullPointerException exception) {
            throw new VoyageException(VoyageError.GET_ALL_BY_ORIGIN_ID, exception);
        }
    }

    /**
     * Returns a {@link List} of {@link Voyage} instances having an destination space station matching the provided space
     * station ID.
     *
     * @param destinationId The space station ID to search with.
     * @return A {@link List} of {@link Voyage} instances.
     */
    public List<Voyage> getAllVoyagesByDestinationId(String destinationId) throws VoyageException {
        try {
            return entityManager.createNamedQuery("Voyage.selectAllByDestinationId", Voyage.class)
                    .setParameter("destinationId", destinationId)
                    .getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException |
                 NullPointerException exception) {
            throw new VoyageException(VoyageError.GET_ALL_BY_DESTINATION_ID, exception);
        }
    }

    /**
     * Returns a {@link List} of {@link Voyage} instances having an origin space station matching the provided origin space
     * station ID and a destination space station matching the provided destination space station ID.
     *
     * @param originId      The origin space station ID to search with.
     * @param destinationId The destination space station ID to search with.
     * @return A {@link List} of {@link Voyage} instances.
     */
    public List<Voyage> getAllVoyagesByOriginIdAndDestinationId(String originId, String destinationId) throws VoyageException {
        try {
            return entityManager.createNamedQuery("Voyage.selectAllByOriginIdAndDestinationId", Voyage.class)
                    .setParameter("originId", originId)
                    .setParameter("destinationId", destinationId)
                    .getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException |
                 NullPointerException exception) {
            throw new VoyageException(VoyageError.GET_ALL_BY_ORIGIN_ID_AND_DESTINATION_ID, exception);
        }
    }

    /**
     * Persists a {@link Voyage} instance.
     *
     * @param voyage The voyage instance to persist.
     * @return A persisted {@link Voyage} instance.
     */
    public Voyage save(Voyage voyage) throws VoyageException {
        if (findById(voyage.getId()).isEmpty()) {
            try {
                entityManager.persist(voyage);
                return findById(voyage.getId()).orElseThrow(() -> new VoyageException(VoyageError.FIND_BY_ID));
            } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException exception) {
                throw new VoyageException(VoyageError.SAVE, exception);
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
    public Voyage merge(Voyage voyage) throws VoyageException {
        try {
            return entityManager.merge(voyage);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new VoyageException(VoyageError.MERGE, exception);
        }
    }

    /**
     * Persists a {@link List} of {@link Voyage} instance.
     *
     * @param voyages The list of voyage instances to persist.
     * @return A persisted {@link List} of {@link Voyage} instances.
     */
    public List<Voyage> save(List<Voyage> voyages) throws VoyageException {
        try {
            return voyages.stream().map(this::save).toList();
        } catch (VoyageException exception) {
            throw new VoyageException(VoyageError.SAVE_LIST, exception);
        }
    }
}
