package jp.co.company.space.api.features.passenger.domain;

import jakarta.persistence.*;
import jp.co.company.space.api.features.passenger.converter.NationalityConverter;
import jp.co.company.space.api.features.passenger.exception.PersonalInformationError;
import jp.co.company.space.api.features.passenger.exception.PersonalInformationException;
import jp.co.company.space.api.features.passenger.validation.ValidPassportNumberFormat;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A POJO representing personal information of a {@link Passenger}.
 */
@Entity
@Table(name = "passenger_personal_information")
@Access(AccessType.FIELD)
public class PersonalInformation {

    /**
     * Creates new {@link PersonalInformation} for a {@link Passenger} based on the provided values.
     *
     * @param lastName       The last name of the passenger.
     * @param middleName     The middle name of the passenger.
     * @param firstName      The first name of the passenger.
     * @param birthdate      The birthdate of the passenger.
     * @param nationality    The nationality of the passenger.
     * @param gender         The gender of the passenger.
     * @param passportNumber The passport number of the passenger.
     * @param passenger      The passenger for this personal information.
     * @return {@link PersonalInformation} for a {@link Passenger}.
     */
    public static PersonalInformation create(String lastName, String middleName, String firstName, LocalDate birthdate, Nationality nationality, Gender gender, String passportNumber, Passenger passenger) throws PersonalInformationException {
        return new PersonalInformation(UUID.randomUUID().toString(), lastName, middleName, firstName, birthdate, nationality, gender, passportNumber, passenger);
    }

    /**
     * Creates new {@link PersonalInformation} for a {@link Passenger} based on the provided values.
     *
     * @param id             The ID of the personal information.
     * @param lastName       The last name of the passenger.
     * @param middleName     The middle name of the passenger.
     * @param firstName      The first name of the passenger.
     * @param birthdate      The birthdate of the passenger.
     * @param nationality    The nationality of the passenger.
     * @param gender         The gender of the passenger.
     * @param passportNumber The passport number of the passenger.
     * @param passenger      The passenger for this personal information.
     * @return {@link PersonalInformation} for a {@link Passenger}.
     */
    public static PersonalInformation reconstruct(String id, String lastName, String middleName, String firstName, LocalDate birthdate, Nationality nationality, Gender gender, String passportNumber, Passenger passenger) throws PersonalInformationException {
        return new PersonalInformation(id, lastName, middleName, firstName, birthdate, nationality, gender, passportNumber, passenger);
    }

    /**
     * The ID of the passenger.
     */
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id;

    /**
     * The last name of the passenger.
     */
    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;

    /**
     * The middle name of the passenger.
     */
    @Basic(optional = false)
    @Column(name = "middle_name")
    private String middleName;

    /**
     * The first name of the user.
     */
    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;

    /**
     * The birthdate of the passenger.
     */
    @Basic
    @Column(name = "birthdate")
    private LocalDate birthdate;

    /**
     * The nationality of the passenger.
     */
    @Basic(optional = false)
    @Column(name = "nationality")
    @Convert(converter = NationalityConverter.class)
    private Nationality nationality;

    /**
     * The gender of the passenger.
     */
    @Basic(optional = false)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    // TODO: Correct all nullables
    /**
     * The passport number of the passenger.
     */
    @ValidPassportNumberFormat
    @Basic(optional = false)
    @Column(name = "passport_number", nullable = false, unique = true)
    private String passportNumber;

    /**
     * The passenger about this information.
     */
    @OneToOne(optional = false)
    @JoinColumn(name = "passenger_id", table = "passenger_personal_information", nullable = false)
    private Passenger passenger;

    protected PersonalInformation() {
    }

    protected PersonalInformation(String id, String lastName, String middleName, String firstName, LocalDate birthdate, Nationality nationality, Gender gender, String passportNumber, Passenger passenger) {
        if (id == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_ID);
        } else if (lastName == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_LAST_NAME);
        } else if (middleName == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_MIDDLE_NAME);
        } else if (firstName == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_FIRST_NAME);
        } else if (birthdate == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_BIRTHDATE);
        } else if (nationality == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_NATIONALITY);
        } else if (gender == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_GENDER);
        } else if (passportNumber == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_PASSPORT_NUMBER);
        } else if (passenger == null) {
            throw new PersonalInformationException(PersonalInformationError.MISSING_PASSENGER);
        }

        this.id = id;
        this.lastName = lastName;
        this.middleName = middleName;
        this.firstName = firstName;
        this.birthdate = birthdate;
        this.nationality = nationality;
        this.gender = gender;
        this.passportNumber = passportNumber;
        this.passenger = passenger;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PersonalInformation that = (PersonalInformation) o;
        return Objects.equals(id, that.id) && Objects.equals(lastName, that.lastName) && Objects.equals(middleName, that.middleName) && Objects.equals(firstName, that.firstName) && Objects.equals(birthdate, that.birthdate) && Objects.equals(nationality, that.nationality) && gender == that.gender && Objects.equals(passportNumber, that.passportNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, middleName, firstName, birthdate, nationality, gender, passportNumber);
    }
}
