package jp.co.nova.gate.api.features.route.dto;

import jp.co.nova.gate.api.features.route.domain.Route;
import jp.co.nova.gate.api.features.spaceShuttleModel.dto.SpaceShuttleModelDto;
import jp.co.nova.gate.api.features.spaceStation.dto.SpaceStationDto;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Objects;

import static jp.co.nova.gate.api.shared.openApi.Examples.ROUTE_DESTINATION_EXAMPLE;
import static jp.co.nova.gate.api.shared.openApi.Examples.ROUTE_ID_EXAMPLE;

/**
 * A POJO representing a DTO of a {@link Route}.
 */
@Schema(name = "Route", description = "The details of a route between a space station and another space station.")
public class RouteDto {
    /**
     * Creates a new {@link RouteDto} based on a {@link Route}.
     *
     * @param route The base {@link Route}.
     * @return a new {@link RouteDto}.
     */
    public static RouteDto create(Route route) {
        if (route == null) {
            throw new IllegalArgumentException("The base route is missing.");
        }

        return new RouteDto(route.getId(), SpaceStationDto.create(route.getOrigin()), SpaceStationDto.create(route.getDestination()), SpaceShuttleModelDto.create(route.getShuttleModel()));
    }

    /**
     * The ID of the route.
     */
    @Schema(description = "The ID of the route.", example = ROUTE_ID_EXAMPLE)
    public String id;

    /**
     * The origin of the route.
     */
    @Schema(description = "The origin space station of the route.")
    public SpaceStationDto origin;

    /**
     * The destination of the route.
     */
    @Schema(description = "The destination space station of the route.", example = ROUTE_DESTINATION_EXAMPLE)
    public SpaceStationDto destination;

    /**
     * The space shuttle model to be used for the route.
     */
    @Schema(description = "The space shuttle model to be used for the route.")
    public SpaceShuttleModelDto shuttleModel;

    protected RouteDto() {}

    protected RouteDto(String id, SpaceStationDto origin, SpaceStationDto destination, SpaceShuttleModelDto shuttleModel) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RouteDto routeDto = (RouteDto) o;
        return Objects.equals(id, routeDto.id) && Objects.equals(origin, routeDto.origin) && Objects.equals(destination, routeDto.destination) && Objects.equals(shuttleModel, routeDto.shuttleModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, origin, destination, shuttleModel);
    }
}
