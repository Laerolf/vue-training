package jp.co.nova.gate.api.features.pod.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jp.co.nova.gate.api.features.pod.domain.PodReservation;
import jp.co.nova.gate.api.features.pod.exception.PodReservationError;
import jp.co.nova.gate.api.features.pod.exception.PodReservationException;
import jp.co.nova.gate.api.features.voyage.domain.Voyage;

import java.util.List;
import java.util.Optional;

/**
 * The class for {@link PodReservation} DB actions.
 */
@ApplicationScoped
public class PodReservationRepository {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected PodReservationRepository() {
    }

    /**
     * Returns a {@link PodReservation} matching the provided ID.
     *
     * @param id The ID of the {@link PodReservation} to search for.
     * @return An {@link Optional} {@link PodReservation}.
     */
    public Optional<PodReservation> findById(String id) throws PodReservationException {
        try {
            return Optional.ofNullable(entityManager.find(PodReservation.class, id));
        } catch (IllegalArgumentException exception) {
            throw new PodReservationException(PodReservationError.FIND_BY_ID, exception);
        }
    }

    /**
     * Returns a {@link List} of all existing {@link PodReservation}s matching the provided {@link Voyage}.
     *
     * @param voyage The voyage to search for.
     * @return A {@link List} of {@link PodReservation}s.
     */
    public List<PodReservation> getAllBySpaceShuttleAndVoyage(Voyage voyage) throws PodReservationException {
        try {
            return entityManager.createNamedQuery("PodReservation.selectAllByVoyage", PodReservation.class)
                    .setParameter("voyage", voyage)
                    .getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException |
                 NullPointerException exception) {
            throw new PodReservationException(PodReservationError.GET_ALL_BY_SPACE_SHUTTLE_AND_VOYAGE, exception);
        }
    }

    /**
     * Saves a {@link PodReservation}.
     *
     * @param podReservation The {@link PodReservation} to save.
     * @return The saved {@link PodReservation}.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public PodReservation save(PodReservation podReservation) throws PodReservationException {
        if (findById(podReservation.getId()).isEmpty()) {
            try {
                entityManager.persist(podReservation);
                return podReservation;
            } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException exception) {
                throw new PodReservationException(PodReservationError.SAVE, exception);
            }
        } else {
            return merge(podReservation);
        }
    }

    /**
     * Merges a persisted {@link PodReservation}.
     *
     * @param podReservation The {@link PodReservation} to merge.
     * @return The merged {@link PodReservation}.
     */
    public PodReservation merge(PodReservation podReservation) throws PodReservationException {
        try {
            return entityManager.merge(podReservation);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new PodReservationException(PodReservationError.MERGE, exception);
        }
    }

    /**
     * Persists a {@link List} of {@link PodReservation}s.
     *
     * @param podReservations The {@link List} of {@link PodReservation}s to save.
     * @return A {@link List} of persisted {@link PodReservation}s
     */
    public List<PodReservation> save(List<PodReservation> podReservations) {
        try {
            return podReservations.stream().map(this::save).toList();
        } catch (PodReservationException exception) {
            throw new PodReservationException(PodReservationError.SAVE_LIST, exception);
        }
    }
}
