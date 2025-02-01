package jp.co.company.space.utils.features.spaceShuttleModel;

import jakarta.persistence.EntityManager;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;

/**
 * A utility class that creates {@link SpaceShuttleModel} instances for tests.
 */
public class SpaceShuttleModelTestDataBuilder {

    /**
     * The test data build to use.
     */
    private SpaceShuttleModelTestDataBuild testDataBuild;

    /**
     * The entity manager to use.
     */
    private EntityManager entityManager;

    public SpaceShuttleModelTestDataBuilder() {}

    public SpaceShuttleModelTestDataBuilder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Creates a new {@link SpaceShuttleModel} instance.
     * 
     * @return a new {@link SpaceShuttleModel} instance
     */
    public SpaceShuttleModel create() {
        testDataBuild = SpaceShuttleModelTestDataBuild.create();
        return testDataBuild.getInstance();
    }

    /**
     * Creates a new {@link SpaceShuttleModel} instance and persists it.
     * 
     * @return a new persisted {@link SpaceShuttleModel} instance
     */
    public SpaceShuttleModel createAndPersist() {
        testDataBuild = SpaceShuttleModelTestDataBuild.create();
        entityManager.persist(testDataBuild.getInstance());
        return entityManager.find(SpaceShuttleModel.class, testDataBuild.getInstance().getId());
    }
}
