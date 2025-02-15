package jp.co.company.space.utils.features.location;

import jp.co.company.space.api.features.location.domain.Location;

/**
 * A utility class that creates {@link Location} instances for tests.
 */
public class LocationTestDataBuilder {
    /**
     * The default name for the location.
     */
    private static String DEFAULT_NAME = "A";

    /**
     * The default galatic latitude for the location.
     */
    private static double DEFAULT_LATITUDE = 0;

    /**
     * The default galatic longitude for the location.
     */
    private static double DEFAULT_LONGITUDE = 0;

    /**
     * The default radial distance for the location.
     */
    private static double DEFAULT_RADIAL_DISTANCE = 27000;

    public LocationTestDataBuilder() {}

    /**
     * Creates a new {@link Location} instance.
     * 
     * @return a new {@link Location} instance
     */
    public Location create() {
        return Location.create(DEFAULT_NAME, DEFAULT_LATITUDE, DEFAULT_LONGITUDE, DEFAULT_RADIAL_DISTANCE);
    }
}
