package jp.co.nova.gate.api.features.passenger.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TransactionRequiredException;
import jakarta.transaction.Transactional;
import jp.co.nova.gate.api.features.passenger.domain.Passenger;
import jp.co.nova.gate.api.features.passenger.exception.PassengerError;
import jp.co.nova.gate.api.features.passenger.exception.PassengerException;

import java.util.List;
import java.util.Optional;

/**
 * The class for {@link Passenger} DB actions.
 */
@ApplicationScoped
public class PassengerRepository {

    // TODO: add a way to remove existing passengers

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected PassengerRepository() {
    }

    /**
     * Gets an {@link Optional} {@link Passenger} matching an ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link Passenger}.
     */
    public Optional<Passenger> findById(String id) throws PassengerException {
        try {
            return Optional.ofNullable(entityManager.find(Passenger.class, id));
        } catch (IllegalArgumentException exception) {
            throw new PassengerException(PassengerError.FIND_BY_ID, exception);
        }
    }

    /**
     * Saves a {@link Passenger}.
     *
     * @param passenger The {@link Passenger} to save.
     * @return The saved {@link Passenger}.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Passenger save(Passenger passenger) throws PassengerException {
        if (findById(passenger.getId()).isEmpty()) {
            try {
                entityManager.persist(passenger);
                return passenger;
            } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException exception) {
                throw new PassengerException(PassengerError.SAVE, exception);
            }
        } else {
            return merge(passenger);
        }
    }

    /**
     * Merges a persisted {@link Passenger}.
     *
     * @param passenger The {@link Passenger} to merge.
     * @return The merged {@link Passenger}.
     */
    public Passenger merge(Passenger passenger) throws PassengerException {
        try {
            return entityManager.merge(passenger);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new PassengerException(PassengerError.MERGE, exception);
        }
    }

    /**
     * Persists a {@link List} of {@link Passenger}s.
     *
     * @param passengers The {@link List} of {@link Passenger}s to save.
     * @return A {@link List} of persisted {@link Passenger}s
     */
    public List<Passenger> save(List<Passenger> passengers) throws PassengerException {
        try {
            return passengers.stream().map(this::save).toList();
        } catch (PassengerException exception) {
            throw new PassengerException(PassengerError.SAVE_LIST, exception);
        }
    }
}
