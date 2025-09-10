package jp.co.company.space.utils.features.voyage;

import jakarta.inject.Inject;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.features.voyage.exception.VoyageException;
import jp.co.company.space.api.features.voyage.repository.VoyageRepository;
import jp.co.company.space.utils.features.route.PersistedRouteTestScenario;
import jp.co.company.space.utils.features.spaceShuttle.PersistedSpaceShuttleTestScenario;
import jp.co.company.space.utils.shared.testScenario.TestScenario;
import jp.co.company.space.utils.shared.testScenario.TestScenarioException;

/**
 * A {@link TestScenario} with a persisted voyage.
 * Nested scenarios: {@link PersistedSpaceShuttleTestScenario} and {@link PersistedRouteTestScenario}
 */
public class PersistedVoyageTestScenario implements TestScenario {

    @Inject
    private PersistedSpaceShuttleTestScenario persistedSpaceShuttleTestScenario;

    @Inject
    private PersistedRouteTestScenario persistedRouteTestScenario;

    @Inject
    private VoyageRepository repository;

    private Voyage persistedVoyage;

    protected PersistedVoyageTestScenario() {
    }

    @Override
    public void setup() {
        try {
            persistedRouteTestScenario.setup();
            persistedSpaceShuttleTestScenario.setup();

            Voyage voyage = new VoyageTestDataBuilder()
                    .withSpaceShuttle(persistedSpaceShuttleTestScenario.getPersistedSpaceShuttle())
                    .withRoute(persistedRouteTestScenario.getPersistedRoute())
                    .create();

            persistedVoyage = repository.save(voyage);
        } catch (VoyageException exception) {
            throw new TestScenarioException("Failed to setup a voyage test scenario", exception);
        }
    }

    public Voyage getPersistedVoyage() {
        return persistedVoyage;
    }

    public SpaceShuttle getPersistedSpaceShuttle() {
        return persistedVoyage.getSpaceShuttle();
    }
}
