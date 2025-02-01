package jp.co.company.space.api.features.spaceShuttleModel.domain;

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
 * An entity class representing a space shuttle model.
 */
@Entity(name = "SpaceShuttleModel")
@Table(name = "space_shuttle_models")
@Access(AccessType.FIELD)
@NamedQueries({ @NamedQuery(name = "selectAllSpaceShuttleModels", query = "SELECT s FROM SpaceShuttleModel s") })
@Schema(name = "SpaceShuttleModel", description = "A space shuttle model entity.")
public class SpaceShuttleModel {

    /**
     * Creates a new {@link SpaceShuttleModel} entity.
     * 
     * @param name        The name of the space shuttle model.
     * @param maxCapacity The maximum seating capacity of the space shuttle model.
     * @param maxSpeed    The maximum speed of the space shuttle model in
     *                    kilometers.
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
     * @param maxSpeed    The maximum speed of the space shuttle model in
     *                    kilometers.
     * @return a {@link SpaceShuttleModel} entity.
     */
    public static SpaceShuttleModel reconstruct(String id, String name, int maxCapacity, long maxSpeed) {
        return new SpaceShuttleModel(id, name, maxCapacity, maxSpeed);
    }

    /**
     * The ID of the space shuttle model.
     */
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @Schema(description = "The ID of the space shuttle model", required = true, example = "1")
    private String id;

    /**
     * The name of the space shuttle model.
     */
    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    @Schema(description = "The name of the space shuttle model", required = true, example = "Morningstar X-666")
    private String name;

    /**
     * The maximum seating capacity of a space shuttle model.
     */
    @Basic(optional = false)
    @Column(name = "max_capacity", nullable = false)
    @Schema(description = "The maximum seating capacity of the space shuttle model", required = true, example = "666")
    private int maxCapacity;

    /**
     * The maximum speed of a space shuttle model in kilometers per hour.
     */
    @Basic(optional = false)
    @Column(name = "max_speed", nullable = false)
    @Schema(description = "The maximum speed of the space shuttle model in kilometers per hour", required = true, example = "90000")
    private long maxSpeed;

    protected SpaceShuttleModel() {
    }

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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public long getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + maxCapacity;
        result = prime * result + (int) (maxSpeed ^ (maxSpeed >>> 32));
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
        SpaceShuttleModel other = (SpaceShuttleModel) obj;
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
        if (maxCapacity != other.maxCapacity) {
            return false;
        }
        if (maxSpeed != other.maxSpeed) {
            return false;
        }
        return true;
    }

}
