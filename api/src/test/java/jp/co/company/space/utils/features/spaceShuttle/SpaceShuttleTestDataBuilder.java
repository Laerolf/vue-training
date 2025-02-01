package jp.co.company.space.utils.features.spaceShuttle;

import jakarta.persistence.EntityManager;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;

/**
 * A utility class that creates {@link SpaceShuttle} instances for tests.
 */
public class SpaceShuttleTestDataBuilder {

    /**
     * The test data build to use.
     */
    private SpaceShuttleTestDataBuild testDataBuild;

    /**
     * The entity manager to use.
     */
    private EntityManager entityManager;

    public SpaceShuttleTestDataBuilder() {}

    public SpaceShuttleTestDataBuilder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Creates a new {@link SpaceShuttle} instance.
     * 
     * @param shuttleModel The model of the space shuttle
     * @return a new {@link SpaceShuttle} instance
     */
    public SpaceShuttle create(SpaceShuttleModel shuttleModel) {
        testDataBuild = SpaceShuttleTestDataBuild.create(shuttleModel);
        return testDataBuild.getInstance();
    }

    /**
     * Creates a new {@link SpaceShuttle} instance and persists it.
     * 
     * @param shuttleModel The model of the space shuttle
     * @return a new persisted {@link SpaceShuttle} instance
     */

    public SpaceShuttle createAndPersist(SpaceShuttleModel shuttleModel) {
        testDataBuild = SpaceShuttleTestDataBuild.create(shuttleModel);
        entityManager.persist(testDataBuild.getInstance());
        return entityManager.find(SpaceShuttle.class, testDataBuild.getInstance().getId());
    }
}
