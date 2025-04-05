package jp.co.company.space.api.features.voyage.domain;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.UUID;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.shared.DomainException;

/**
 * A POJO representing a voyage.
 */
@Entity(name = "Voyage")
@Table(name = "voyages")
@Access(AccessType.FIELD)
@NamedQueries({ @NamedQuery(name = "getAllVoyages", query = "SELECT v FROM Voyage v"),
        @NamedQuery(name = "getAllVoyagesFromOriginId", query = "SELECT v FROM Voyage v WHERE v.route.origin.id = :originId"),
        @NamedQuery(name = "getAllVoyagesToDestinationId", query = "SELECT v FROM Voyage v WHERE v.route.destination.id = :destinationId"),
        @NamedQuery(name = "getAllVoyagesFromOriginIdToDestinationId", query = "SELECT v FROM Voyage v WHERE v.route.origin.id = :originId AND v.route.destination.id = :destinationId") })
public class Voyage {

    /**
     * Returns a new {@link Voyage} instance.
     * 
     * @param departureDate The departure date of the voyage.
     * @param route         The route of the voyage.
     * @param spaceShuttle  The space shuttle of the voyage.
     * @return A new {@link Voyage} instance.
     */
    public static Voyage create(ZonedDateTime departureDate, Route route, SpaceShuttle spaceShuttle) {
        if (departureDate == null) {
            throw new IllegalArgumentException("The departure date of the voyage is missing.");
        } else if (route == null) {
            throw new IllegalArgumentException("The route of the voyage is missing.");
        } else if (spaceShuttle == null) {
            throw new IllegalArgumentException("The space shuttle of the voyage is missing.");
        }

        Duration voyageDuration = VoyageDurationFactory.create(route, spaceShuttle).calculate();
        ZonedDateTime arrivalDate = departureDate.plus(voyageDuration);

        return new Voyage(UUID.randomUUID().toString(), departureDate, arrivalDate, VoyageStatus.SCHEDULED, route, spaceShuttle);
    }

    /**
     * Returns a {@link Voyage} instance.
     * 
     * @param id            The ID of the voyage.
     * @param departureDate The departure date of the voyage.
     * @param arrivalDate   The arrival date of the voyage.
     * @param status        The status of the voyage.
     * @param route         The route of the voyage.
     * @param spaceShuttle  The space shuttle of the voyage.
     * @return A {@link Voyage} instance.
     * @throws DomainException When the arrival date is before the departure date of the voyage.
     */
    public static Voyage reconstruct(String id, ZonedDateTime departureDate, ZonedDateTime arrivalDate, VoyageStatus status, Route route, SpaceShuttle spaceShuttle) {
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
    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "route_id", table = "voyages", nullable = false)
    private Route route;

    /**
     * The space shuttle of the voyage.
     */
    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "space_shuttle_id", table = "voyages", nullable = false)
    private SpaceShuttle spaceShuttle;

    protected Voyage() {}

    public Voyage(String id, ZonedDateTime departureDate, ZonedDateTime arrivalDate, VoyageStatus status, Route route, SpaceShuttle spaceShuttle) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the voyage is missing.");
        } else if (departureDate == null) {
            throw new IllegalArgumentException("The departure date of the voyage is missing.");
        } else if (arrivalDate == null) {
            throw new IllegalArgumentException("The arrival date of the voyage is missing.");
        } else if (status == null) {
            throw new IllegalArgumentException("The status of the voyage is missing.");
        } else if (route == null) {
            throw new IllegalArgumentException("The route of the voyage is missing.");
        } else if (spaceShuttle == null) {
            throw new IllegalArgumentException("The space shuttle of the voyage is missing.");
        }

        if (arrivalDate.isBefore(departureDate)) {
            throw new DomainException("The arrival date of a voyage cannot be before its departure date.");
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((departureDate == null) ? 0 : departureDate.hashCode());
        result = prime * result + ((arrivalDate == null) ? 0 : arrivalDate.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((route == null) ? 0 : route.hashCode());
        result = prime * result + ((spaceShuttle == null) ? 0 : spaceShuttle.hashCode());
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
        Voyage other = (Voyage) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (departureDate == null) {
            if (other.departureDate != null) {
                return false;
            }
        } else if (!departureDate.equals(other.departureDate)) {
            return false;
        }
        if (arrivalDate == null) {
            if (other.arrivalDate != null) {
                return false;
            }
        } else if (!arrivalDate.equals(other.arrivalDate)) {
            return false;
        }
        if (status != other.status) {
            return false;
        }
        if (route == null) {
            if (other.route != null) {
                return false;
            }
        } else if (!route.equals(other.route)) {
            return false;
        }
        if (spaceShuttle == null) {
            return other.spaceShuttle == null;
        } else {
            return spaceShuttle.equals(other.spaceShuttle);
        }
    }

}
