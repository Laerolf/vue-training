package jp.co.company.space.api.features.spaceShuttle.domain;

import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;

/**
 * An entity class representing a space shuttle.
 */
@Entity(name = "SpaceShuttle")
@Table(name = "space_shuttles")
@Access(AccessType.FIELD)
@NamedQueries({ @NamedQuery(name = "selectAllSpaceShuttles", query = "SELECT s FROM SpaceShuttle s") })
@Schema(name = "SpaceShuttle", description = "A space shuttle entity.")
public class SpaceShuttle {

    /**
     * Creates a new {@link SpaceShuttle} entity.
     * 
     * @param name         The name of the space shuttle.
     * @param shuttleModel The model of the space shuttle.
     * @return A new {@link SpaceShuttle} entity.
     */
    public static SpaceShuttle create(String name, SpaceShuttleModel shuttleModel) {
        return new SpaceShuttle(UUID.randomUUID().toString(), name, shuttleModel);
    }

    /**
     * Recreates a {@link SpaceShuttle} entity.
     * 
     * @param id           The ID of the space shuttle.
     * @param name         The name of the space shuttle.
     * @param shuttleModel The model of the space shuttle.
     * @return A {@link SpaceShuttle} entity.
     */
    public static SpaceShuttle reconstruct(String id, String name, SpaceShuttleModel shuttleModel) {
        return new SpaceShuttle(id, name, shuttleModel);
    }

    /**
     * The ID of the space shuttle.
     */
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @Schema(description = "The ID of the space shuttle", required = true, example = "1")
    private String id;

    /**
     * The name of the space shuttle.
     */
    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    @Schema(description = "The name of the space shuttle", required = true, example = "From Hell with love")
    private String name;

    /**
     * The model of the space shuttle.
     */
    @ManyToOne
    @Schema(description = "The model of the space shuttle", required = true, implementation = SpaceShuttleModel.class)
    private SpaceShuttleModel model;

    protected SpaceShuttle() {
    }

    private SpaceShuttle(String id, String name, SpaceShuttleModel model) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the space shuttle is missing.");
        } else if (name == null) {
            throw new IllegalArgumentException("The name of the space shuttle is missing.");
        } else if (model == null) {
            throw new IllegalArgumentException("The model of the space shuttle is missing.");
        }

        this.id = id;
        this.name = name;
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SpaceShuttleModel getModel() {
        return model;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((model == null) ? 0 : model.hashCode());
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
        SpaceShuttle other = (SpaceShuttle) obj;
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
        if (model == null) {
            if (other.model != null) {
                return false;
            }
        } else if (!model.equals(other.model)) {
            return false;
        }
        return true;
    }

}
