package jp.co.company.space.api.features.spaceShuttle.service;

import org.junit.jupiter.api.Test;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;

/**
 * Unit tests for the {@link SpaceShuttleService} class.
 */
@HelidonTest
public class SpaceShuttleServiceTest {

    /**
     * The service to test.
     */
    @Inject
    private SpaceShuttleService service;

    @Test
    void whenCreated_dataIsLoaded() {
        // When
        service.getAll();
    }
}
