package jp.co.nova.gate.api.features.spaceShuttleModel.domain;

import jakarta.persistence.*;
import jp.co.nova.gate.api.features.spaceShuttleModel.exception.SpaceShuttleModelError;
import jp.co.nova.gate.api.features.spaceShuttleModel.exception.SpaceShuttleModelException;

import java.util.Objects;
import java.util.UUID;

/**
 * A class representing a space shuttle model.
 */
@Entity
@Table(name = "space_shuttle_models")
@Access(AccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = "SpaceShuttleModel.selectAll", query = "SELECT s FROM SpaceShuttleModel s")
})
public class SpaceShuttleModel {

    /**
     * Creates a new {@link SpaceShuttleModel}.
     *
     * @param name        The name of the space shuttle model.
     * @param maxCapacity The maximum seating capacity of the space shuttle model.
     * @param maxSpeed    The maximum speed of the space shuttle model in kilometers.
     * @return a new {@link SpaceShuttleModel}.
     */
    public static SpaceShuttleModel create(String name, int maxCapacity, long maxSpeed) throws SpaceShuttleModelException {
        return new SpaceShuttleModel(UUID.randomUUID().toString(), name, maxCapacity, maxSpeed);
    }

    /**
     * Reconstructs a {@link SpaceShuttleModel}.
     *
     * @param id          The ID of the space shuttle model.
     * @param name        The name of the space shuttle model.
     * @param maxCapacity The maximum seating capacity of the space shuttle model.
     * @param maxSpeed    The maximum speed of the space shuttle model in kilometers.
     * @return a {@link SpaceShuttleModel}.
     */
    public static SpaceShuttleModel reconstruct(String id, String name, int maxCapacity, long maxSpeed) throws SpaceShuttleModelException {
        return new SpaceShuttleModel(id, name, maxCapacity, maxSpeed);
    }

    /**
     * The ID of the space shuttle model.
     */
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id;

    /**
     * The name of the space shuttle model.
     */
    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    /**
     * The maximum seating capacity of a space shuttle model.
     */
    @Basic(optional = false)
    @Column(name = "max_capacity", nullable = false)
    private int maxCapacity;

    /**
     * The maximum speed of a space shuttle model in kilometers per hour.
     */
    @Basic(optional = false)
    @Column(name = "max_speed", nullable = false)
    private long maxSpeed;

    protected SpaceShuttleModel() {
    }

    private SpaceShuttleModel(String id, String name, int maxCapacity, long maxSpeed) throws SpaceShuttleModelException {
        if (id == null) {
            throw new SpaceShuttleModelException(SpaceShuttleModelError.MISSING_ID);
        } else if (name == null) {
            throw new SpaceShuttleModelException(SpaceShuttleModelError.MISSING_NAME);
        }

        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.maxSpeed = maxSpeed;
    }

    /**
     * @return The ID of the space shuttle model.
     */
    public String getId() {
        return id;
    }

    /**
     * @return The name of the space shuttle model.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The maximum seating capacity of a space shuttle model.
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * @return The maximum speed of a space shuttle model in kilometers per hour.
     */
    public long getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SpaceShuttleModel that = (SpaceShuttleModel) o;
        return maxCapacity == that.maxCapacity && maxSpeed == that.maxSpeed && Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, maxCapacity, maxSpeed);
    }
}
