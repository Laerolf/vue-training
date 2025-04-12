package jp.co.company.space.api.features.user.domain;

import jakarta.persistence.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;
import java.util.UUID;

/**
 * A POJO representing a user.
 */
@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
@Schema(name = "User", description = "A user.")
public class User {

    /**
     * Creates a new {@link User} instance.
     *
     * @param lastName     The last name of the user.
     * @param firstName    The first name of the user.
     * @param emailAddress The email address of the user.
     * @param password     The password of the user.
     * @return A new {@link User} instance.
     */
    public static User create(String lastName, String firstName, String emailAddress, String password) {
        return new User(UUID.randomUUID().toString(), lastName, firstName, emailAddress, password);
    }

    /**
     * Recreates a {@link User} instance.
     *
     * @param id           The ID of the user.
     * @param lastName     The last name of the user.
     * @param firstName    The first name of the user.
     * @param emailAddress The email address of the user.
     * @param password     The password of the user.
     * @return A {@link User} instance.
     */
    public static User reconstruct(String id, String lastName, String firstName, String emailAddress, String password) {
        return new User(id, lastName, firstName, emailAddress, password);
    }

    /**
     * The ID of the user.
     */
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    @Schema(description = "The ID of the user.", required = true, example = "1")
    private String id;

    /**
     * The last name of the user.
     */
    @Basic(optional = false)
    @Column(name = "last_name", nullable = false)
    @Schema(description = "The last name of the user.", example = "Jekyll")
    private String lastName;

    /**
     * The first name of the user.
     */
    @Basic(optional = false)
    @Column(name = "first_name", nullable = false)
    @Schema(description = "The first name of the user.", example = "Henry")
    private String firstName;

    /**
     * The email address of the user.
     */
    @Basic(optional = false)
    @Column(name = "email_address", nullable = false)
    @Schema(description = "The email address name of the user.", example = "edward.hyde@example.com")
    private String emailAddress;

    /**
     * The password of the user.
     */
    @Basic(optional = false)
    @Column(nullable = false)
    @Schema(description = "The password name of the user.", hidden = true)
    private String password;

    protected User() {}

    private User(String id, String lastName, String firstName, String emailAddress, String password) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the user is missing.");
        } else if (lastName == null) {
            throw new IllegalArgumentException("The last name of the user is missing.");
        } else if (firstName == null) {
            throw new IllegalArgumentException("The first name of the user is missing.");
        } else if (emailAddress == null) {
            throw new IllegalArgumentException("The email address of the user is missing.");
        } else if (password == null) {
            throw new IllegalArgumentException("The password of the user is missing.");
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
