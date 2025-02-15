package jp.co.company.space.utils.features.route;

import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;

/**
 * A utility class that creates {@link Route} instances for tests.
 */
public class RouteTestDataBuilder {
    public RouteTestDataBuilder() {}

    /**
     * Creates a new {@link Route} instance.
     * 
     * @param origin       The origin of the route.
     * @param destination  The destination of the route.
     * @param shuttleModel The space shuttle model for the route.
     * @return a new {@link Route} instance.
     */
    public Route create(SpaceStation origin, SpaceStation destination, SpaceShuttleModel shuttleModel) {
        return Route.create(origin, destination, shuttleModel);
    }
}
