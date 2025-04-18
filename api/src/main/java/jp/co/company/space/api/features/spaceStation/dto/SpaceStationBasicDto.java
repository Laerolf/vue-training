package jp.co.company.space.api.features.spaceStation.dto;

import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * A POJO representing a brief DTO of a {@link SpaceStation} instance.
 */
@Schema(description = "The brief details of a space station.")
public class SpaceStationBasicDto {

    /**
     * Creates a {@link SpaceStationBasicDto} entity from a base {@link SpaceStation} instance.
     *
     * @param spaceStation The base {@link SpaceStation} instance.
     * @return A {@link SpaceStationBasicDto} entity.
     */
    public static SpaceStationBasicDto create(SpaceStation spaceStation) {
        if (spaceStation == null) {
            throw new IllegalArgumentException("the base space station is missing.");
        }

        return new SpaceStationBasicDto(spaceStation.getId(), spaceStation.getName(), spaceStation.getCode(), spaceStation.getCountry(), spaceStation.getLocation().getId());
    }

    @Schema(description = "The ID of the space station.", required = true, example = "1")
    public String id;

    @Schema(description = "The name of the space station.", required = true, example = "Tanegashima Space Center")
    public String name;

    @Schema(description = "The code of the space station.", required = true, example = "TNS")
    public String code;

    @Schema(description = "The country of the space station.", required = true, example = "Japan")
    public String country;

    @Schema(description = "The location's ID of the space station.", required = true)
    public String locationId;

    protected SpaceStationBasicDto() {}

    private SpaceStationBasicDto(String id, String name, String code, String country, String locationId) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the space station is missing.");
        } else if (name == null) {
            throw new IllegalArgumentException("The name of the space station is missing.");
        } else if (code == null) {
            throw new IllegalArgumentException("The code of the space station is missing.");
        } else if (locationId == null) {
            throw new IllegalArgumentException("The location's ID of the space station is missing.");
        }

        this.id = id;
        this.name = name;
        this.code = code;
        this.country = country;
        this.locationId = locationId;
    }
}
