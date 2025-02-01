package jp.co.company.space.api.features.location.endpoint;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;
import jp.co.company.space.api.features.location.domain.Location;

/**
 * Unit tests for the {@link LocationEndpoint} class.
 */
@HelidonTest
public class LocationEndpointTest {
    /**
     * The target to test the endpoint with.
     */
    @Inject
    private WebTarget target;

    @Test
    void findLocationById() {
        // When
        Location location = target.path("location/00000000-0000-1000-8000-000000000001").request().get(Location.class);

        // Then
        assertNotNull(location);
    }

    @Test
    void getAllLocations() {
        // When
        List<?> allLocations = target.path("location").request().get(List.class);

        // Then
        assertTrue(!allLocations.isEmpty());
    }
}
