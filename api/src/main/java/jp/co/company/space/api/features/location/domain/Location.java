package jp.co.company.space.api.features.location.domain;

import jakarta.persistence.*;
import jp.co.company.space.api.features.location.exception.LocationError;
import jp.co.company.space.api.features.location.exception.LocationException;

import java.util.Objects;
import java.util.UUID;

/**
 * A POJO representing a location.
 */
@Entity
@Table(name = "locations")
@Access(AccessType.FIELD)
@NamedQueries({@NamedQuery(name = "Location.selectAll", query = "SELECT l FROM Location l")})
public class Location {

    /**
     * Creates a new {@link Location} instance.
     *
     * @param name           The name of the location.
     * @param latitude       The ecliptic latitude of the location.
     * @param longitude      The ecliptic longitude of the location.
     * @param radialDistance The radial distance of the location in astronomical units.
     * @return A new {@link Location}.
     */
    public static Location create(String name, double latitude, double longitude, double radialDistance) throws LocationException {
        return new Location(UUID.randomUUID().toString(), name, latitude, longitude, radialDistance);
    }

    /**
     * Recreates a {@link Location} instance.
     *
     * @param id             The ID of the location.
     * @param name           The name of the location.
     * @param latitude       The ecliptic latitude of the location.
     * @param longitude      The ecliptic longitude of the location.
     * @param radialDistance The radial distance of the location in astronomical units.
     * @return A new {@link Location}.
     */
    public static Location reconstruct(String id, String name, double latitude, double longitude, double radialDistance) throws LocationException {
        return new Location(id, name, latitude, longitude, radialDistance);
    }

    /**
     * The ID of the location.
     */
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id;

    /**
     * The name of the location.
     */
    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    /**
     * The ecliptic latitude of the location.
     */
    @Basic(optional = false)
    @Column(nullable = false)
    private double latitude;

    /**
     * The ecliptic longitude of the location.
     */
    @Basic(optional = false)
    @Column(nullable = false)
    private double longitude;

    /**
     * The radial distance of the location in astronomical units.
     */
    @Basic(optional = false)
    @Column(name = "radial_distance", nullable = false)
    private double radialDistance;

    protected Location() {
    }

    protected Location(String id, String name, double latitude, double longitude, double radialDistance) throws LocationException {
        if (id == null) {
            throw new LocationException(LocationError.MISSING_ID);
        } else if (name == null) {
            throw new LocationException(LocationError.MISSING_NAME);
        }

        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radialDistance = radialDistance;
    }

    /**
     * @return The ID of the location.
     */
    public String getId() {
        return id;
    }

    /**
     * @return The name of the location.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The ecliptic latitude of the location.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return The ecliptic longitude of the location.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @return The radial distance of the location in astronomical units.
     */
    public double getRadialDistance() {
        return radialDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(latitude, location.latitude) == 0 && Double.compare(longitude, location.longitude) == 0 && Double.compare(radialDistance, location.radialDistance) == 0 && Objects.equals(id, location.id) && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, latitude, longitude, radialDistance);
    }
}
