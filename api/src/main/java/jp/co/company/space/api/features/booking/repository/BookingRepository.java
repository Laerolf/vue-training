package jp.co.company.space.api.features.booking.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.booking.exception.BookingError;
import jp.co.company.space.api.features.booking.exception.BookingException;

import java.util.List;
import java.util.Optional;

/**
 * The class for {@link Booking} DB actions.
 */
@ApplicationScoped
public class BookingRepository {

    // TODO: add a way to remove existing bookings

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
    public Optional<Booking> findById(String id) throws BookingException {
        try {
            return entityManager.createNamedQuery("Booking.selectById", Booking.class).setParameter("id", id).getResultStream().findFirst();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException |
                 NullPointerException exception) {
            throw new BookingException(BookingError.FIND_BY_ID, exception);
        }
    }

    /**
     * Returns a {@link List} of persisted {@link Booking} instances matching the provided user ID.
     *
     * @param userId The user ID to search with.
     * @return A {@link List} of persisted {@link Booking} instances.
     */
    public List<Booking> getAllByUserId(String userId) throws BookingException {
        try {
            return entityManager.createNamedQuery("Booking.selectAllByUserId", Booking.class).setParameter("userId", userId).getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException |
                 NullPointerException exception) {
            throw new BookingException(BookingError.FIND_BY_USER_ID, exception);
        }
    }

    /**
     * Returns a {@link List} of persisted {@link Booking} instances matching the provided voyage ID.
     *
     * @param voyageId The voyage ID to search with.
     * @return A {@link List} of persisted {@link Booking} instances.
     */
    public List<Booking> getAllByVoyageId(String voyageId) throws BookingException {
        try {
            return entityManager.createNamedQuery("Booking.selectAllByVoyageId", Booking.class).setParameter("voyageId", voyageId).getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException |
                 NullPointerException exception) {
            throw new BookingException(BookingError.FIND_BY_VOYAGE_ID, exception);
        }
    }

    /**
     * Persists a {@link Booking} instance.
     *
     * @param booking The {@link Booking} instance to persist.
     * @return A persisted {@link Booking} instance.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public Booking save(Booking booking) throws BookingException {
        if (findById(booking.getId()).isEmpty()) {
            try {
                entityManager.persist(booking);
                return findById(booking.getId()).orElseThrow(() -> new BookingException(BookingError.FIND_BY_ID));
            } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException exception) {
                throw new BookingException(BookingError.SAVE, exception);
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
    public Booking merge(Booking booking) throws BookingException {
        try {
            return entityManager.merge(booking);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new BookingException(BookingError.MERGE, exception);
        }
    }

    /**
     * Saves a {@link List} of {@link Booking} instances.
     *
     * @param bookings The {@link List} of {@link Booking} to save.
     * @return A {@link List} of persisted {@link Booking} instances.
     */
    public List<Booking> save(List<Booking> bookings) throws BookingException {
        try {
            return bookings.stream().map(this::save).toList();
        } catch (IllegalArgumentException exception) {
            throw new BookingException(BookingError.SAVE_LIST, exception);
        }
    }
}
