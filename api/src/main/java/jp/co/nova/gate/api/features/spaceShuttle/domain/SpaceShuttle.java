package jp.co.nova.gate.api.features.spaceShuttle.domain;

import jakarta.persistence.*;
import jp.co.nova.gate.api.features.spaceShuttle.exception.SpaceShuttleError;
import jp.co.nova.gate.api.features.spaceShuttle.exception.SpaceShuttleException;
import jp.co.nova.gate.api.features.spaceShuttleModel.domain.SpaceShuttleModel;

import java.util.Objects;
import java.util.UUID;

/**
 * A class representing a space shuttle.
 */
@Entity
@Table(name = "space_shuttles")
@Access(AccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = "SpaceShuttle.selectAll", query = "SELECT s FROM SpaceShuttle s"),
        @NamedQuery(name = "SpaceShuttle.selectAllByModelId", query = "SELECT s FROM SpaceShuttleModel m JOIN SpaceShuttle s ON s.model = m WHERE m.id = :id")
})
public class SpaceShuttle {

    /**
     * Creates a new {@link SpaceShuttle}.
     *
     * @param name         The name of the space shuttle.
     * @param shuttleModel The model of the space shuttle.
     * @return A new {@link SpaceShuttle}.
     */
    public static SpaceShuttle create(String name, SpaceShuttleModel shuttleModel) throws SpaceShuttleException {
        return new SpaceShuttle(UUID.randomUUID().toString(), name, shuttleModel);
    }

    /**
     * Reconstructs a {@link SpaceShuttle}.
     *
     * @param id           The ID of the space shuttle.
     * @param name         The name of the space shuttle.
     * @param shuttleModel The model of the space shuttle.
     * @return A {@link SpaceShuttle}.
     */
    public static SpaceShuttle reconstruct(String id, String name, SpaceShuttleModel shuttleModel) throws SpaceShuttleException {
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
    @ManyToOne(optional = false)
    @JoinColumn(name = "model_id", nullable = false)
    private SpaceShuttleModel model;

    protected SpaceShuttle() {
    }

    private SpaceShuttle(String id, String name, SpaceShuttleModel model) throws SpaceShuttleException {
        if (id == null) {
            throw new SpaceShuttleException(SpaceShuttleError.MISSING_ID);
        } else if (name == null) {
            throw new SpaceShuttleException(SpaceShuttleError.MISSING_NAME);
        } else if (model == null) {
            throw new SpaceShuttleException(SpaceShuttleError.MISSING_MODEL);
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

    /**
     * Gets the {@link SpaceShuttleLayout} for this {@link SpaceShuttle}.
     *
     * @return A {@link SpaceShuttleLayout}.
     */
    public SpaceShuttleLayout getLayout() {
        return new SpaceShuttleLayoutFactory(model).create();
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
