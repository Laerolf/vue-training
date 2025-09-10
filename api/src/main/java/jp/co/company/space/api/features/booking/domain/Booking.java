package jp.co.company.space.api.features.booking.domain;

import jakarta.persistence.*;
import jp.co.company.space.api.features.booking.exception.BookingError;
import jp.co.company.space.api.features.booking.exception.BookingException;
import jp.co.company.space.api.features.passenger.domain.Passenger;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.voyage.domain.Voyage;

import java.time.ZonedDateTime;
import java.util.*;

/**
 * A POJO representing a booking.
 */
@Entity
@Table(name = "bookings")
@Access(AccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = "Booking.selectById", query = "SELECT b FROM Booking b LEFT JOIN FETCH b.passengers WHERE b.id = :id"),
        @NamedQuery(name = "Booking.selectAllByUserId", query = "SELECT b FROM Booking b LEFT JOIN FETCH b.passengers WHERE b.user.id = :userId"),
        @NamedQuery(name = "Booking.selectAllByVoyageId", query = "SELECT b FROM Booking b LEFT JOIN FETCH b.passengers WHERE b.voyage.id = :voyageId")
})
public class Booking {
    /**
     * Creates a new {@link Booking} for a {@link User} and {@link Voyage}.
     *
     * @param user   The user who made the booking.
     * @param voyage The voyage of the booking.
     * @return A new {@link Booking}.
     * @throws BookingException When the user or voyage of the booking are missing.
     */
    public static Booking create(User user, Voyage voyage) throws BookingException {
        return new Booking(UUID.randomUUID().toString(), ZonedDateTime.now(), BookingStatus.DRAFT, user, voyage);
    }

    /**
     * Returns a {@link Booking}.
     *
     * @param id           The ID of the booking.
     * @param creationDate The creation date of the booking.
     * @param status       The status of the booking.
     * @param user         The user who made the booking.
     * @param voyage       The voyage of the booking.
     * @return A {@link Booking}.
     * @throws BookingException When the id, creation date, status, user or voyage of the booking are missing.
     */
    public static Booking reconstruct(String id, ZonedDateTime creationDate, BookingStatus status, User user, Voyage voyage) throws BookingException {
        return new Booking(id, creationDate, status, user, voyage);
    }

    /**
     * The ID of the booking.
     */
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id;

    /**
     * The creation date of the booking.
     */
    @Basic(optional = false)
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    /**
     * The status of the booking.
     */
    @Basic(optional = false)
    @Column(nullable = false)
    private BookingStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", table = "bookings", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "voyage_id", table = "bookings", nullable = false)
    private Voyage voyage;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Passenger> passengers = new HashSet<>();

    protected Booking() {
    }

    protected Booking(String id, ZonedDateTime creationDate, BookingStatus status, User user, Voyage voyage) throws BookingException {
        if (id == null) {
            throw new BookingException(BookingError.MISSING_ID);
        } else if (creationDate == null) {
            throw new BookingException(BookingError.MISSING_CREATION_DATE);
        } else if (status == null) {
            throw new BookingException(BookingError.MISSING_STATUS);
        } else if (user == null) {
            throw new BookingException(BookingError.MISSING_USER);
        } else if (voyage == null) {
            throw new BookingException(BookingError.MISSING_VOYAGE);
        }

        this.id = id;
        this.creationDate = creationDate;
        this.status = status;
        this.user = user;
        this.voyage = voyage;
    }

    public String getId() {
        return id;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    /**
     * Assigns the provided {@link List} of {@link Passenger}s to this booking.
     *
     * @param passengerList The passengers to assign to this voyage.
     * @throws BookingException When the passengers are missing.
     */
    public void assignPassengers(List<Passenger> passengerList) throws BookingException {
        if (passengerList == null) {
            throw new BookingException(BookingError.MISSING_PASSENGERS);
        }

        this.passengers = new HashSet<>(passengerList);
    }

    /**
     * Changes the {@link Booking}'s status to "created".
     */
    public void setCreated() {
        status = BookingStatus.CREATED;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(creationDate, booking.creationDate) && status == booking.status && Objects.equals(user, booking.user) && Objects.equals(voyage, booking.voyage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, status, user, voyage);
    }
}
