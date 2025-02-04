package jp.co.company.space.api.features.spaceShuttleModel.domain;

/**
 * A POJO representing an event indicating the initialization of the {@link SpaceShuttleModelService}.
 */
public class SpaceShuttleModelServiceInit {

    /**
     * Creates a new {@link SpaceShuttleModelServiceInit} instance.
     * 
     * @return a new {@link SpaceShuttleModelServiceInit} instance
     */
    public static SpaceShuttleModelServiceInit create() {
        return new SpaceShuttleModelServiceInit();
    }

    protected SpaceShuttleModelServiceInit() {}
}