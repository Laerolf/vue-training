package jp.co.nova.gate.utils.features.spaceShuttle;

import jakarta.inject.Inject;
import jp.co.nova.gate.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.nova.gate.api.features.spaceShuttle.exception.SpaceShuttleException;
import jp.co.nova.gate.api.features.spaceShuttle.repository.SpaceShuttleRepository;
import jp.co.nova.gate.utils.features.spaceShuttleModel.PersistedSpaceShuttleModelTestScenario;
import jp.co.nova.gate.utils.shared.testScenario.TestScenario;
import jp.co.nova.gate.utils.shared.testScenario.TestScenarioException;

/**
 * A {@link TestScenario} with a persisted space shuttle.
 * Nested scenarios: {@link PersistedSpaceShuttleModelTestScenario}
 */
public class PersistedSpaceShuttleTestScenario implements TestScenario {

    @Inject
    private PersistedSpaceShuttleModelTestScenario persistedSpaceShuttleModelTestScenario;

    @Inject
    private SpaceShuttleRepository repository;

    private SpaceShuttle persistedSpaceShuttle;

    protected PersistedSpaceShuttleTestScenario() {
    }

    @Override
    public void setup() {
        try {
            persistedSpaceShuttleModelTestScenario.setup();

            SpaceShuttle spaceShuttle = new SpaceShuttleTestDataBuilder().withModel(persistedSpaceShuttleModelTestScenario.getPersistedSpaceShuttleModel()).create();
            persistedSpaceShuttle = repository.save(spaceShuttle);
        } catch (TestScenarioException | SpaceShuttleException exception) {
            throw new TestScenarioException("Failed to setup a space shuttle test scenario", exception);
        }
    }

    public SpaceShuttle getPersistedSpaceShuttle() {
        return persistedSpaceShuttle;
    }
}
