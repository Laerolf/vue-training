package jp.co.company.space.api.features.route.endpoint;

import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;

/**
 * Unit tests for the {@link RouteEndpoint} class.
 */
@HelidonTest
public class RouteEndpointTest {
    /**
     * The target to test the endpoint with.
     */
    @Inject
    private WebTarget target;

    @Test
    void getAllRoutes() {
        // When
        List<?> routes = target.path("route").request().get(List.class);

        // Then
        assertFalse(routes.isEmpty());
    }
}
