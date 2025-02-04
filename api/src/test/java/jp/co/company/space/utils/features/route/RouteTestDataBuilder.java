package jp.co.company.space.utils.features.route;

import jakarta.persistence.EntityManager;
import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;

/**
 * A utility class that creates {@link Route} instances for tests.
 */
public class RouteTestDataBuilder {
    /**
     * The test data build to use.
     */
    private RouteTestDataBuild testDataBuild;

    /**
     * The entity manager to use.
     */
    private EntityManager entityManager;

    public RouteTestDataBuilder() {}

    public RouteTestDataBuilder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Creates a new {@link Route} instance.
     * 
     * @param origin       The origin of the route.
     * @param destination  The destination of the route.
     * @param shuttleModel The space shuttle model for the route.
     * @return a new {@link Route} instance.
     */
    public Route create(SpaceStation origin, SpaceStation destination, SpaceShuttleModel shuttleModel) {
        testDataBuild = RouteTestDataBuild.create(origin, destination, shuttleModel);
        return testDataBuild.getInstance();
    }

    /**
     * Creates a new {@link Route} instance and persists it.
     * 
     * @param origin       The origin of the route.
     * @param destination  The destination of the route.
     * @param shuttleModel The space shuttle model for the route.
     * @return a new, persisted {@link Route} instance.
     */

    public Route createAndPersist(SpaceStation origin, SpaceStation destination, SpaceShuttleModel shuttleModel) {
        testDataBuild = RouteTestDataBuild.create(origin, destination, shuttleModel);
        entityManager.persist(testDataBuild.getInstance());
        return entityManager.find(Route.class, testDataBuild.getInstance().getId());
    }
}
