package jp.co.company.space.utils.features.route;

import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;

/**
 * A POJO representing a test data build for a {@link Route} instance.
 */
class RouteTestDataBuild {
    /**
     * Creates a new {@link RouteTestDataBuild} instance.
     * 
     * @param origin       The origin of the route.
     * @param destination  The destination of the route.
     * @param shuttleModel The space shuttle model for the route.
     * @return a new {@link RouteTestDataBuild} instance.
     */
    static RouteTestDataBuild create(SpaceStation origin, SpaceStation destination, SpaceShuttleModel shuttleModel) {
        return new RouteTestDataBuild(Route.create(origin, destination, shuttleModel));
    }

    /**
     * The instance to build.
     */
    private Route instance;

    protected RouteTestDataBuild(Route instance) {
        this.instance = instance;
    }

    Route getInstance() {
        return instance;
    }
}
