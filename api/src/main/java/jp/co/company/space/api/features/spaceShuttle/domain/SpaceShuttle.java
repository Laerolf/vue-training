package jp.co.company.space.api.features.spaceShuttle.domain;

import jakarta.persistence.*;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;

import java.util.Objects;
import java.util.UUID;

/**
 * An entity class representing a space shuttle.
 */
@Entity(name = "SpaceShuttle")
@Table(name = "space_shuttles")
@Access(AccessType.FIELD)
@NamedQueries({ @NamedQuery(name = "selectAllSpaceShuttles", query = "SELECT s FROM SpaceShuttle s") })
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
    @Column(nullable = false, updatable = false, unique = true)
    private String id;

    /**
     * The name of the space shuttle.
     */
    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    /**
     * The model of the space shuttle.
     */
    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "model_id", nullable = false)
    private SpaceShuttleModel model;

    protected SpaceShuttle() {}

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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SpaceShuttle that = (SpaceShuttle) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, model);
    }
}
