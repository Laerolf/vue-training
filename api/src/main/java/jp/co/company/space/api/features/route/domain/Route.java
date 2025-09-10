package jp.co.company.space.api.features.route.domain;

import jakarta.persistence.*;
import jp.co.company.space.api.features.route.exception.RouteError;
import jp.co.company.space.api.features.route.exception.RouteException;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;

import java.util.Objects;
import java.util.UUID;

/**
 * A POJO representing a route.
 */
@Entity
@Table(name = "routes")
@Access(AccessType.FIELD)
@NamedQueries({@NamedQuery(name = "Route.selectAll", query = "SELECT r FROM Route r")})
public class Route {
    /**
     * Creates a new {@link Route}.
     *
     * @param origin       The origin of the route.
     * @param destination  The destination of the route.
     * @param shuttleModel The space shuttle model for the route.
     * @return a new {@link Route}.
     */
    public static Route create(SpaceStation origin, SpaceStation destination, SpaceShuttleModel shuttleModel) throws RouteException {
        return new Route(UUID.randomUUID().toString(), origin, destination, shuttleModel);
    }

    /**
     * Reconstructs a {@link Route}.
     *
     * @param id           The ID of the route.
     * @param origin       The origin of the route.
     * @param destination  The destination of the route.
     * @param shuttleModel The space shuttle model for the route.
     * @return A {@link Route}.
     */
    public static Route reconstruct(String id, SpaceStation origin, SpaceStation destination, SpaceShuttleModel shuttleModel) throws RouteException {
        return new Route(id, origin, destination, shuttleModel);
    }

    /**
     * The ID of the route.
     */
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    private String id;

    /**
     * The origin space station of the route.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "origin_id", table = "routes", nullable = false, unique = true)
    private SpaceStation origin;

    /**
     * The destination space station of the route.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "destination_id", table = "routes", nullable = false, unique = true)
    private SpaceStation destination;

    /**
     * The space shuttle model to be used for the route.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "space_shuttle_model_id", table = "routes", nullable = false, unique = true)
    private SpaceShuttleModel shuttleModel;

    protected Route() {
    }

    protected Route(String id, SpaceStation origin, SpaceStation destination, SpaceShuttleModel shuttleModel) throws RouteException {
        if (id == null) {
            throw new RouteException(RouteError.MISSING_ID);
        } else if (origin == null) {
            throw new RouteException(RouteError.MISSING_ORIGIN);
        } else if (destination == null) {
            throw new RouteException(RouteError.MISSING_DESTINATION);
        } else if (shuttleModel == null) {
            throw new RouteException(RouteError.MISSING_SPACE_SHUTTLE_MODEL);
        }

        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.shuttleModel = shuttleModel;
    }

    public String getId() {
        return id;
    }

    public SpaceStation getOrigin() {
        return origin;
    }

    public SpaceStation getDestination() {
        return destination;
    }

    public SpaceShuttleModel getShuttleModel() {
        return shuttleModel;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(id, route.id) && Objects.equals(origin, route.origin) && Objects.equals(destination, route.destination) && Objects.equals(shuttleModel, route.shuttleModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, origin, destination, shuttleModel);
    }
}
