package jp.co.company.space.utils.features.spaceShuttle;

import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;

/**
 * A POJO representing a test data build for a {@link SpaceShuttle} instance.
 */
class SpaceShuttleTestDataBuild {
    /**
     * The default name for the space shuttle.
     */
    private static String name = "A";

    /**
     * Creates a new {@link SpaceShuttleTestDataBuild} instance.
     * 
     * @param shuttleModel The model of the space shuttle
     * @return a new {@link SpaceShuttleTestDataBuild} instance
     */
    static SpaceShuttleTestDataBuild create(SpaceShuttleModel shuttleModel) {
        return new SpaceShuttleTestDataBuild(SpaceShuttle.create(name, shuttleModel));
    }

    /**
     * The instance to build.
     */
    private SpaceShuttle instance;

    protected SpaceShuttleTestDataBuild(SpaceShuttle instance) {
        this.instance = instance;
    }

    SpaceShuttle getInstance() {
        return instance;
    }
}
