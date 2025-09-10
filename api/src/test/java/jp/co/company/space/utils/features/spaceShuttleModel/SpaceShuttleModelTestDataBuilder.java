package jp.co.company.space.utils.features.spaceShuttleModel;

import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceShuttleModel.exception.SpaceShuttleModelException;

import java.util.Optional;

/**
 * A test data builder that creates a {@link SpaceShuttleModel} for testing purposes.
 */
public class SpaceShuttleModelTestDataBuilder {

    /**
     * The default name for the space shuttle model.
     */
    private static final String DEFAULT_NAME = "A";

    /**
     * The default maximum capacity for the space shuttle model.
     */
    private static final int DEFAULT_MAX_CAPACITY = 100;

    /**
     * The default maximum speed for the space shuttle model.
     */
    private static final long DEFAULT_MAX_SPEED = 100000;

    private Integer maxCapacity;
    private Long maxSpeed;

    public SpaceShuttleModelTestDataBuilder() {
    }

    public SpaceShuttleModelTestDataBuilder withMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
        return this;
    }

    public SpaceShuttleModelTestDataBuilder withMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = Long.valueOf(maxSpeed);
        return this;
    }

    /**
     * Creates a new {@link SpaceShuttleModel}.
     *
     * @return a new {@link SpaceShuttleModel}
     */
    public SpaceShuttleModel create() throws SpaceShuttleModelException {
        return SpaceShuttleModel.create(
                DEFAULT_NAME,
                Optional.ofNullable(maxCapacity).orElse(DEFAULT_MAX_CAPACITY),
                Optional.ofNullable(maxSpeed).orElse(DEFAULT_MAX_SPEED)
        );
    }
}
