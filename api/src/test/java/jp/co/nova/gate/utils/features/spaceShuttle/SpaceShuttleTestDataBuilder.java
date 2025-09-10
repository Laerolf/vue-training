package jp.co.nova.gate.utils.features.spaceShuttle;

import jp.co.nova.gate.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.nova.gate.api.features.spaceShuttle.exception.SpaceShuttleException;
import jp.co.nova.gate.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.nova.gate.utils.features.spaceShuttleModel.SpaceShuttleModelTestDataBuilder;

import java.util.Optional;

/**
 * A test data builder that creates a {@link SpaceShuttle} for testing purposes.
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
     * Creates a new {@link SpaceShuttle}.
     *
     * @return a new {@link SpaceShuttle}
     */
    public SpaceShuttle create() throws SpaceShuttleException {
        return SpaceShuttle.create(
                Optional.ofNullable(name).orElse(DEFAULT_NAME),
                Optional.ofNullable(model).orElse(DEFAULT_SPACE_SHUTTLE_MODEL)
        );
    }
}
