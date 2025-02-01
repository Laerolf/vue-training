package jp.co.company.space.api.features.spaceShuttleModel.endpoint;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;

/**
 * Unit test for the {@link SpaceShuttleModelEndpoint} class.
 */
@HelidonTest
public class SpaceShuttleModelEndpointTest {

    /**
     * The target to test the endpoint with.
     */
    @Inject
    private WebTarget target;

    @Test
    void findSpaceShuttleModelById() {
        // When
        SpaceShuttleModel spaceShuttleModel = target.path("space-shuttle-model/00000000-0000-1000-8000-000000000001")
                .request().get(SpaceShuttleModel.class);

        // Then
        assertNotNull(spaceShuttleModel);
    }

    @Test
    void getAllSpaceShuttleModels() {
        // When
        List<?> allSpaceShuttleModels = target.path("space-shuttle-model").request().get(List.class);

        // Then
        assertTrue(!allSpaceShuttleModels.isEmpty());
    }
}
