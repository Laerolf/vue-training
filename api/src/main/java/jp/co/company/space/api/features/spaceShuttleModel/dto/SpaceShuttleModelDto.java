package jp.co.company.space.api.features.spaceShuttleModel.dto;

import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.examples.*;

/**
 * A POJO representing a DTO of a {@link SpaceShuttleModel} instance.
 */
@Schema(name = "SpaceShuttleModel", description = "The details of a space shuttle model.")
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
    @Schema(description = "The ID of the space shuttle model", example = SPACE_SHUTTLE_MODEL_ID_EXAMPLE)
    public String id;

    /**
     * The name of the space shuttle model.
     */
    @Schema(description = "The name of the space shuttle model", example = SPACE_SHUTTLE_MODEL_NAME_EXAMPLE)
    public String name;

    /**
     * The maximum seating capacity of a space shuttle model.
     */
    @Schema(description = "The maximum seating capacity of the space shuttle model", example = SPACE_SHUTTLE_MODEL_MAX_CAPACITY_EXAMPLE)
    public int maxCapacity;

    /**
     * The maximum speed of a space shuttle model in kilometers per hour.
     */
    @Schema(description = "The maximum speed of the space shuttle model in kilometers per hour", example = SPACE_SHUTTLE_MODEL_MAX_SPEED_EXAMPLE)
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
