package jp.co.company.space.api.features.pod.domain;

import jakarta.persistence.*;
import jp.co.company.space.api.features.passenger.domain.Passenger;
import jp.co.company.space.api.features.pod.exception.PodReservationError;
import jp.co.company.space.api.features.pod.exception.PodReservationException;
import jp.co.company.space.api.features.voyage.domain.Voyage;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A POJO representing a pod reservation of a passenger on a space shuttle.
 */
@Entity
@Table(name = "pod_reservations")
@Access(AccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = "PodReservation.selectAllByVoyage", query = "SELECT pr FROM PodReservation pr WHERE pr.voyage = :voyage")
})
public class PodReservation {

    /**
     * Creates a new {@link PodReservation}.
     *
     * @param podCode   The pod code of the pod reservation.
     * @param passenger The passenger of the pod reservation.
     * @param voyage    The voyage of the pod the reservation.
     * @return A new {@link PodReservation}.
     */
    public static PodReservation create(String podCode, Passenger passenger, Voyage voyage) throws PodReservationException {
        return new PodReservation(UUID.randomUUID().toString(), podCode, ZonedDateTime.now(), passenger, voyage);
    }

    /**
     * Reconstructs a new {@link PodReservation}.
     *
     * @param id           The ID of the pod reservation.
     * @param podCode      The pod code of the pod reservation.
     * @param creationDate The creation date of the pod reservation.
     * @param passenger    The passenger of the pod reservation.
     * @param voyage       The voyage of the pod the reservation.
     * @return A new {@link PodReservation}.
     */
    public static PodReservation reconstruct(String id, String podCode, ZonedDateTime creationDate, Passenger passenger, Voyage voyage) throws PodReservationException {
        return new PodReservation(id, podCode, creationDate, passenger, voyage);
    }

    /**
     * The ID of the pod reservation.
     */
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id;

    /**
     * The pod code of the pod reservation.
     */
    @Basic
    @Column(name = "pod_code", nullable = false)
    private String podCode;

    @Basic
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @OneToOne(optional = false)
    @JoinColumn(name = "passenger_id", table = "pod_reservations", nullable = false)
    private Passenger passenger;

    @ManyToOne(optional = false)
    @JoinColumn(name = "voyage_id", table = "pod_reservations", nullable = false)
    private Voyage voyage;

    protected PodReservation() {
    }

    protected PodReservation(String id, String podCode, ZonedDateTime creationDate, Passenger passenger, Voyage voyage) throws PodReservationException {
        if (id == null) {
            throw new PodReservationException(PodReservationError.MISSING_ID);
        } else if (podCode == null) {
            throw new PodReservationException(PodReservationError.MISSING_CODE);
        } else if (creationDate == null) {
            throw new PodReservationException(PodReservationError.MISSING_CREATION_DATE);
        } else if (passenger == null) {
            throw new PodReservationException(PodReservationError.MISSING_PASSENGER);
        } else if (voyage == null) {
            throw new PodReservationException(PodReservationError.MISSING_VOYAGE);
        }

        this.id = id;
        this.podCode = podCode;
        this.creationDate = creationDate;
        this.passenger = passenger;
        this.voyage = voyage;
    }

    public String getId() {
        return id;
    }

    public String getPodCode() {
        return podCode;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PodReservation that = (PodReservation) o;
        return Objects.equals(id, that.id) && Objects.equals(podCode, that.podCode) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, podCode, creationDate);
    }
}
