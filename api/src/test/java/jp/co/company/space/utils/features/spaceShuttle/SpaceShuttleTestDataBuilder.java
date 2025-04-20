package jp.co.company.space.utils.features.spaceShuttle;

import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.utils.features.spaceShuttleModel.SpaceShuttleModelTestDataBuilder;

import java.util.Optional;

/**
 * A utility class that creates {@link SpaceShuttle} instances for tests.
 */
public class SpaceShuttleTestDataBuilder {

    /**
     * The default name for the space shuttle.
     */
    private static final String DEFAULT_NAME = "A";

    /**
     * The default model for the space shuttle.
     */
    private static final SpaceShuttleModel DEFAULT_SPACE_SHUTTLE_MODEL = new SpaceShuttleModelTestDataBuilder().create();

    private String name;

    private SpaceShuttleModel model;

    public SpaceShuttleTestDataBuilder() {
    }

    public SpaceShuttleTestDataBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public SpaceShuttleTestDataBuilder withModel(SpaceShuttleModel model) {
        this.model = model;
        return this;
    }

    /**
     * Creates a new {@link SpaceShuttle} instance.
     *
     * @return a new {@link SpaceShuttle} instance
     */
    public SpaceShuttle create() {
        return SpaceShuttle.create(
                Optional.ofNullable(name).orElse(DEFAULT_NAME),
                Optional.ofNullable(model).orElse(DEFAULT_SPACE_SHUTTLE_MODEL)
        );
    }
}
