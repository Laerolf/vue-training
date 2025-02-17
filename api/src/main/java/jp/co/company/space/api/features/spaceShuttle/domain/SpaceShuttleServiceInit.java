package jp.co.company.space.api.features.spaceShuttle.domain;

/**
 * A POJO representing an event indicating the initalisation of a {@link SpaceShuttleService} instance.
 */
public class SpaceShuttleServiceInit {

    /**
     * Creates a new {@link SpaceShuttleServiceInit} instance.
     * 
     * @return A new {@link SpaceShuttleServiceInit} instance.
     */
    public static SpaceShuttleServiceInit create() {
        return new SpaceShuttleServiceInit();
    }

    protected SpaceShuttleServiceInit() {}
}
