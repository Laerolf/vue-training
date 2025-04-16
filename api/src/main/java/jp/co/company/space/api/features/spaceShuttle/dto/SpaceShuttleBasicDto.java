package jp.co.company.space.api.features.spaceShuttle.dto;

import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * A POJO representing a brief DTO of a {@link SpaceShuttle} instance.
 */
@Schema(description = "The brief details of a space shuttle.")
public class SpaceShuttleBasicDto {

    /**
     * Creates a new {@link SpaceShuttleBasicDto} instance based on a {@link SpaceShuttle} instance.
     *
     * @param spaceShuttle The base {@link SpaceShuttle} instance.
     * @return a new {@link SpaceShuttleBasicDto} instance.
     */
    public static SpaceShuttleBasicDto create(SpaceShuttle spaceShuttle) {
        return new SpaceShuttleBasicDto(spaceShuttle.getId(), spaceShuttle.getName(), spaceShuttle.getModel().getId());
    }

    /**
     * The ID of the space shuttle.
     */
    @Schema(description = "The ID of the space shuttle", required = true, example = "1")
    public String id;

    /**
     * The name of the space shuttle.
     */
    @Schema(description = "The name of the space shuttle", required = true, example = "From Hell with love")
    public String name;

    /**
     * The model's ID of the space shuttle.
     */
    @Schema(description = "The model's ID of the space shuttle", required = true)
    public String modelId;

    protected SpaceShuttleBasicDto() {}

    protected SpaceShuttleBasicDto(String id, String name, String modelId) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the space shuttle is missing.");
        } else if (name == null) {
            throw new IllegalArgumentException("The name of the space shuttle is missing.");
        } else if (modelId == null) {
            throw new IllegalArgumentException("The model's ID of the space shuttle is missing.");
        }

        this.id = id;
        this.name = name;
        this.modelId = modelId;
    }
}
