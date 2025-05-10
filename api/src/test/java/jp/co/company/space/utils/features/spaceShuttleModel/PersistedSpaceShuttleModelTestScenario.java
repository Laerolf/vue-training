package jp.co.company.space.utils.features.spaceShuttleModel;

import jakarta.inject.Inject;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceShuttleModel.exception.SpaceShuttleModelException;
import jp.co.company.space.api.features.spaceShuttleModel.repository.SpaceShuttleModelRepository;
import jp.co.company.space.utils.shared.testScenario.TestScenario;
import jp.co.company.space.utils.shared.testScenario.TestScenarioException;

/**
 * A {@link TestScenario} with a persisted space shuttle model.
 */
public class PersistedSpaceShuttleModelTestScenario implements TestScenario {

    @Inject
    private SpaceShuttleModelRepository spaceShuttleModelRepository;

    private SpaceShuttleModel persistedSpaceShuttleModel;

    protected PersistedSpaceShuttleModelTestScenario() {
    }

    @Override
    public void setup() {
        try {
            SpaceShuttleModel spaceShuttleModel = new SpaceShuttleModelTestDataBuilder().create();
            persistedSpaceShuttleModel = spaceShuttleModelRepository.save(spaceShuttleModel);
        } catch (TestScenarioException | SpaceShuttleModelException exception) {
            throw new TestScenarioException("Failed to setup a space shuttle model test scenario", exception);
        }
    }

    public SpaceShuttleModel getPersistedSpaceShuttleModel() {
        return persistedSpaceShuttleModel;
    }
}
