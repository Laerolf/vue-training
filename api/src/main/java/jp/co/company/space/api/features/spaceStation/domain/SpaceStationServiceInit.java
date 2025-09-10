package jp.co.company.space.api.features.spaceStation.domain;

import jp.co.company.space.api.features.spaceStation.service.SpaceStationService;

/**
 * A POJO representing an event indicating the initialization of the {@link SpaceStationService}.
 */
public class SpaceStationServiceInit {

    /**
     * Creates a new {@link SpaceStationServiceInit}.
     *
     * @return a new {@link SpaceStationServiceInit}
     */
    public static SpaceStationServiceInit create() {
        return new SpaceStationServiceInit();
    }

    protected SpaceStationServiceInit() {}
}