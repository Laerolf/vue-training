package jp.co.nova.gate.utils.features.spaceStation;

import jakarta.inject.Inject;
import jp.co.nova.gate.api.features.spaceStation.domain.SpaceStation;
import jp.co.nova.gate.api.features.spaceStation.exception.SpaceStationException;
import jp.co.nova.gate.api.features.spaceStation.repository.SpaceStationRepository;
import jp.co.nova.gate.utils.features.location.PersistedLocationTestScenario;
import jp.co.nova.gate.utils.shared.testScenario.TestScenario;
import jp.co.nova.gate.utils.shared.testScenario.TestScenarioException;

/**
 * A {@link TestScenario} with a persisted space station.
 * Nested scenarios: {@link PersistedLocationTestScenario}
 */
public class SpaceStationTestScenario implements TestScenario {

    @Inject
    public PersistedLocationTestScenario persistedLocationTestScenario;

    @Inject
    public SpaceStationRepository repository;

    private SpaceStation persistedSpaceStation;

    protected SpaceStationTestScenario() {
    }

    @Override
    public void setup() throws TestScenarioException {
        try {
            persistedLocationTestScenario.setup();

            SpaceStation spaceStation = new SpaceStationTestDataBuilder().create(persistedLocationTestScenario.getPersistedLocation());
            persistedSpaceStation = repository.save(spaceStation);
        } catch (TestScenarioException | SpaceStationException exception) {
            throw new TestScenarioException("Failed to setup a space station test scenario", exception);
        }
    }

    public SpaceStation getPersistedSpaceStation() {
        return persistedSpaceStation;
    }
}
