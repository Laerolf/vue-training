package jp.co.company.space.api.features.user.domain;

import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (emailAddress == null) {
            if (other.emailAddress != null) {
                return false;
            }
        } else if (!emailAddress.equals(other.emailAddress)) {
            return false;
        }
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        return true;
    }

}
