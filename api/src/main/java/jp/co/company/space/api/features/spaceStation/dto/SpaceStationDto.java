package jp.co.company.space.api.features.spaceStation.dto;

import jp.co.company.space.api.features.location.dto.BasicLocationDto;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.Examples.*;

/**
 * A POJO representing a DTO of a {@link SpaceStation}.
 */
@Schema(name = "SpaceStation", description = "The details of a space station.")
public class SpaceStationDto {

    /**
     * Creates a {@link SpaceStationDto} from a base {@link SpaceStation}.
     *
     * @param spaceStation The base {@link SpaceStation}.
     * @return A {@link SpaceStationDto}.
     */
    public static SpaceStationDto create(SpaceStation spaceStation) {
        if (spaceStation == null) {
            throw new IllegalArgumentException("the base space station is missing.");
        }

        return new SpaceStationDto(spaceStation.getId(), spaceStation.getName(), spaceStation.getCode(), spaceStation.getCountry(), BasicLocationDto.create(spaceStation.getLocation()));
    }

    @Schema(description = "The ID of the space station.", example = SPACE_STATION_ORIGIN_ID_EXAMPLE)
    public String id;

    @Schema(description = "The name of the space station.", example = SPACE_STATION_NAME_EXAMPLE)
    public String name;

    @Schema(description = "The code of the space station.", example = SPACE_STATION_CODE_EXAMPLE)
    public String code;

    @Schema(description = "The country of the space station.", example = SPACE_STATION_COUNTRY_EXAMPLE)
    public String country;

    @Schema(description = "The location of the space station.")
    public BasicLocationDto location;

    protected SpaceStationDto() {}

    private SpaceStationDto(String id, String name, String code, String country, BasicLocationDto location) {
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
