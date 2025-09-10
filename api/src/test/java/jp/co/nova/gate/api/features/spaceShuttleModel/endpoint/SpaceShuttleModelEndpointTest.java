package jp.co.nova.gate.api.features.spaceShuttleModel.endpoint;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import jp.co.nova.gate.api.features.spaceShuttle.dto.SpaceShuttleBasicDto;
import jp.co.nova.gate.api.features.spaceShuttleModel.dto.SpaceShuttleModelDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    /**
     * Tests the properties of a {@link SpaceShuttleModelDto}.
     * @param spaceShuttleModelDto The route to test.
     */
    private void testSpaceShuttleModelDto(SpaceShuttleModelDto spaceShuttleModelDto) {
        assertNotNull(spaceShuttleModelDto);

        assertNotNull(spaceShuttleModelDto.id);
        assertNotNull(spaceShuttleModelDto.name);
        assertTrue(spaceShuttleModelDto.maxSpeed > 0);
    }

    /**
     * Tests the properties of a {@link SpaceShuttleBasicDto}.
     * @param spaceShuttleDto The route to test.
     */
    private void testSpaceShuttleDto(SpaceShuttleBasicDto spaceShuttleDto) {
        assertNotNull(spaceShuttleDto);

        assertNotNull(spaceShuttleDto.id);
        assertNotNull(spaceShuttleDto.name);
        assertNotNull(spaceShuttleDto.modelId);
    }

    @Test
    void getAllSpaceShuttleModels() {
        // When
        Response response = target.path("space-shuttle-models").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<SpaceShuttleModelDto> foundSpaceShuttleModels = response.readEntity(new GenericType<>() {});
        assertNotNull(foundSpaceShuttleModels);
        assertFalse(foundSpaceShuttleModels.isEmpty());

        testSpaceShuttleModelDto(foundSpaceShuttleModels.getFirst());
    }

    @Test
    void findSpaceShuttleModelById() {
        // Given
        String selectedSpaceShuttleModelId = "00000000-0000-1000-8000-000000000001";

        // When
        Response response = target.path(String.format("space-shuttle-models/%s", selectedSpaceShuttleModelId)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        SpaceShuttleModelDto foundSpaceShuttleModel = response.readEntity(SpaceShuttleModelDto.class);
        assertNotNull(foundSpaceShuttleModel);

        assertEquals(selectedSpaceShuttleModelId, foundSpaceShuttleModel.id);
        testSpaceShuttleModelDto(foundSpaceShuttleModel);
    }

    @Test
    void getAllSpaceShuttlesByModel() {
        // Given
        String selectedSpaceShuttleModelId = "00000000-0000-1000-8000-000000000001";

        // When
        Response response = target.path(String.format("space-shuttle-models/%s/space-shuttles", selectedSpaceShuttleModelId)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<SpaceShuttleBasicDto> foundSpaceShuttles = response.readEntity(new GenericType<>() {});
        assertNotNull(foundSpaceShuttles);
        assertFalse(foundSpaceShuttles.isEmpty());

        testSpaceShuttleDto(foundSpaceShuttles.getFirst());
    }
}
