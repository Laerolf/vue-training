package jp.co.company.space.api.features.spaceShuttle.domain;

import jp.co.company.space.api.features.spaceShuttle.service.SpaceShuttleService;

/**
 * A POJO representing an event indicating the initalisation of a {@link SpaceShuttleService}.
 */
public class SpaceShuttleServiceInit {

    /**
     * Creates a new {@link SpaceShuttleServiceInit}.
     *
     * @return A new {@link SpaceShuttleServiceInit}.
     */
    public static SpaceShuttleServiceInit create() {
        return new SpaceShuttleServiceInit();
    }

    protected SpaceShuttleServiceInit() {}
}
