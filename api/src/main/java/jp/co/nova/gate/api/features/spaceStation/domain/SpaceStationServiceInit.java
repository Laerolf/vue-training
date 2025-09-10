package jp.co.nova.gate.api.features.spaceStation.domain;

import jp.co.nova.gate.api.features.spaceStation.service.SpaceStationService;

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