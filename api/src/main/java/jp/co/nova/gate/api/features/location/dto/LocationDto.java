package jp.co.nova.gate.api.features.location.dto;

import jp.co.nova.gate.api.features.location.domain.Location;
import jp.co.nova.gate.api.features.location.exception.LocationError;
import jp.co.nova.gate.api.features.location.exception.LocationException;
import jp.co.nova.gate.api.features.locationCharacteristic.dto.LocationCharacteristicDto;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

import static jp.co.nova.gate.api.shared.openApi.Examples.*;

/**
 * A POJO representing a brief DTO of a {@link Location}.
 */
@Schema(name = "Location", description = "The details of a location.")
public class LocationDto {

    /**
     * Creates a new {@link LocationDto} based on a {@link Location}.
     *
     * @param location The base {@link Location}.
     * @return A new {@link LocationDto}.
     */
    public static LocationDto create(Location location) throws LocationException {
        List<LocationCharacteristicDto> characteristics = location.getCharacteristics().stream().map(LocationCharacteristicDto::create).toList();
        return new LocationDto(location.getId(), location.getName(), location.getLatitude(), location.getLongitude(), location.getRadialDistance(), characteristics);
    }

    /**
     * The ID of the location.
     */
    @Schema(description = "The ID of the location.", example = LOCATION_ID_EXAMPLE)
    public String id;

    /**
     * The name of the location.
     */
    @Schema(description = "The name of the location.", example = LOCATION_NAME_EXAMPLE)
    public String name;

    /**
     * The ecliptic latitude of the location.
     */
    @Schema(description = "The ecliptic latitude of the location.", example = LOCATION_LATITUDE_EXAMPLE)
    public Double latitude;

    /**
     * The ecliptic longitude of the location.
     */
    @Schema(description = "The ecliptic longitude of the location.", example = LOCATION_LONGITUDE_EXAMPLE)
    public Double longitude;

    /**
     * The radial distance of the location in astronomical units.
     */
    @Schema(description = "The radial distance of the location in astronomical units.", example = LOCATION_RADIAL_DISTANCE_EXAMPLE)
    public Double radialDistance;

    /**
     * The characteristics of the location.
     */
    @Schema(description = "The characteristics of the location.")
    public List<LocationCharacteristicDto> locationCharacteristics;

    protected LocationDto() {
    }

    protected LocationDto(String id, String name, double latitude, double longitude, double radialDistance, List<LocationCharacteristicDto> locationCharacteristics) {
        if (id == null) {
            throw new LocationException(LocationError.MISSING_ID);
        } else if (name == null) {
            throw new LocationException(LocationError.MISSING_NAME);
        }

        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radialDistance = radialDistance;
        this.locationCharacteristics = locationCharacteristics;
    }
}
