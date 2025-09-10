package jp.co.company.space.api.features.spaceStation.domain;

import jakarta.persistence.*;
import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.spaceStation.exception.SpaceStationError;
import jp.co.company.space.api.features.spaceStation.exception.SpaceStationException;

import java.util.Objects;
import java.util.UUID;

/**
 * A POJO representing a space station.
 */
@Entity
@Table(name = "space_stations")
@Access(AccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = "SpaceStation.selectAll", query = "SELECT s FROM SpaceStation s"),
        @NamedQuery(name = "SpaceStation.selectById", query = "SELECT s FROM SpaceStation s LEFT JOIN FETCH s.location WHERE s.id = :id")
})
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
    public static SpaceStation create(String name, String code, String country, Location location) throws SpaceStationException {
        return new SpaceStation(UUID.randomUUID().toString(), name, code, country, location);
    }

    /**
     * Reconstructs a {@link SpaceStation} entity.
     *
     * @param id       The ID of the space station.
     * @param name     The name of the space station.
     * @param code     The code of the space station.
     * @param country  The country of the space station.
     * @param location The location of the space station.
     * @return A {@link SpaceStation} entity.
     */
    public static SpaceStation reconstruct(String id, String name, String code, String country, Location location) throws SpaceStationException {
        return new SpaceStation(id, name, code, country, location);
    }

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String code;

    @Basic(optional = false)
    @Column
    private String country;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id", table = "space_stations", nullable = false, unique = true)
    private Location location;

    protected SpaceStation() {
    }

    private SpaceStation(String id, String name, String code, String country, Location location) throws SpaceStationException {
        if (id == null) {
            throw new SpaceStationException(SpaceStationError.MISSING_ID);
        } else if (name == null) {
            throw new SpaceStationException(SpaceStationError.MISSING_NAME);
        } else if (code == null) {
            throw new SpaceStationException(SpaceStationError.MISSING_CODE);
        } else if (location == null) {
            throw new SpaceStationException(SpaceStationError.MISSING_LOCATION);
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SpaceStation that = (SpaceStation) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(code, that.code) && Objects.equals(country, that.country) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, country, location);
    }
}
