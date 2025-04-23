package jp.co.company.space.api.features.booking.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TransactionRequiredException;
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.booking.domain.Booking;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * The class for {@link Booking} DB actions.
 */
@ApplicationScoped
public class BookingRepository {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected BookingRepository() {
    }

    /**
     * Returns an {@link Optional} persisted {@link Booking} instance matching the provided booking ID.
     *
     * @param id The booking ID to search with.
     * @return An {@link Optional} persisted {@link Booking} instance.
     */
    public Optional<Booking> findById(String id) {
        return entityManager.createNamedQuery("Booking.selectById", Booking.class).setParameter("id", id).getResultStream().findFirst();
    }

    /**
     * Returns a {@link List} of persisted {@link Booking} instances matching the provided user ID.
     *
     * @param userId The user ID to search with.
     * @return A {@link List} of persisted {@link Booking} instances.
     */
    public List<Booking> getAllByUserId(String userId) {
        return entityManager.createNamedQuery("Booking.selectAllByUserId", Booking.class).setParameter("userId", userId).getResultList();
    }

    /**
     * Returns a {@link List} of persisted {@link Booking} instances matching the provided voyage ID.
     *
     * @param voyageId The voyage ID to search with.
     * @return A {@link List} of persisted {@link Booking} instances.
     */
    public List<Booking> getAllByVoyageId(String voyageId) {
        return entityManager.createNamedQuery("Booking.selectAllByVoyageId", Booking.class).setParameter("voyageId", voyageId).getResultList();
    }

    /**
     * Persists a {@link Booking} instance.
     *
     * @param booking The {@link Booking} instance to persist.
     * @return A persisted {@link Booking} instance.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Booking save(Booking booking) {
        if (findById(booking.getId()).isEmpty()) {
            try {
                entityManager.persist(booking);
                return findById(booking.getId()).orElseThrow();
            } catch (TransactionRequiredException | EntityExistsException | NoSuchElementException |
                     IllegalArgumentException exception) {
                throw new IllegalArgumentException("Failed to save a booking instance.", exception);
            }
        } else {
            return merge(booking);
        }
    }

    /**
     * Merges a {@link Booking} instance with a persisted {@link Booking}.
     *
     * @param booking The {@link Booking} instance to merge.
     * @return A persisted {@link Booking} instance.
     */
    public Booking merge(Booking booking) {
        try {
            return entityManager.merge(booking);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to merge a booking instance.", exception);
        }
    }

    /**
     * Saves a {@link List} of {@link Booking} instances.
     *
     * @param bookings The {@link List} of {@link Booking} to save.
     * @return A {@link List} of persisted {@link Booking} instances.
     */
    public List<Booking> save(List<Booking> bookings) {
        try {
            return bookings.stream().map(this::save).toList();
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to save a list of bookings.", exception);
        }
    }
}
