package jp.co.company.space.api.features.spaceStation.endpoint;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;

/**
 * Unit tests for the {@link SpaceStationEndpoint} class.
 */
@HelidonTest
public class SpaceStationEndpointTest {
    /**
     * The target to test the endpoint with.
     */
    @Inject
    private WebTarget target;

    @Test
    void findSpaceStationById() {
        // When
        SpaceShuttle spaceStation = target.path("space-station/00000000-0000-1000-8000-000000000001").request()
                .get(SpaceShuttle.class);

        // Then
        assertNotNull(spaceStation);
    }

    @Test
    void getAllStations() {
        // When
        List<?> allSpaceStations = target.path("space-station").request().get(List.class);

        // Then
        assertTrue(!allSpaceStations.isEmpty());
    }
}
