package jp.co.company.space.api.features.spaceStation.domain;

/**
 * A POJO representing an event indicating the initialization of the {@link SpaceStationService}.
 */
public class SpaceStationServiceInit {

    /**
     * Creates a new {@link SpaceStationServiceInit} instance.
     * 
     * @return a new {@link SpaceStationServiceInit} instance
     */
    public static SpaceStationServiceInit create() {
        return new SpaceStationServiceInit();
    }

    protected SpaceStationServiceInit() {}
}