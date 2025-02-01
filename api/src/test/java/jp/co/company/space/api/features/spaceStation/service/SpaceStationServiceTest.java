package jp.co.company.space.api.features.spaceStation.service;

import org.junit.jupiter.api.Test;

import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;

/**
 * Unit tests for the {@link SpaceStationService} class.
 */
@HelidonTest
public class SpaceStationServiceTest {
    /**
     * The service to test.
     */
    @Inject
    private SpaceStationService service;

    @Test
    void whenCreated_dataIsLoaded() {
        // When
        service.getAll();
    }
}
