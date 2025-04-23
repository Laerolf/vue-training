package jp.co.company.space.api.features.passenger.domain;

import jakarta.persistence.*;
import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.catalog.domain.MealPreference;
import jp.co.company.space.api.features.catalog.domain.PackageType;
import jp.co.company.space.api.features.pod.domain.PodReservation;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.shared.exception.DomainException;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A POJO representing a passenger.
 */
@Entity
@Table(name = "passengers")
@Access(AccessType.FIELD)
public class Passenger {

    /**
     * Creates a {@link Passenger} instance.
     *
     * @param mealPreference The meal preference of a passenger.
     * @param packageType    The package type assigned to a passenger.
     * @param podReservation The pod reservation of a passenger.
     * @param booking        The booking of a passenger.
     * @param voyage         The voyage of a passenger.
     * @return A {@link Passenger} instance.
     */
    public static Passenger create(MealPreference mealPreference, PackageType packageType, PodReservation podReservation, Booking booking, Voyage voyage) {
        return new Passenger(UUID.randomUUID().toString(), ZonedDateTime.now(), mealPreference, packageType, podReservation, booking, voyage);
    }

    /**
     * Creates a {@link Passenger} instance.
     *
     * @param mealPreference The meal preference of a passenger.
     * @param packageType    The package type assigned to a passenger.
     * @param booking        The booking of a passenger.
     * @param voyage         The voyage of a passenger.
     * @return A {@link Passenger} instance.
     */
    public static Passenger create(MealPreference mealPreference, PackageType packageType, Booking booking, Voyage voyage) {
        return new Passenger(UUID.randomUUID().toString(), ZonedDateTime.now(), mealPreference, packageType, null, booking, voyage);
    }

    /**
     * Recreates a {@link Passenger} instance.
     *
     * @param id             The ID of a passenger.
     * @param creationDate   The creation date of a passenger.
     * @param mealPreference The meal preference of a passenger.
     * @param packageType    The package type assigned to a passenger.
     * @param podReservation The pod reservation of a passenger.
     * @param booking        The booking of a passenger.
     * @param voyage         The voyage of a passenger.
     * @return A {@link Passenger} instance.
     */
    public static Passenger recreate(String id, ZonedDateTime creationDate, MealPreference mealPreference, PackageType packageType, PodReservation podReservation, Booking booking, Voyage voyage) {
        return new Passenger(id, creationDate, mealPreference, packageType, podReservation, booking, voyage);
    }

    /**
     * The ID of the passenger.
     */
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id;

    /**
     * The creation date of the passenger.
     */
    @Basic
    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    /**
     * The meal preference of the user.
     */
    @Basic
    @Column(name = "meal_preference")
    private MealPreference mealPreference;

    /**
     * The package type assigned to the passenger.
     */
    @Basic(optional = false)
    @Column(name = "package_type", nullable = false)
    private PackageType packageType;

    // TODO: add unit tests for domain models
    // TODO: improve relation properties like cascade
    /**
     * The pod assigned to the passenger on a space shuttle.
     */
    @OneToOne(mappedBy = "passenger")
    private PodReservation podReservation;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "booking_id", table = "passengers", nullable = false)
    private Booking booking;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "voyage_id", table = "passengers", nullable = false)
    private Voyage voyage;

    protected Passenger() {
    }

    protected Passenger(String id, ZonedDateTime creationDate, MealPreference mealPreference, PackageType packageType, PodReservation podReservation, Booking booking, Voyage voyage) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the passenger is missing.");
        } else if (creationDate == null) {
            throw new IllegalArgumentException("The creation date of the passenger is missing.");
        } else if (mealPreference == null) {
            throw new IllegalArgumentException("The meal preference of the passenger is missing.");
        } else if (packageType == null) {
            throw new IllegalArgumentException("The package type assigned to the passenger is missing.");
        } else if (booking == null) {
            throw new IllegalArgumentException("The booking of the passenger is missing.");
        } else if (voyage == null) {
            throw new IllegalArgumentException("The voyage of the passenger is missing.");
        }

        this.id = id;
        this.creationDate = creationDate;
        this.mealPreference = mealPreference;
        this.packageType = packageType;
        this.podReservation = podReservation;
        this.booking = booking;
        this.voyage = voyage;
    }

    public String getId() {
        return id;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public MealPreference getMealPreference() {
        return mealPreference;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    public PodReservation getPodReservation() {
        return podReservation;
    }

    public Booking getBooking() {
        return booking;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void assignPodReservation(PodReservation podReservation) {
        if (podReservation == null) {
            throw new IllegalArgumentException("The pod reservation is missing.");
        } else if (this.getPodReservation() != null) {
            throw new DomainException("This passenger already has a pod reservation.");
        }

        this.podReservation = podReservation;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(id, passenger.id) && Objects.equals(creationDate, passenger.creationDate) && mealPreference == passenger.mealPreference && packageType == passenger.packageType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, mealPreference, packageType);
    }
}
