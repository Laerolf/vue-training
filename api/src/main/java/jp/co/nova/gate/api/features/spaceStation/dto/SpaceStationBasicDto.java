package jp.co.nova.gate.api.features.spaceStation.dto;

import jp.co.nova.gate.api.features.spaceStation.domain.SpaceStation;
import jp.co.nova.gate.api.features.spaceStation.exception.SpaceStationError;
import jp.co.nova.gate.api.features.spaceStation.exception.SpaceStationException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.nova.gate.api.shared.openApi.Examples.*;

/**
 * A POJO representing a brief DTO of a {@link SpaceStation}.
 */
@Schema(name = "BasicSpaceStation", description = "The brief details of a space station.")
public class SpaceStationBasicDto {

    /**
     * Creates a {@link SpaceStationBasicDto} from a base {@link SpaceStation}.
     *
     * @param spaceStation The base {@link SpaceStation}.
     * @return A {@link SpaceStationBasicDto}.
     */
    public static SpaceStationBasicDto create(SpaceStation spaceStation) throws SpaceStationException {
        if (spaceStation == null) {
            throw new SpaceStationException(SpaceStationError.MISSING);
        }

        return new SpaceStationBasicDto(spaceStation.getId(), spaceStation.getName(), spaceStation.getCode(), spaceStation.getCountry(), spaceStation.getLocation().getId());
    }

    @Schema(description = "The ID of the space station.", example = SPACE_STATION_ORIGIN_ID_EXAMPLE)
    public String id;

    @Schema(description = "The name of the space station.", example = SPACE_STATION_NAME_EXAMPLE)
    public String name;

    @Schema(description = "The code of the space station.", example = SPACE_STATION_CODE_EXAMPLE)
    public String code;

    @Schema(description = "The country of the space station.", example = SPACE_STATION_COUNTRY_EXAMPLE)
    public String country;

    @Schema(description = "The location's ID of the space station.", example = ID_EXAMPLE)
    public String locationId;

    protected SpaceStationBasicDto() {
    }

    private SpaceStationBasicDto(String id, String name, String code, String country, String locationId) throws SpaceStationException {
        if (id == null) {
            throw new SpaceStationException(SpaceStationError.MISSING_ID);
        } else if (name == null) {
            throw new SpaceStationException(SpaceStationError.MISSING_NAME);
        } else if (code == null) {
            throw new SpaceStationException(SpaceStationError.MISSING_CODE);
        } else if (locationId == null) {
            throw new SpaceStationException(SpaceStationError.MISSING_LOCATION);
        }

        this.id = id;
        this.name = name;
        this.code = code;
        this.country = country;
        this.locationId = locationId;
    }
}
