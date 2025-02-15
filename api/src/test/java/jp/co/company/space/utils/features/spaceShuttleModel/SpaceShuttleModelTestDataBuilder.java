package jp.co.company.space.utils.features.spaceShuttleModel;

import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;

/**
 * A utility class that creates {@link SpaceShuttleModel} instances for tests.
 */
public class SpaceShuttleModelTestDataBuilder {

    /**
     * The default name for the space shuttle model.
     */
    private static String DEFAULT_NAME = "A";

    /**
     * The default maximum capacity for the space shuttle model.
     */
    private static int DEFAULT_MAX_CAPACITY = 100;

    /**
     * The default maximum speed for the space shuttle model.
     */
    private static long DEFAULT_MAX_SPEED = 100000;

    public SpaceShuttleModelTestDataBuilder() {}

    /**
     * Creates a new {@link SpaceShuttleModel} instance.
     * 
     * @return a new {@link SpaceShuttleModel} instance
     */
    public SpaceShuttleModel create() {
        return SpaceShuttleModel.create(DEFAULT_NAME, DEFAULT_MAX_CAPACITY, DEFAULT_MAX_SPEED);
    }
}
