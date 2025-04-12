package jp.co.company.space.api.features.location.domain;

import jp.co.company.space.api.features.location.service.LocationService;

/**
 * A POJO representing an event indicating the initialization of the {@link LocationService}.
 */
public class LocationServiceInit {

    /**
     * Creates a new {@link LocationServiceInit} instance.
     *
     * @return a new {@link LocationServiceInit} instance
     */
    public static LocationServiceInit create() {
        return new LocationServiceInit();
    }

    protected LocationServiceInit() {}
}