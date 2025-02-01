package jp.co.company.space.utils.features.spaceStation;

import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;

/**
 * A POJO representing a test data build for a {@link SpaceStation} instance.
 */
class SpaceStationTestDataBuild {
    /**
     * The default name for the space station.
     */
    private static String name = "A";

    /**
     * The default code for the space station.
     */
    private static String code = "AJ";

    /**
     * The default country for the space station.
     */
    private static String country = "Japan";

    /**
     * Creates a new {@link SpaceStationTestDataBuild} instance.
     * 
     * @param location The location of the space station
     * @return a new {@link SpaceStationTestDataBuild} instance
     */
    static SpaceStationTestDataBuild create(Location location) {
        return new SpaceStationTestDataBuild(SpaceStation.create(name, code, country, location));
    }

    /**
     * The instance to build.
     */
    private SpaceStation instance;

    protected SpaceStationTestDataBuild(SpaceStation instance) {
        this.instance = instance;
    }

    SpaceStation getInstance() {
        return instance;
    }
}
