package jp.co.company.space.api.features.spaceShuttleModel.service;

import org.junit.jupiter.api.Test;

import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;

/**
 * Unit tests for the {@link SpaceShuttleModelService} class.
 */
@HelidonTest
public class SpaceShuttleModelServiceTest {
    /**
     * The service to test.
     */
    @Inject
    private SpaceShuttleModelService service;

    @Test
    void whenCreated_dataIsLoaded() {
        // When
        service.getAll();
    }
}
