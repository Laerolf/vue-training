package jp.co.company.space.utils.features.location;

import jp.co.company.space.api.features.location.domain.Location;

import java.util.Optional;

/**
 * A utility class that creates {@link Location} instances for tests.
 */
public class LocationTestDataBuilder {
    /**
     * The default name for the location.
     */
    private static final String DEFAULT_NAME = "Earth";

    /**
     * The default ecliptic latitude for the location.
     */
    private static final double DEFAULT_LATITUDE = 0;

    /**
     * The default ecliptic longitude for the location.
     */
    private static final double DEFAULT_LONGITUDE = 326;

    /**
     * The default radial distance for the location.
     */
    private static final double DEFAULT_RADIAL_DISTANCE = 1;

    private String name;
    private Double latitude;
    private Double longitude;
    private Double radialDistance;

    public LocationTestDataBuilder() {}

    public LocationTestDataBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public LocationTestDataBuilder withLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public LocationTestDataBuilder withLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public LocationTestDataBuilder withRadialDistance(double radialDistance) {
        this.radialDistance = radialDistance;
        return this;
    }

    /**
     * Creates a new {@link Location} instance.
     *
     * @return a new {@link Location} instance
     */
    public Location create() {
        Location location = Location.create(Optional.ofNullable(name).orElse(DEFAULT_NAME), Optional.ofNullable(latitude).orElse(DEFAULT_LATITUDE),
                Optional.ofNullable(longitude).orElse(DEFAULT_LONGITUDE), Optional.ofNullable(radialDistance).orElse(DEFAULT_RADIAL_DISTANCE));

        cleanUp();
        return location;
    }

    private void cleanUp() {
        name = null;
        latitude = null;
        longitude = null;
        radialDistance = null;
    }
}
