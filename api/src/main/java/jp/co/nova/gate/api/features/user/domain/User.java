package jp.co.nova.gate.api.features.user.domain;

import jakarta.persistence.*;
import jp.co.nova.gate.api.features.booking.domain.Booking;
import jp.co.nova.gate.api.features.user.exception.UserError;
import jp.co.nova.gate.api.features.user.exception.UserException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A POJO representing a user.
 */
@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = "User.selectById", query = "SELECT u FROM User u LEFT JOIN FETCH u.bookings b LEFT JOIN FETCH b.passengers WHERE u.id = :id"),
        @NamedQuery(name = "User.selectByEmailAddress", query = "SELECT u FROM User u LEFT JOIN FETCH u.bookings b LEFT JOIN FETCH b.passengers WHERE u.emailAddress = :emailAddress")
})
public class User {

    /**
     * Creates a new {@link User}.
     *
     * @param lastName     The last name of the user.
     * @param firstName    The first name of the user.
     * @param emailAddress The email address of the user.
     * @param password     The password of the user.
     * @return A new {@link User}.
     */
    public static User create(String lastName, String firstName, String emailAddress, String password) throws UserException {
        return new User(UUID.randomUUID().toString(), lastName, firstName, emailAddress, password);
    }

    /**
     * Reconstructs a {@link User}.
     *
     * @param id           The ID of the user.
     * @param lastName     The last name of the user.
     * @param firstName    The first name of the user.
     * @param emailAddress The email address of the user.
     * @param password     The password of the user.
     * @return A {@link User}.
     */
    public static User reconstruct(String id, String lastName, String firstName, String emailAddress, String password) throws UserException {
        return new User(id, lastName, firstName, emailAddress, password);
    }

    /**
     * The ID of the user.
     */
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id;

    /**
     * The last name of the user.
     */
    @Basic(optional = false)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * The first name of the user.
     */
    @Basic(optional = false)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * The email address of the user.
     */
    @Basic(optional = false)
    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    /**
     * The password of the user.
     */
    @Basic(optional = false)
    @Column(nullable = false)
    private String password;

    /**
     * The bookings of the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Booking> bookings = new HashSet<>();

    protected User() {
    }

    protected User(String id, String lastName, String firstName, String emailAddress, String password) throws UserException {
        if (id == null) {
            throw new UserException(UserError.MISSING_ID);
        } else if (lastName == null) {
            throw new UserException(UserError.MISSING_LAST_NAME);
        } else if (firstName == null) {
            throw new UserException(UserError.MISSING_FIRST_NAME);
        } else if (emailAddress == null) {
            throw new UserException(UserError.MISSING_EMAIL_ADDRESS);
        } else if (password == null) {
            throw new UserException(UserError.MISSING_PASSWORD);
        }

        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(lastName, user.lastName) && Objects.equals(firstName, user.firstName) && Objects.equals(emailAddress, user.emailAddress) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, emailAddress, password);
    }
}
