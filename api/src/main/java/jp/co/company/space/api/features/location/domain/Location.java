package jp.co.company.space.api.features.location.domain;

import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 * A POJO representing a location.
 */
@Entity(name = "Location")
@Table(name = "locations")
@Access(AccessType.FIELD)
@NamedQueries({ @NamedQuery(name = "selectAllLocations", query = "select l from Location l") })
@Schema(name = "Location", description = "A location.")
public class Location {

    /**
     * Creates a new {@link Location} instance.
     * 
     * @param name           The name of the location.
     * @param latitude       The galactic latitude of the location.
     * @param longitude      The galactic longitude of the location.
     * @param radialDistance The radial distance of the location
     * @return A new {@link Location}.
     */
    public static Location create(String name, double latitude, double longitude, double radialDistance) {
        return new Location(UUID.randomUUID().toString(), name, latitude, longitude, radialDistance);
    }

    /**
     * Recreates a {@link Location} instance.
     * 
     * @param id             The ID of the location.
     * @param name           The name of the location.
     * @param latitude       The galactic latitude of the location.
     * @param longitude      The galactic longitude of the location.
     * @param radialDistance The radial distance of the location
     * @return A new {@link Location}.
     */
    public static Location reconstruct(String id, String name, double latitude, double longitude,
            double radialDistance) {
        return new Location(id, name, latitude, longitude, radialDistance);
    }

    /**
     * The ID of the location.
     */
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @Schema(description = "The ID of the location.", required = true, example = "1")
    private String id;

    /**
     * The name of the location.
     */
    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    @Schema(description = "The name of the location.", required = true, example = "Earth")
    private String name;

    /**
     * The galatic latitude of the location.
     */
    @Basic(optional = false)
    @Column(name = "latitude", nullable = false)
    @Schema(description = "The galatic latitude of the location.", required = true, example = "0.0")
    private double latitude;

    /**
     * The galatic longitude of the location.
     */
    @Basic(optional = false)
    @Column(name = "longitude", nullable = false)
    @Schema(description = "The galatic longitude of the location.", required = true, example = "0.0")
    private double longitude;

    /**
     * The galatic radial distance of the location.
     */
    @Basic(optional = false)
    @Column(name = "radialDistance", nullable = false)
    @Schema(description = "The galatic radial distance of the location.", required = true, example = "27000")
    private double radialDistance;

    protected Location() {}

    private Location(String id, String name, double latitude, double longitude, double radialDistance) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the location is missing.");
        } else if (name == null) {
            throw new IllegalArgumentException("The name of the location is missing.");
        }

        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radialDistance = radialDistance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getRadialDistance() {
        return radialDistance;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(radialDistance);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        Location other = (Location) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        if (Double.doubleToLongBits(radialDistance) != Double.doubleToLongBits(other.radialDistance)) {
            return false;
        }
        return true;
    }

}
