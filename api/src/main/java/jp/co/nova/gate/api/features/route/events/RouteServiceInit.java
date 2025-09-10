package jp.co.nova.gate.api.features.route.events;

import jp.co.nova.gate.api.features.route.service.RouteService;

/**
 * A POJO representing an event indicating the initialisation of a {@link RouteService}.
 */
public class RouteServiceInit {

    /**
     * Creates a new {@link RouteServiceInit}.
     *
     * @return A new {@link RouteServiceInit}.
     */
    public static RouteServiceInit create() {
        return new RouteServiceInit();
    }

    protected RouteServiceInit() {}
}
