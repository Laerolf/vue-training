package jp.co.company.space.api.features.spaceShuttleModel.domain;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

/**
 * An entity class representing a space shuttle model.
 */
@Entity(name = "SpaceShuttleModel")
@Table(name = "space_shuttle_models")
@Access(AccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = "selectAllSpaceShuttleModels", query = "SELECT s FROM SpaceShuttleModel s"),
        @NamedQuery(name = "selectAllSpaceShuttlesByModelId", query = "SELECT s FROM SpaceShuttleModel m JOIN SpaceShuttle s ON s.model = m WHERE m.id = :id")
})
public class SpaceShuttleModel {

    /**
     * Creates a new {@link SpaceShuttleModel} entity.
     *
     * @param name        The name of the space shuttle model.
     * @param maxCapacity The maximum seating capacity of the space shuttle model.
     * @param maxSpeed    The maximum speed of the space shuttle model in kilometers.
     * @return a new {@link SpaceShuttleModel} entity.
     */
    public static SpaceShuttleModel create(String name, int maxCapacity, long maxSpeed) {
        return new SpaceShuttleModel(UUID.randomUUID().toString(), name, maxCapacity, maxSpeed);
    }

    /**
     * Recreates a {@link SpaceShuttleModel} entity.
     *
     * @param id          The ID of the space shuttle model.
     * @param name        The name of the space shuttle model.
     * @param maxCapacity The maximum seating capacity of the space shuttle model.
     * @param maxSpeed    The maximum speed of the space shuttle model in kilometers.
     * @return a {@link SpaceShuttleModel} entity.
     */
    public static SpaceShuttleModel reconstruct(String id, String name, int maxCapacity, long maxSpeed) {
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

    protected SpaceShuttleModel() {}

    private SpaceShuttleModel(String id, String name, int maxCapacity, long maxSpeed) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the space shuttle model is missing.");
        } else if (name == null) {
            throw new IllegalArgumentException("The name of the space shuttle model is missing.");
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
