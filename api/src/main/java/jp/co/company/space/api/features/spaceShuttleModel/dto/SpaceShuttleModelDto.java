package jp.co.company.space.api.features.spaceShuttleModel.dto;

import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceShuttleModel.exception.SpaceShuttleModelError;
import jp.co.company.space.api.features.spaceShuttleModel.exception.SpaceShuttleModelException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.Examples.*;

/**
 * A POJO representing a DTO of a {@link SpaceShuttleModel}.
 */
@Schema(name = "SpaceShuttleModel", description = "The details of a space shuttle model.")
public class SpaceShuttleModelDto {

    /**
     * Creates a {@link SpaceShuttleModelDto} based on a {@link SpaceShuttleModel}.
     *
     * @param spaceShuttleModel The base {@link SpaceShuttleModel}.
     * @return a {@link SpaceShuttleModelDto}.
     */
    public static SpaceShuttleModelDto create(SpaceShuttleModel spaceShuttleModel) throws SpaceShuttleModelException {
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

    protected SpaceShuttleModelDto() {
    }

    protected SpaceShuttleModelDto(String id, String name, int maxCapacity, long maxSpeed) {
        if (id == null) {
            throw new SpaceShuttleModelException(SpaceShuttleModelError.MISSING_ID);
        } else if (name == null) {
            throw new SpaceShuttleModelException(SpaceShuttleModelError.MISSING_NAME);
        }

        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.maxSpeed = maxSpeed;
    }
}
