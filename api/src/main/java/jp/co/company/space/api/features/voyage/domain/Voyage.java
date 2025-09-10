package jp.co.company.space.api.features.voyage.domain;

import jakarta.persistence.*;
import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.voyage.exception.VoyageError;
import jp.co.company.space.api.features.voyage.exception.VoyageException;
import jp.co.company.space.api.shared.exception.DomainException;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A POJO representing a voyage.
 */
@Entity
@Table(name = "voyages")
@Access(AccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = "Voyage.selectAll", query = "SELECT v FROM Voyage v"),
        @NamedQuery(name = "Voyage.selectAllByOriginId", query = "SELECT v FROM Voyage v WHERE v.route.origin.id = :originId"),
        @NamedQuery(name = "Voyage.selectAllByDestinationId", query = "SELECT v FROM Voyage v WHERE v.route.destination.id = :destinationId"),
        @NamedQuery(name = "Voyage.selectAllByOriginIdAndDestinationId", query = "SELECT v FROM Voyage v WHERE v.route.origin.id = :originId AND v.route.destination.id = :destinationId")
})
public class Voyage {

    /**
     * Returns a new {@link Voyage}.
     *
     * @param departureDate The departure date of the voyage.
     * @param route         The route of the voyage.
     * @param spaceShuttle  The space shuttle of the voyage.
     * @return A new {@link Voyage}.
     */
    public static Voyage create(ZonedDateTime departureDate, Route route, SpaceShuttle spaceShuttle) throws VoyageException {
        if (departureDate == null) {
            throw new VoyageException(VoyageError.MISSING_DEPARTURE_DATE);
        } else if (route == null) {
            throw new VoyageException(VoyageError.MISSING_ROUTE);
        } else if (spaceShuttle == null) {
            throw new VoyageException(VoyageError.MISSING_SPACE_SHUTTLE);
        }

        Duration voyageDuration = VoyageDurationFactory.create(route, spaceShuttle).calculate();
        ZonedDateTime arrivalDate = departureDate.plus(voyageDuration);

        return new Voyage(UUID.randomUUID().toString(), departureDate, arrivalDate, VoyageStatus.SCHEDULED, route, spaceShuttle);
    }

    /**
     * Returns a {@link Voyage}.
     *
     * @param id            The ID of the voyage.
     * @param departureDate The departure date of the voyage.
     * @param arrivalDate   The arrival date of the voyage.
     * @param status        The status of the voyage.
     * @param route         The route of the voyage.
     * @param spaceShuttle  The space shuttle of the voyage.
     * @return A {@link Voyage}.
     * @throws DomainException When the arrival date is before the departure date of the voyage.
     */
    public static Voyage reconstruct(String id, ZonedDateTime departureDate, ZonedDateTime arrivalDate, VoyageStatus status, Route route, SpaceShuttle spaceShuttle) throws VoyageException {
        return new Voyage(id, departureDate, arrivalDate, status, route, spaceShuttle);
    }

    /**
     * The ID of the voyage.
     */
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private String id;

    /**
     * The departure date of the voyage.
     */
    @Basic(optional = false)
    @Column(name = "departure_date", nullable = false)
    private ZonedDateTime departureDate;

    /**
     * The arrival date of the voyage.
     */
    @Basic(optional = false)
    @Column(name = "arrival_date", nullable = false)
    private ZonedDateTime arrivalDate;

    /**
     * The status of the voyage.
     */
    @Basic(optional = false)
    @Column(nullable = false)
    private VoyageStatus status;

    /**
     * The route of the voyage.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "route_id", table = "voyages", nullable = false)
    private Route route;

    /**
     * The space shuttle of the voyage.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "space_shuttle_id", table = "voyages", nullable = false)
    private SpaceShuttle spaceShuttle;

    protected Voyage() {
    }

    protected Voyage(String id, ZonedDateTime departureDate, ZonedDateTime arrivalDate, VoyageStatus status, Route route, SpaceShuttle spaceShuttle) throws VoyageException {
        if (id == null) {
            throw new VoyageException(VoyageError.MISSING_ID);
        } else if (departureDate == null) {
            throw new VoyageException(VoyageError.MISSING_DEPARTURE_DATE);
        } else if (arrivalDate == null) {
            throw new VoyageException(VoyageError.MISSING_ARRIVAL_DATE);
        } else if (status == null) {
            throw new VoyageException(VoyageError.MISSING_STATUS);
        } else if (route == null) {
            throw new VoyageException(VoyageError.MISSING_ROUTE);
        } else if (spaceShuttle == null) {
            throw new VoyageException(VoyageError.MISSING_SPACE_SHUTTLE);
        }

        if (arrivalDate.isBefore(departureDate)) {
            throw new VoyageException(VoyageError.ARRIVAL_DATE_BEFORE_DEPARTURE_DATE);
        }

        this.id = id;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.status = status;
        this.route = route;
        this.spaceShuttle = spaceShuttle;
    }

    public String getId() {
        return id;
    }

    public ZonedDateTime getDepartureDate() {
        return departureDate;
    }

    public ZonedDateTime getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Returns the {@link Duration} between the departure date and arrival date of this voyage in seconds.
     *
     * @return A {@link Duration}.
     */
    public Duration getDuration() {
        return Duration.between(departureDate, arrivalDate);
    }

    public VoyageStatus getStatus() {
        return status;
    }

    public Route getRoute() {
        return route;
    }

    public SpaceShuttle getSpaceShuttle() {
        return spaceShuttle;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Voyage voyage = (Voyage) o;
        return Objects.equals(id, voyage.id) && Objects.equals(departureDate, voyage.departureDate) && Objects.equals(arrivalDate, voyage.arrivalDate) && status == voyage.status && Objects.equals(route, voyage.route) && Objects.equals(spaceShuttle, voyage.spaceShuttle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departureDate, arrivalDate, status, route, spaceShuttle);
    }
}
