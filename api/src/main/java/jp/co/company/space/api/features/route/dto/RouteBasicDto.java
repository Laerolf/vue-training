package jp.co.company.space.api.features.route.dto;

import jp.co.company.space.api.features.route.domain.Route;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * A POJO representing a brief DTO of a {@link Route} instance.
 */
@Schema(name = "RouteBasicDto", description = "The brief details of a route between a space station and another space station.")
public class RouteBasicDto {

    /**
     * An example value for a route's destination.
     */
    private static final String ROUTE_DESTINATION_ID_EXAMPLE = "12";

    /**
     * Creates a new {@link RouteBasicDto} instance based on a {@link Route} instance.
     *
     * @param route The base {@link Route} instance.
     * @return a new {@link RouteBasicDto} instance.
     */
    public static RouteBasicDto create(Route route) {
        if (route == null) {
            throw new IllegalArgumentException("The base route is missing.");
        }

        return new RouteBasicDto(route.getId(), route.getOrigin().getId(), route.getDestination().getId(), route.getShuttleModel().getId());
    }

    /**
     * The ID of the route.
     */
    @Schema(description = "The ID of the route.", required = true, example = "1")
    public String id;

    /**
     * The origin space station's ID of the route.
     */
    @Schema(description = "The origin space station's ID of the route.", required = true)
    public String originId;

    /**
     * The destination space station's ID of the route.
     */
    @Schema(description = "The destination space station's ID of the route.", required = true, example = ROUTE_DESTINATION_ID_EXAMPLE)
    public String destinationId;

    /**
     * The space shuttle model's to be used for the route.
     */
    @Schema(description = "The space shuttle model's ID to be used for the route.", required = true)
    public String shuttleModelId;

    protected RouteBasicDto() {
    }

    protected RouteBasicDto(String id, String originId, String destinationId, String shuttleModelId) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the route is missing.");
        } else if (originId == null) {
            throw new IllegalArgumentException("The originId space station's ID of the route is missing.");
        } else if (destinationId == null) {
            throw new IllegalArgumentException("The destination space station's ID of the route is missing.");
        } else if (shuttleModelId == null) {
            throw new IllegalArgumentException("The space shuttle model's ID for the route is missing.");
        }

        this.id = id;
        this.originId = originId;
        this.destinationId = destinationId;
        this.shuttleModelId = shuttleModelId;
    }
}
