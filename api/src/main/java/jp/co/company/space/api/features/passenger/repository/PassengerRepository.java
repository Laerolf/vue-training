package jp.co.company.space.api.features.passenger.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TransactionRequiredException;
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.passenger.domain.Passenger;

import java.util.List;
import java.util.NoSuchElementException;
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

    /**
     * Saves a {@link Passenger} instance.
     *
     * @param passenger The {@link Passenger} instance to save.
     * @return The saved {@link Passenger} instance.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Passenger save(Passenger passenger) {
        if (findById(passenger.getId()).isEmpty()) {
            try {
                entityManager.persist(passenger);
                return findById(passenger.getId()).orElseThrow();
            } catch (TransactionRequiredException | EntityExistsException | NoSuchElementException
                     | IllegalArgumentException exception) {
                throw new IllegalArgumentException("Failed to save a passenger instance.", exception);
            }
        } else {
            return merge(passenger);
        }
    }

    /**
     * Merges a persisted {@link Passenger} instance with the provided instance.
     *
     * @param passenger The {@link Passenger} instance to merge.
     * @return The merged {@link Passenger} instance.
     */
    public Passenger merge(Passenger passenger) {
        try {
            return entityManager.merge(passenger);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to merge a passenger instance.", exception);
        }
    }

    /**
     * Persists a {@link List} of {@link Passenger} instances.
     *
     * @param passengers The {@link List} of {@link Passenger} instances to save.
     * @return A {@link List} of persisted {@link Passenger} instances
     */
    public List<Passenger> save(List<Passenger> passengers) {
        return passengers.stream().map(this::save).toList();
    }
}
