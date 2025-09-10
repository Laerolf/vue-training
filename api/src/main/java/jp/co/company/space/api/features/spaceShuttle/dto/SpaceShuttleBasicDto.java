package jp.co.company.space.api.features.spaceShuttle.dto;

import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttle.exception.SpaceShuttleError;
import jp.co.company.space.api.features.spaceShuttle.exception.SpaceShuttleException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.Examples.*;

/**
 * A POJO representing a brief DTO of a {@link SpaceShuttle}.
 */
@Schema(name = "BasicSpaceShuttle", description = "The brief details of a space shuttle.")
public class SpaceShuttleBasicDto {

    /**
     * Creates a new {@link SpaceShuttleBasicDto} based on a {@link SpaceShuttle}.
     *
     * @param spaceShuttle The base {@link SpaceShuttle}.
     * @return a new {@link SpaceShuttleBasicDto}.
     */
    public static SpaceShuttleBasicDto create(SpaceShuttle spaceShuttle) throws SpaceShuttleException {
        return new SpaceShuttleBasicDto(spaceShuttle.getId(), spaceShuttle.getName(), spaceShuttle.getModel().getId());
    }

    /**
     * The ID of the space shuttle.
     */
    @Schema(description = "The ID of the space shuttle", example = SPACE_SHUTTLE_ID_EXAMPLE)
    public String id;

    /**
     * The name of the space shuttle.
     */
    @Schema(description = "The name of the space shuttle", example = SPACE_SHUTTLE_NAME_EXAMPLE)
    public String name;

    /**
     * The model's ID of the space shuttle.
     */
    @Schema(description = "The model's ID of the space shuttle", example = SPACE_SHUTTLE_MODEL_ID_EXAMPLE)
    public String modelId;

    protected SpaceShuttleBasicDto() {
    }

    protected SpaceShuttleBasicDto(String id, String name, String modelId) throws SpaceShuttleException {
        if (id == null) {
            throw new SpaceShuttleException(SpaceShuttleError.MISSING_ID);
        } else if (name == null) {
            throw new SpaceShuttleException(SpaceShuttleError.MISSING_NAME);
        } else if (modelId == null) {
            throw new SpaceShuttleException(SpaceShuttleError.MISSING_MODEL_ID);
        }

        this.id = id;
        this.name = name;
        this.modelId = modelId;
    }
}
