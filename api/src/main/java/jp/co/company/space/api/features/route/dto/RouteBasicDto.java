package jp.co.company.space.api.features.route.dto;

import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.route.exception.RouteError;
import jp.co.company.space.api.features.route.exception.RouteException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.Examples.*;

/**
 * A POJO representing a brief DTO of a {@link Route}.
 */
@Schema(name = "BasicRoute", description = "The brief details of a route between a space station and another space station.")
public class RouteBasicDto {

    /**
     * Creates a new {@link RouteBasicDto} based on a {@link Route}.
     *
     * @param route The base {@link Route}.
     * @return a new {@link RouteBasicDto}.
     */
    public static RouteBasicDto create(Route route) throws RouteException {
        if (route == null) {
            throw new RouteException(RouteError.MISSING);
        }

        return new RouteBasicDto(route.getId(), route.getOrigin().getId(), route.getDestination().getId(), route.getShuttleModel().getId());
    }

    /**
     * The ID of the route.
     */
    @Schema(description = "The ID of the route.", example = ROUTE_ID_EXAMPLE)
    public String id;

    /**
     * The origin space station's ID of the route.
     */
    @Schema(description = "The origin space station's ID of the route.", example = ROUTE_ORIGIN_ID_EXAMPLE)
    public String originId;

    /**
     * The destination space station's ID of the route.
     */
    @Schema(description = "The destination space station's ID of the route.", example = ROUTE_DESTINATION_ID_EXAMPLE)
    public String destinationId;

    /**
     * The space shuttle model's to be used for the route.
     */
    @Schema(description = "The space shuttle model's ID to be used for the route.", example = SPACE_SHUTTLE_MODEL_ID_EXAMPLE)
    public String shuttleModelId;

    protected RouteBasicDto() {
    }

    protected RouteBasicDto(String id, String originId, String destinationId, String shuttleModelId) throws RouteException {
        if (id == null) {
            throw new RouteException(RouteError.MISSING_ID);
        } else if (originId == null) {
            throw new RouteException(RouteError.MISSING_ORIGIN_ID);
        } else if (destinationId == null) {
            throw new RouteException(RouteError.MISSING_DESTINATION_ID);
        } else if (shuttleModelId == null) {
            throw new RouteException(RouteError.MISSING_SPACE_SHUTTLE_MODEL_ID);
        }

        this.id = id;
        this.originId = originId;
        this.destinationId = destinationId;
        this.shuttleModelId = shuttleModelId;
    }
}
