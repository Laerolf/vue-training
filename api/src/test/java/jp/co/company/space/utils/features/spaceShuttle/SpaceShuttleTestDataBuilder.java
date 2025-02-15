package jp.co.company.space.utils.features.spaceShuttle;

import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;

/**
 * A utility class that creates {@link SpaceShuttle} instances for tests.
 */
public class SpaceShuttleTestDataBuilder {

    /**
     * The default name for the space shuttle.
     */
    private static String DEFAULT_NAME = "A";

    public SpaceShuttleTestDataBuilder() {}

    /**
     * Creates a new {@link SpaceShuttle} instance.
     * 
     * @param shuttleModel The model of the space shuttle
     * @return a new {@link SpaceShuttle} instance
     */
    public SpaceShuttle create(SpaceShuttleModel shuttleModel) {
        return SpaceShuttle.create(DEFAULT_NAME, shuttleModel);
    }
}
