package jp.co.company.space.utils.features.spaceStation;

import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;

/**
 * A utility class that creates {@link SpaceStation} instances for tests.
 */
public class SpaceStationTestDataBuilder {
    /**
     * The default name for the space station.
     */
    private static final String DEFAULT_NAME = "Station 1";

    /**
     * The default country name for the space staton.
     */
    private static final String DEFAULT_COUNTRY = null;

    public SpaceStationTestDataBuilder() {}

    /**
     * Creates a new {@link SpaceStation} instance.
     *
     * @param location The location of the space station
     * @return a new {@link SpaceStation} instance
     */
    public SpaceStation create(Location location) {
        return SpaceStation.create(DEFAULT_NAME, DEFAULT_NAME, DEFAULT_COUNTRY, location);
    }
}
