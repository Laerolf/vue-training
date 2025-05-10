package jp.co.company.space.utils.features.spaceStation;

import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import jp.co.company.space.api.features.spaceStation.exception.SpaceStationException;

/**
 * A test data builder that creates a {@link SpaceStation} instance for testing purposes.
 */
public class SpaceStationTestDataBuilder {
    /**
     * The default name for the space station.
     */
    private static final String DEFAULT_NAME = "Station 1";

    /**
     * The default country name for the space staton.
     */
    private static final String DEFAULT_COUNTRY = "Unknown";

    public SpaceStationTestDataBuilder() {
    }

    /**
     * Creates a new {@link SpaceStation} instance.
     *
     * @param location The location of the space station
     * @return a new {@link SpaceStation} instance
     */
    public SpaceStation create(Location location) throws SpaceStationException {
        return SpaceStation.create(DEFAULT_NAME, DEFAULT_NAME, DEFAULT_COUNTRY, location);
    }
}
