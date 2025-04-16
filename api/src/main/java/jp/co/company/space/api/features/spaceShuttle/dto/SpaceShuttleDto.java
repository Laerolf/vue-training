package jp.co.company.space.api.features.spaceShuttle.dto;

import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttleModel.dto.SpaceShuttleModelDto;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * A POJO representing a DTO of a {@link SpaceShuttle} instance.
 */
@Schema(description = "The details of a space shuttle.")
public class SpaceShuttleDto {

    /**
     * Creates a new {@link SpaceShuttleDto} instance based on a {@link SpaceShuttle} instance.
     *
     * @param spaceShuttle The base {@link SpaceShuttle} instance.
     * @return a new {@link SpaceShuttleDto} instance.
     */
    public static SpaceShuttleDto create(SpaceShuttle spaceShuttle) {
        return new SpaceShuttleDto(spaceShuttle.getId(), spaceShuttle.getName(), SpaceShuttleModelDto.create(spaceShuttle.getModel()));
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
     * The model of the space shuttle.
     */
    @Schema(description = "The model of the space shuttle", required = true)
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
