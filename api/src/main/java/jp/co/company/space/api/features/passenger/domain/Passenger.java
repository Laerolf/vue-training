package jp.co.company.space.api.features.passenger.domain;

import jakarta.persistence.*;
import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.catalog.domain.MealPreference;
import jp.co.company.space.api.features.catalog.domain.PackageType;
import jp.co.company.space.api.features.passenger.exception.PassengerError;
import jp.co.company.space.api.features.passenger.exception.PassengerException;
import jp.co.company.space.api.features.pod.domain.PodReservation;
import jp.co.company.space.api.features.voyage.domain.Voyage;

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
     * @param mealPreference      The meal preference of a passenger.
     * @param packageType         The package type assigned to a passenger.
     * @param podReservation      The pod reservation of a passenger.
     * @param personalInformation The personal information of a passenger.
     * @param booking             The booking of a passenger.
     * @param voyage              The voyage of a passenger.
     * @return A {@link Passenger} instance.
     */
    public static Passenger create(MealPreference mealPreference, PackageType packageType, PodReservation podReservation, PersonalInformation personalInformation, Booking booking, Voyage voyage) throws PassengerException {
        return new Passenger(UUID.randomUUID().toString(), ZonedDateTime.now(), mealPreference, packageType, podReservation, personalInformation, booking, voyage);
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
    public static Passenger create(MealPreference mealPreference, PackageType packageType, Booking booking, Voyage voyage) throws PassengerException {
        return new Passenger(UUID.randomUUID().toString(), ZonedDateTime.now(), mealPreference, packageType, null, null, booking, voyage);
    }

    /**
     * Reconstructs a {@link Passenger} instance.
     *
     * @param id                  The ID of a passenger.
     * @param creationDate        The creation date of a passenger.
     * @param mealPreference      The meal preference of a passenger.
     * @param packageType         The package type assigned to a passenger.
     * @param podReservation      The pod reservation of a passenger.
     * @param personalInformation The personal information of a passenger.
     * @param booking             The booking of a passenger.
     * @param voyage              The voyage of a passenger.
     * @return A {@link Passenger} instance.
     */
    public static Passenger reconstruct(String id, ZonedDateTime creationDate, MealPreference mealPreference, PackageType packageType, PodReservation podReservation, PersonalInformation personalInformation, Booking booking, Voyage voyage) throws PassengerException {
        return new Passenger(id, creationDate, mealPreference, packageType, podReservation, personalInformation, booking, voyage);
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
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    /**
     * The meal preference of the user.
     */
    @Basic
    @Column(name = "meal_preference", nullable = false)
    private MealPreference mealPreference;

    /**
     * The package type assigned to the passenger.
     */
    @Basic(optional = false)
    @Column(name = "package_type", nullable = false)
    private PackageType packageType;

    // TODO: add unit tests for domain models
    /**
     * The pod assigned to the passenger on a space shuttle.
     */
    @OneToOne(mappedBy = "passenger")
    private PodReservation podReservation;

    @OneToOne(mappedBy = "passenger")
    private PersonalInformation personalInformation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "booking_id", table = "passengers", nullable = false)
    private Booking booking;

    @ManyToOne(optional = false)
    @JoinColumn(name = "voyage_id", table = "passengers", nullable = false)
    private Voyage voyage;

    protected Passenger() {
    }

    protected Passenger(String id, ZonedDateTime creationDate, MealPreference mealPreference, PackageType packageType, PodReservation podReservation, PersonalInformation personalInformation, Booking booking, Voyage voyage) throws PassengerException {
        if (id == null) {
            throw new PassengerException(PassengerError.MISSING_ID);
        } else if (creationDate == null) {
            throw new PassengerException(PassengerError.MISSING_CREATION_DATE);
        } else if (mealPreference == null) {
            throw new PassengerException(PassengerError.MISSING_MEAL_PREFERENCE);
        } else if (packageType == null) {
            throw new PassengerException(PassengerError.MISSING_PACKAGE_TYPE);
        } else if (booking == null) {
            throw new PassengerException(PassengerError.MISSING_BOOKING);
        } else if (voyage == null) {
            throw new PassengerException(PassengerError.MISSING_VOYAGE);
        }

        this.id = id;
        this.creationDate = creationDate;
        this.mealPreference = mealPreference;
        this.packageType = packageType;
        this.podReservation = podReservation;
        this.personalInformation = personalInformation;
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

    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    public Booking getBooking() {
        return booking;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    /**
     * Sets a {@link PodReservation} instance as the reservation of this passenger.
     *
     * @param podReservation The pod reservation to set.
     */
    public void assignPodReservation(PodReservation podReservation) throws PassengerException {
        if (podReservation == null) {
            throw new PassengerException(PassengerError.MISSING_POD_RESERVATION);
        } else if (this.getPodReservation() != null) {
            throw new PassengerException(PassengerError.HAS_POD_RESERVATION);
        }

        this.podReservation = podReservation;
    }

    /**
     * Assigns {@link PersonalInformation} to this passenger.
     *
     * @param personalInformation The information to set.
     */
    public void assignPersonalInformation(PersonalInformation personalInformation) throws PassengerException {
        if (personalInformation == null) {
            throw new PassengerException(PassengerError.MISSING_PERSONAL_INFORMATION);
        }

        this.personalInformation = personalInformation;
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
