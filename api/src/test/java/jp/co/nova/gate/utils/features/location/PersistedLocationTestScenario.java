package jp.co.nova.gate.utils.features.location;

import jakarta.inject.Inject;
import jp.co.nova.gate.api.features.location.domain.Location;
import jp.co.nova.gate.api.features.location.exception.LocationException;
import jp.co.nova.gate.api.features.location.repository.LocationRepository;
import jp.co.nova.gate.utils.shared.testScenario.TestScenario;
import jp.co.nova.gate.utils.shared.testScenario.TestScenarioException;

/**
 * A {@link TestScenario} with a persisted location.
 */
public class PersistedLocationTestScenario implements TestScenario {

    @Inject
    private LocationRepository repository;

    private Location persistedLocation;

    protected PersistedLocationTestScenario() {
    }

    @Override
    public void setup() throws TestScenarioException {
        try {
            Location location = new LocationTestDataBuilder().create();

            persistedLocation = repository.save(location);
        } catch (LocationException exception) {
            throw new TestScenarioException("Failed to setup a location test scenario", exception);
        }
    }

    public Location getPersistedLocation() {
        return persistedLocation;
    }
}
