package jp.co.company.space.api.features.spaceShuttle.endpoint;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;

/**
 * Unit tests for the {@link SpaceShuttleEndpoint} class.
 */
@HelidonTest
public class SpaceShuttleEndpointTest {

    /**
     * The target to test the endpoint with.
     */
    @Inject
    private WebTarget target;

    @Test
    void findSpaceShuttleById() {
        // When
        SpaceShuttle spaceShuttle = target.path("space-shuttle/00000000-0000-1000-8000-000000000001").request()
                .get(SpaceShuttle.class);

        // Then
        assertNotNull(spaceShuttle);
    }

    @Test
    void getAllShuttles() {
        // When
        List<?> allSpaceShuttles = target.path("space-shuttle").request().get(List.class);

        // Then
        assertTrue(!allSpaceShuttles.isEmpty());
    }

}
