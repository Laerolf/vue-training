package jp.co.company.space.api.features.spaceShuttleModel.domain;

/**
 * A POJO representing an event indicating the initialization of the {@link SpaceShuttleService}.
 */
public class SpaceShuttleServiceInit {

    /**
     * Creates a new {@link SpaceShuttleServiceInit} instance.
     * 
     * @return a new {@link SpaceShuttleServiceInit} instance
     */
    public static SpaceShuttleServiceInit create() {
        return new SpaceShuttleServiceInit();
    }

    protected SpaceShuttleServiceInit() {}
}