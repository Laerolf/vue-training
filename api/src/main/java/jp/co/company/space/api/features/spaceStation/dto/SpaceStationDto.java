package jp.co.company.space.api.features.spaceStation.dto;

import jp.co.company.space.api.features.location.dto.LocationDto;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * A POJO representing a DTO of a {@link SpaceStation} instance.
 */
@Schema(description = "The details of a space station.")
public class SpaceStationDto {

    /**
     * Creates a {@link SpaceStationDto} entity from a base {@link SpaceStation} instance.
     *
     * @param spaceStation The base {@link SpaceStation} instance.
     * @return A {@link SpaceStationDto} entity.
     */
    public static SpaceStationDto create(SpaceStation spaceStation) {
        if (spaceStation == null) {
            throw new IllegalArgumentException("the base space station is missing.");
        }

        return new SpaceStationDto(spaceStation.getId(), spaceStation.getName(), spaceStation.getCode(), spaceStation.getCountry(), LocationDto.create(spaceStation.getLocation()));
    }

    @Schema(description = "The ID of the space station.", required = true, example = "1")
    public String id;

    @Schema(description = "The name of the space station.", required = true, example = "Tanegashima Space Center")
    public String name;

    @Schema(description = "The code of the space station.", required = true, example = "TNS")
    public String code;

    @Schema(description = "The country of the space station.", required = true, example = "Japan")
    public String country;

    @Schema(description = "The location of the space station.", required = true)
    public LocationDto location;

    protected SpaceStationDto() {}

    private SpaceStationDto(String id, String name, String code, String country, LocationDto location) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the space station is missing.");
        } else if (name == null) {
            throw new IllegalArgumentException("The name of the space station is missing.");
        } else if (code == null) {
            throw new IllegalArgumentException("The code of the space station is missing.");
        } else if (location == null) {
            throw new IllegalArgumentException("The location of the space station is missing.");
        }

        this.id = id;
        this.name = name;
        this.code = code;
        this.country = country;
        this.location = location;
    }
}
