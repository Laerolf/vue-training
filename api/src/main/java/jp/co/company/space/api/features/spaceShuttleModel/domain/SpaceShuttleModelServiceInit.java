package jp.co.company.space.api.features.spaceShuttleModel.domain;

import jp.co.company.space.api.features.spaceShuttleModel.service.SpaceShuttleModelService;

/**
 * A POJO representing an event indicating the initialization of the {@link SpaceShuttleModelService}.
 */
public class SpaceShuttleModelServiceInit {

    /**
     * Creates a new {@link SpaceShuttleModelServiceInit}.
     *
     * @return a new {@link SpaceShuttleModelServiceInit}
     */
    public static SpaceShuttleModelServiceInit create() {
        return new SpaceShuttleModelServiceInit();
    }

    protected SpaceShuttleModelServiceInit() {}
}