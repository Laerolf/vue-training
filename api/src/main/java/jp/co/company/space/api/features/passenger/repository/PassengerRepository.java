package jp.co.company.space.api.features.passenger.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jp.co.company.space.api.features.passenger.domain.Passenger;

import java.util.Optional;

/**
 * The class for {@link Passenger} DB actions.
 */
@ApplicationScoped
public class PassengerRepository {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected PassengerRepository() {
    }

    /**
     * Gets an {@link Optional} {@link Passenger} instance matching an ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link Passenger} instance.
     */
    public Optional<Passenger> findById(String id) {
        return Optional.ofNullable(entityManager.find(Passenger.class, id));
    }
}
