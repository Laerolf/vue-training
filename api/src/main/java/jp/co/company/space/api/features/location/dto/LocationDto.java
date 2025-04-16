package jp.co.company.space.api.features.location.dto;

import jp.co.company.space.api.features.location.domain.Location;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * A POJO representing a brief DTO of a {@link Location} instance.
 */
@Schema(description = "The details of a location.")
public class LocationDto {

    /**
     * Creates a new {@link LocationDto} instance based on a {@link Location} instance.
     * @param location The base {@link Location} instance.
     * @return A new {@link LocationDto} instance.
     */
    public static LocationDto create(Location location) {
        return new LocationDto(location.getId(), location.getName(), location.getLatitude(), location.getLongitude(), location.getRadialDistance());
    }

    /**
     * The ID of the location.
     */
    @Schema(description = "The ID of the location.", required = true, example = "1")
    public String id;

    /**
     * The name of the location.
     */
    @Schema(description = "The name of the location.", required = true, example = "Earth")
    public String name;

    /**
     * The ecliptic latitude of the location.
     */
    @Schema(description = "The ecliptic latitude of the location.", required = true, example = "0.0")
    public double latitude;

    /**
     * The ecliptic longitude of the location.
     */
    @Schema(description = "The ecliptic longitude of the location.", required = true, example = "0.0")
    public double longitude;

    /**
     * The radial distance of the location in astronomical units.
     */
    @Schema(description = "The radial distance of the location in astronomical units.", required = true, example = "27000")
    public double radialDistance;

    protected LocationDto() {}

    protected LocationDto(String id, String name, double latitude, double longitude, double radialDistance) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the location is missing.");
        } else if (name == null) {
            throw new IllegalArgumentException("The name of the location is missing.");
        }

        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radialDistance = radialDistance;
    }
}
