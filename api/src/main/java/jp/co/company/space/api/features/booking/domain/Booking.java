package jp.co.company.space.api.features.booking.domain;

import jakarta.persistence.*;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.voyage.domain.Voyage;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A POJO representing a booking.
 */
@Entity
@Table(name = "bookings")
@Access(AccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = "Booking.getAllByUserId", query = "SELECT b FROM Booking b WHERE b.user.id = :userId"),
        @NamedQuery(name = "Booking.getAllByVoyageId", query = "SELECT b FROM Booking b WHERE b.voyage.id = :voyageId")
})
public class Booking {
    /**
     * Creates a new {@link Booking} instance for a {@link User} instance and {@link Voyage} instance.
     *
     * @param user   The user who made the booking.
     * @param voyage The voyage of the booking.
     * @return A new {@link Booking} instance.
     */
    public static Booking create(User user, Voyage voyage) {
        return new Booking(UUID.randomUUID().toString(), ZonedDateTime.now(), BookingStatus.DRAFT, user, voyage);
    }

    /**
     * Returns a {@link Booking} instance.
     *
     * @param id           The ID of the booking.
     * @param creationDate The creation date of the booking.
     * @param status       The status of the booking.
     * @param user         The user who made the booking.
     * @param voyage       The voyage of the booking.
     * @return A {@link Booking} instance.
     */
    public static Booking reconstruct(String id, ZonedDateTime creationDate, BookingStatus status, User user, Voyage voyage) {
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

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "user_id", table = "bookings", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "voyage_id", table = "bookings", nullable = false)
    private Voyage voyage;

    protected Booking() {
    }

    protected Booking(String id, ZonedDateTime creationDate, BookingStatus status, User user, Voyage voyage) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the booking is missing.");
        } else if (creationDate == null) {
            throw new IllegalArgumentException("The creation date of the booking is missing.");
        } else if (status == null) {
            throw new IllegalArgumentException("The status of the booking is missing.");
        } else if (user == null) {
            throw new IllegalArgumentException("The user of the booking is missing.");
        } else if (voyage == null) {
            throw new IllegalArgumentException("The voyage of the booking is missing.");
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
