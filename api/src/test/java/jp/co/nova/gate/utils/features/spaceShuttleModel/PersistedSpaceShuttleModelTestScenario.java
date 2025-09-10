package jp.co.nova.gate.utils.features.spaceShuttleModel;

import jakarta.inject.Inject;
import jp.co.nova.gate.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.nova.gate.api.features.spaceShuttleModel.exception.SpaceShuttleModelException;
import jp.co.nova.gate.api.features.spaceShuttleModel.repository.SpaceShuttleModelRepository;
import jp.co.nova.gate.utils.shared.testScenario.TestScenario;
import jp.co.nova.gate.utils.shared.testScenario.TestScenarioException;

/**
 * A {@link TestScenario} with a persisted space shuttle model.
 */
public class PersistedSpaceShuttleModelTestScenario implements TestScenario {

    @Inject
    private SpaceShuttleModelRepository repository;

    private SpaceShuttleModel persistedSpaceShuttleModel;

    protected PersistedSpaceShuttleModelTestScenario() {
    }

    @Override
    public void setup() {
        try {
            SpaceShuttleModel spaceShuttleModel = new SpaceShuttleModelTestDataBuilder().create();
            persistedSpaceShuttleModel = repository.save(spaceShuttleModel);
        } catch (TestScenarioException | SpaceShuttleModelException exception) {
            throw new TestScenarioException("Failed to setup a space shuttle model test scenario", exception);
        }
    }

    public SpaceShuttleModel getPersistedSpaceShuttleModel() {
        return persistedSpaceShuttleModel;
    }
}
