package jp.co.company.space.api.features.pod.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jp.co.company.space.api.features.pod.domain.PodReservation;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.shared.interfaces.QueryRepository;

import java.util.List;
import java.util.Optional;

/**
 * The class for {@link PodReservation} DB actions.
 */
@ApplicationScoped
public class PodReservationRepository implements QueryRepository<PodReservation> {

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
    @Override
    public Optional<PodReservation> findById(String id) {
        return Optional.ofNullable(entityManager.find(PodReservation.class, id));
    }

    /**
     * Returns a {@link List} of all existing {@link PodReservation} instances.
     *
     * @return A {@link List} of {@link PodReservation} instances.
     */
    @Override
    public List<PodReservation> getAll() {
        return entityManager.createNamedQuery("PodReservation.getAll", PodReservation.class).getResultList();
    }

    /**
     * Returns a {@link List} of all existing {@link PodReservation} instances matching the provided {@link SpaceShuttle} and {@link Voyage} instance.
     *
     * @param spaceShuttle The space shuttle to search for.
     * @param voyage       The voyage to search for.
     * @return A {@link List} of {@link PodReservation} instances.
     */
    public List<PodReservation> getAllBySpaceShuttleAndVoyage(SpaceShuttle spaceShuttle, Voyage voyage) {
        return entityManager.createNamedQuery("PodReservation.getAllBySpaceShuttleAndVoyage", PodReservation.class)
                .setParameter("spaceShuttle", spaceShuttle)
                .setParameter("voyage", voyage)
                .getResultList();
    }
}
