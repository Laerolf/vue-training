package jp.co.company.space.api.features.spaceStation.domain;

import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

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
import jp.co.company.space.api.features.location.domain.Location;

/**
 * A POJO representing a space station.
 */
@Entity(name = "SpaceStation")
@Table(name = "space_stations")
@Access(AccessType.FIELD)
@NamedQueries({ @NamedQuery(name = "selectAllSpaceStations", query = "SELECT s FROM SpaceStation s"), })
@Schema(name = "SpaceStation", description = "A space station.")
public class SpaceStation {

    /**
     * Creates a new {@link SpaceStation} entity.
     * 
     * @param name     The name of the space station.
     * @param code     The code of the space station.
     * @param country  The country of the space station.
     * @param location The location of the space station.
     * @return A new {@link SpaceStation} entity.
     */
    public static SpaceStation create(String name, String code, String country, Location location) {
        return new SpaceStation(UUID.randomUUID().toString(), name, code, country, location);
    }

    /**
     * Recreates a {@link SpaceStation} entity.
     * 
     * @param id       The ID of the space station.
     * @param name     The name of the space station.
     * @param code     The code of the space station.
     * @param country  The country of the space station.
     * @param location The location of the space station.
     * @return A {@link SpaceStation} entity.
     */
    public static SpaceStation reconstruct(String id, String name, String code, String country, Location location) {
        return new SpaceStation(id, name, code, country, location);
    }

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    @Schema(description = "The ID of the space station.", required = true, example = "1")
    private String id;

    @Basic(optional = false)
    @Column(nullable = false)
    @Schema(description = "The name of the space station.", required = true, example = "Tanegashima Space Center")
    private String name;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    @Schema(description = "The code of the space station.", required = true, example = "TNS")
    private String code;

    @Basic(optional = false)
    @Column(nullable = true)
    @Schema(description = "The country of the space station.", required = true, example = "Japan")
    private String country;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "location_id", table = "space_stations", nullable = false, unique = true)
    @Schema(description = "The location of the space station.", required = true, implementation = Location.class)
    private Location location;

    protected SpaceStation() {}

    private SpaceStation(String id, String name, String code, String country, Location location) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the space station is missing.");
        } else if (name == null) {
            throw new IllegalArgumentException("The name of the space station is missing.");
        } else if (code == null) {
            throw new IllegalArgumentException("The code of the space station is missing.");
        } else if (location == null) {
            throw new IllegalArgumentException("The location of the space station is missing.");
        }

        this.id = id;
        this.name = name;
        this.code = code;
        this.country = country;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCountry() {
        return country;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
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
        SpaceStation other = (SpaceStation) obj;
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
        if (code == null) {
            if (other.code != null) {
                return false;
            }
        } else if (!code.equals(other.code)) {
            return false;
        }
        if (country == null) {
            if (other.country != null) {
                return false;
            }
        } else if (!country.equals(other.country)) {
            return false;
        }
        if (location == null) {
            if (other.location != null) {
                return false;
            }
        } else if (!location.equals(other.location)) {
            return false;
        }
        return true;
    }

}
