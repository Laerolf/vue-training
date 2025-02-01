package jp.co.company.space.utils.features.location;

import jp.co.company.space.api.features.location.domain.Location;

/**
 * A POJO representing a test data build for a {@link Location} instance.
 */
class LocationTestDataBuild {

    /**
     * The default name for the location.
     */
    private static String name = "A";

    /**
     * The default galatic latitude for the location.
     */
    private static double latitude = 0;

    /**
     * The default galatic longitude for the location.
     */
    private static double longitude = 0;

    /**
     * The default radial distance for the location.
     */
    private static double radialDistance = 27000;

    /**
     * Creates a new {@link LocationTestDataBuild} instance.
     * 
     * @return a new {@link LocationTestDataBuild} instance
     */
    public static LocationTestDataBuild create() {
        Location location = Location.create(name, latitude, longitude, radialDistance);
        return new LocationTestDataBuild(location);
    }

    /**
     * The instance to build.
     */
    private Location instance;

    protected LocationTestDataBuild(Location instance) {
        this.instance = instance;
    }

    public Location getInstance() {
        return instance;
    }

}
