package jp.co.company.space.api.features.location.dto;

import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.location.exception.LocationError;
import jp.co.company.space.api.features.location.exception.LocationException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.Examples.*;

/**
 * A POJO representing a brief DTO of a {@link Location} instance.
 */
@Schema(name = "Location", description = "The details of a location.")
public class LocationDto {

    /**
     * Creates a new {@link LocationDto} instance based on a {@link Location} instance.
     *
     * @param location The base {@link Location} instance.
     * @return A new {@link LocationDto} instance.
     */
    public static LocationDto create(Location location) throws LocationException {
        return new LocationDto(location.getId(), location.getName(), location.getLatitude(), location.getLongitude(), location.getRadialDistance());
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
    public double latitude;

    /**
     * The ecliptic longitude of the location.
     */
    @Schema(description = "The ecliptic longitude of the location.", example = LOCATION_LONGITUDE_EXAMPLE)
    public double longitude;

    /**
     * The radial distance of the location in astronomical units.
     */
    @Schema(description = "The radial distance of the location in astronomical units.", example = LOCATION_RADIAL_DISTANCE_EXAMPLE)
    public double radialDistance;

    protected LocationDto() {
    }

    protected LocationDto(String id, String name, double latitude, double longitude, double radialDistance) throws LocationException {
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
    }
}
