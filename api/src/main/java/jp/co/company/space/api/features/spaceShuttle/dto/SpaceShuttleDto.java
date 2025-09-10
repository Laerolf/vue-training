package jp.co.company.space.api.features.spaceShuttle.dto;

import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttleModel.dto.SpaceShuttleModelDto;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.Examples.SPACE_SHUTTLE_ID_EXAMPLE;
import static jp.co.company.space.api.shared.openApi.Examples.SPACE_SHUTTLE_NAME_EXAMPLE;

/**
 * A POJO representing a DTO of a {@link SpaceShuttle}.
 */
@Schema(name = "SpaceShuttle", description = "The details of a space shuttle.")
public class SpaceShuttleDto {

    /**
     * Creates a new {@link SpaceShuttleDto} based on a {@link SpaceShuttle}.
     *
     * @param spaceShuttle The base {@link SpaceShuttle}.
     * @return a new {@link SpaceShuttleDto}.
     */
    public static SpaceShuttleDto create(SpaceShuttle spaceShuttle) {
        return new SpaceShuttleDto(spaceShuttle.getId(), spaceShuttle.getName(), SpaceShuttleModelDto.create(spaceShuttle.getModel()));
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
     * The model of the space shuttle.
     */
    @Schema(description = "The model of the space shuttle")
    public SpaceShuttleModelDto model;

    protected SpaceShuttleDto() {}

    protected SpaceShuttleDto(String id, String name, SpaceShuttleModelDto model) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the space shuttle is missing.");
        } else if (name == null) {
            throw new IllegalArgumentException("The name of the space shuttle is missing.");
        } else if (model == null) {
            throw new IllegalArgumentException("The model of the space shuttle is missing.");
        }

        this.id = id;
        this.name = name;
        this.model = model;
    }
}
