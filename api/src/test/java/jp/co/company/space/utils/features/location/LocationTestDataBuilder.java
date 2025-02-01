package jp.co.company.space.utils.features.location;

import jakarta.persistence.EntityManager;
import jp.co.company.space.api.features.location.domain.Location;

/**
 * A utility class that creates {@link Location} instances for tests.
 */
public class LocationTestDataBuilder {
    /**
     * The test data build to use.
     */
    private LocationTestDataBuild testDataBuild;

    /**
     * The entity manager to use.
     */
    private EntityManager entityManager;

    public LocationTestDataBuilder() {}

    public LocationTestDataBuilder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Creates a new {@link Location} instance.
     * 
     * @return a new {@link Location} instance
     */
    public Location create() {
        testDataBuild = LocationTestDataBuild.create();
        return testDataBuild.getInstance();
    }

    /**
     * Creates a new {@link Location} instance and persists it.
     * 
     * @return a new persisted {@link Location} instance
     */
    public Location createAndPersist() {
        testDataBuild = LocationTestDataBuild.create();
        entityManager.persist(testDataBuild.getInstance());
        return entityManager.find(Location.class, testDataBuild.getInstance().getId());
    }
}
