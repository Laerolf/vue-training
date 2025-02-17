package jp.co.company.space.api.features.route.domain;

import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;

/**
 * A POJO representing a route.
 */
@Entity
@Table(name = "routes")
@Access(AccessType.FIELD)
@NamedQueries({ @NamedQuery(name = "selectAllRoutes", query = "SELECT r FROM Route r") })
@Schema(name = "Route", description = "A route between a space station and another space station.")
public class Route {

    /**
     * An example value for a route's destination.
     */
    private static final String ROUTE_DESTINATION_EXAMPLE = "{ \"code\": \"RHV\", \"country\": \"null\", \"id\": \"12\", \"location\": { \"id\": \"4\", \"latitude\": 0, \"longitude\": 13.1, \"name\": \"Mars\", \"radialDistance\": 1.5},\"name\": \"Red Haven - Mars\"}";

    /**
     * Creates a new {@link Route} instance.
     * 
     * @param origin       The origin of the route.
     * @param destination  The destination of the route.
     * @param shuttleModel The space shuttle model for the route.
     * @return a new {@link Route} instance.
     */
    public static Route create(SpaceStation origin, SpaceStation destination, SpaceShuttleModel shuttleModel) {
        return new Route(UUID.randomUUID().toString(), origin, destination, shuttleModel);
    }

    /**
     * Recreates a {@link Route} instance.
     * 
     * @param id           The ID of the route.
     * @param origin       The origin of the route.
     * @param destination  The destination of the route.
     * @param shuttleModel The space shuttle model for the route.
     * @return the recreated {@link Route} instance.
     */
    public static Route reconstruct(String id, SpaceStation origin, SpaceStation destination, SpaceShuttleModel shuttleModel) {
        return new Route(id, origin, destination, shuttleModel);
    }

    /**
     * The ID of the route.
     */
    @Id
    @Column(nullable = false, updatable = false, unique = true)
    @Schema(description = "The ID of the route.", required = true, example = "1")
    private String id;

    /**
     * The origin of the route.
     */
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "origin_id", table = "routes", nullable = false, unique = true)
    @Schema(description = "The origin location of the route.", required = true)
    private SpaceStation origin;

    /**
     * The destination of the route.
     */
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "destination_id", table = "routes", nullable = false, unique = true)
    @Schema(description = "The destination location of the route.", required = true, example = ROUTE_DESTINATION_EXAMPLE)
    private SpaceStation destination;

    /**
     * The space shuttle model to be used for the route.
     */
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "space_shuttle_model_id", table = "routes", nullable = false, unique = true)
    @Schema(description = "The space shuttle model to be used for the route.", required = true)
    private SpaceShuttleModel shuttleModel;

    protected Route() {}

    private Route(String id, SpaceStation origin, SpaceStation destination, SpaceShuttleModel shuttleModel) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the route is missing.");
        } else if (origin == null) {
            throw new IllegalArgumentException("The origin of the route is missing.");
        } else if (destination == null) {
            throw new IllegalArgumentException("The destination of the route is missing.");
        } else if (shuttleModel == null) {
            throw new IllegalArgumentException("The space shuttle model for the route is missing.");
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((origin == null) ? 0 : origin.hashCode());
        result = prime * result + ((destination == null) ? 0 : destination.hashCode());
        result = prime * result + ((shuttleModel == null) ? 0 : shuttleModel.hashCode());
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
        Route other = (Route) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (origin == null) {
            if (other.origin != null) {
                return false;
            }
        } else if (!origin.equals(other.origin)) {
            return false;
        }
        if (destination == null) {
            if (other.destination != null) {
                return false;
            }
        } else if (!destination.equals(other.destination)) {
            return false;
        }
        if (shuttleModel == null) {
            if (other.shuttleModel != null) {
                return false;
            }
        } else if (!shuttleModel.equals(other.shuttleModel)) {
            return false;
        }
        return true;
    }

}
