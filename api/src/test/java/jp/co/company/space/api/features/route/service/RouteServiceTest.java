package jp.co.company.space.api.features.route.service;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;

/**
 * Unit tests for the {@link RouteService} class.
 */
@HelidonTest
public class RouteServiceTest {
    /**
     * The service to test.
     */
    @Inject
    private RouteService service;

    @Test
    void whenCreated_dataIsLoaded() {
        // When
        service.getAll();

        // Then
        assertFalse(service.getAll().isEmpty());
    }
}
