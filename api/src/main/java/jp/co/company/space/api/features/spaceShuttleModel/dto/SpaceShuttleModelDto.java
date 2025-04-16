package jp.co.company.space.api.features.spaceShuttleModel.dto;

import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * A POJO representing a DTO of a {@link SpaceShuttleModel} instance.
 */
@Schema(description = "The details of a space shuttle model.")
public class SpaceShuttleModelDto {

    /**
     * Creates a {@link SpaceShuttleModelDto} entity based on a {@link SpaceShuttleModel} instance.
     *
     * @param spaceShuttleModel The base {@link SpaceShuttleModel} instance.
     * @return a {@link SpaceShuttleModelDto} entity.
     */
    public static SpaceShuttleModelDto create(SpaceShuttleModel spaceShuttleModel) {
        return new SpaceShuttleModelDto(spaceShuttleModel.getId(), spaceShuttleModel.getName(), spaceShuttleModel.getMaxCapacity(), spaceShuttleModel.getMaxSpeed());
    }

    /**
     * The ID of the space shuttle model.
     */
    @Schema(description = "The ID of the space shuttle model", required = true, example = "1")
    public String id;

    /**
     * The name of the space shuttle model.
     */
    @Schema(description = "The name of the space shuttle model", required = true, example = "Morningstar X-666")
    public String name;

    /**
     * The maximum seating capacity of a space shuttle model.
     */
    @Schema(description = "The maximum seating capacity of the space shuttle model", required = true, example = "666")
    public int maxCapacity;

    /**
     * The maximum speed of a space shuttle model in kilometers per hour.
     */
    @Schema(description = "The maximum speed of the space shuttle model in kilometers per hour", required = true, example = "90000")
    public long maxSpeed;

    protected SpaceShuttleModelDto() {}

    protected SpaceShuttleModelDto(String id, String name, int maxCapacity, long maxSpeed) {
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
}
