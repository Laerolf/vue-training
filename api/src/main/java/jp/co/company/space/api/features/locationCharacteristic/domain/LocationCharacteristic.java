package jp.co.company.space.api.features.locationCharacteristic.domain;

import jakarta.persistence.*;
import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.locationCharacteristic.exception.LocationCharacteristicError;
import jp.co.company.space.api.features.locationCharacteristic.exception.LocationCharacteristicException;

import java.util.Objects;
import java.util.UUID;

/**
 * A POJO representing a {@link Location}'s characteristic.
 */
@Entity
@Table(name = "location_characteristics")
@Access(AccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = "LocationCharacteristic.selectById", query = "SELECT lc FROM LocationCharacteristic lc WHERE lc.id = :id"),
})
public class LocationCharacteristic {

    /**
     * Creates a new {@link LocationCharacteristic} instance.
     *
     * @param characteristic The characteristic of the location characteristic.
     * @return A new {@link LocationCharacteristic}.
     */
    public static LocationCharacteristic create(PlanetCharacteristic characteristic) throws LocationCharacteristicException {
        return new LocationCharacteristic(UUID.randomUUID().toString(), characteristic);
    }

    /**
     * Recreates a {@link LocationCharacteristic} instance.
     *
     * @param id             The ID of the location characteristic.
     * @param characteristic The characteristic of the location characteristic.
     * @return A {@link LocationCharacteristic}.
     */
    public static LocationCharacteristic recreate(String id, PlanetCharacteristic characteristic) throws LocationCharacteristicException {
        return new LocationCharacteristic(id, characteristic);
    }

    /**
     * The ID of the location's characteristic.
     */
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id;

    /**
     * The location's characteristic.
     */
    @Basic(optional = false)
    @Column(nullable = false)
    private PlanetCharacteristic characteristic;

    @ManyToOne
    @JoinColumn(name = "location_id", table = "location_characteristics")
    private Location location;

    protected LocationCharacteristic() {
    }

    private LocationCharacteristic(String id, PlanetCharacteristic characteristic) throws LocationCharacteristicException {
        if (id == null) {
            throw new LocationCharacteristicException(LocationCharacteristicError.MISSING_ID);
        } else if (characteristic == null) {
            throw new LocationCharacteristicException(LocationCharacteristicError.MISSING_CHARACTERISTIC);
        }

        this.id = id;
        this.characteristic = characteristic;
    }

    /**
     * Assigns a {@link Location} to this characteristic.
     *
     * @param location The location to assign.
     */
    public void assignLocation(Location location) {
        this.location = location;
    }

    /**
     * @return The ID of the location characteristic.
     */
    public String getId() {
        return id;
    }

    /**
     * @return The characteristic of the location characteristic.
     */
    public PlanetCharacteristic getCharacteristic() {
        return characteristic;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return The location of the location characteristic.
     */
    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LocationCharacteristic that = (LocationCharacteristic) o;
        return Objects.equals(id, that.id) && characteristic == that.characteristic;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, characteristic);
    }
}
