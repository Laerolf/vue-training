package jp.co.company.space.utils.features.spaceStation;

import jakarta.persistence.EntityManager;
import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;

/**
 * A utility class that creates {@link SpaceStation} instances for tests.
 */
public class SpaceStationTestDataBuilder {
    /**
     * The test data build to use.
     */
    private SpaceStationTestDataBuild testDataBuild;

    /**
     * The entity manager to use.
     */
    private EntityManager entityManager;

    public SpaceStationTestDataBuilder() {}

    public SpaceStationTestDataBuilder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Creates a new {@link SpaceStation} instance.
     * 
     * @param location The location of the space station
     * @return a new {@link SpaceStation} instance
     */
    public SpaceStation create(Location location) {
        testDataBuild = SpaceStationTestDataBuild.create(location);
        return testDataBuild.getInstance();
    }

    /**
     * Creates a new {@link SpaceStation} instance and persists it.
     * 
     * @param location The location of the space station
     * @return a new persisted {@link SpaceStation} instance
     */
    public SpaceStation createAndPersist(Location location) {
        testDataBuild = SpaceStationTestDataBuild.create(location);
        entityManager.persist(testDataBuild.getInstance());
        return entityManager.find(SpaceStation.class, testDataBuild.getInstance().getId());
    }
}
