package jp.co.company.space.api.features.pod.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TransactionRequiredException;
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.pod.domain.PodReservation;
import jp.co.company.space.api.features.voyage.domain.Voyage;

import java.util.List;
import java.util.NoSuchElementException;
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
     * Returns a {@link PodReservation} instance matching the provided ID.
     *
     * @param id The ID of the {@link PodReservation} instance to search for.
     * @return An {@link Optional} {@link PodReservation} instance.
     */
    public Optional<PodReservation> findById(String id) {
        return Optional.ofNullable(entityManager.find(PodReservation.class, id));
    }

    /**
     * Returns a {@link List} of all existing {@link PodReservation} instances matching the provided {@link Voyage} instance.
     *
     * @param voyage       The voyage to search for.
     * @return A {@link List} of {@link PodReservation} instances.
     */
    public List<PodReservation> getAllBySpaceShuttleAndVoyage(Voyage voyage) {
        return entityManager.createNamedQuery("PodReservation.selectAllByVoyage", PodReservation.class)
                .setParameter("voyage", voyage)
                .getResultList();
    }

    /**
     * Saves a {@link PodReservation} instance.
     *
     * @param podReservation The {@link PodReservation} instance to save.
     * @return The saved {@link PodReservation} instance.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public PodReservation save(PodReservation podReservation) {
        if (findById(podReservation.getId()).isEmpty()) {
            try {
                entityManager.persist(podReservation);
                return findById(podReservation.getId()).orElseThrow();
            } catch (TransactionRequiredException | EntityExistsException | NoSuchElementException
                     | IllegalArgumentException exception) {
                throw new IllegalArgumentException("Failed to save a pod reservation instance.", exception);
            }
        } else {
            return merge(podReservation);
        }
    }

    /**
     * Merges a persisted {@link PodReservation} instance with the provided instance.
     *
     * @param podReservation The {@link PodReservation} instance to merge.
     * @return The merged {@link PodReservation} instance.
     */
    public PodReservation merge(PodReservation podReservation) {
        try {
            return entityManager.merge(podReservation);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to merge a pod reservation instance.", exception);
        }
    }

    /**
     * Persists a {@link List} of {@link PodReservation} instances.
     *
     * @param podReservations The {@link List} of {@link PodReservation} instances to save.
     * @return A {@link List} of persisted {@link PodReservation} instances
     */
    public List<PodReservation> save(List<PodReservation> podReservations) {
        return podReservations.stream().map(this::save).toList();
    }
}
