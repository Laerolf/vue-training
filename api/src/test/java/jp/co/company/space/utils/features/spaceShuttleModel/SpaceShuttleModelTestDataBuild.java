package jp.co.company.space.utils.features.spaceShuttleModel;

import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;

/**
 * A POJO representing a test data build for a {@link SpaceShuttleModel} instance.
 */
class SpaceShuttleModelTestDataBuild {
    /**
     * The default name for the space shuttle model.
     */
    private static String name = "A";

    /**
     * The default maximum capacity for the space shuttle model.
     */
    private static int maxCapacity = 100;

    /**
     * The default maximum speed for the space shuttle model.
     */
    private static long maxSpeed = 100000;

    /**
     * Creates a new {@link SpaceShuttleModelTestDataBuild} instance.
     * 
     * @return a new {@link SpaceShuttleModelTestDataBuild} instance
     */
    static SpaceShuttleModelTestDataBuild create() {
        return new SpaceShuttleModelTestDataBuild(SpaceShuttleModel.create(name, maxCapacity, maxSpeed));
    }

    /**
     * The instance to build.
     */
    private SpaceShuttleModel instance;

    protected SpaceShuttleModelTestDataBuild(SpaceShuttleModel instance) {
        this.instance = instance;
    }

    SpaceShuttleModel getInstance() {
        return instance;
    }
}
