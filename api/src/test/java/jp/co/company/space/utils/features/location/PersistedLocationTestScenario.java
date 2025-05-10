package jp.co.company.space.utils.features.location;

import jakarta.inject.Inject;
import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.location.exception.LocationException;
import jp.co.company.space.api.features.location.repository.LocationRepository;
import jp.co.company.space.utils.shared.testScenario.TestScenario;
import jp.co.company.space.utils.shared.testScenario.TestScenarioException;

/**
 * A {@link TestScenario} with a persisted location.
 */
public class PersistedLocationTestScenario implements TestScenario {

    @Inject
    private LocationRepository locationRepository;

    private Location persistedLocation;

    protected PersistedLocationTestScenario() {
    }

    @Override
    public void setup() throws TestScenarioException {
        try {
            Location location = new LocationTestDataBuilder().create();

            persistedLocation = locationRepository.save(location);
        } catch (LocationException exception) {
            throw new TestScenarioException("Failed to setup a location test scenario", exception);
        }
    }

    public Location getPersistedLocation() {
        return persistedLocation;
    }
}
