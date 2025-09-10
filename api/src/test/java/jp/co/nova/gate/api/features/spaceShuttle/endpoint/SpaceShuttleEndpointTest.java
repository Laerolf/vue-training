package jp.co.nova.gate.api.features.spaceShuttle.endpoint;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import jp.co.nova.gate.api.features.spaceShuttle.dto.SpaceShuttleBasicDto;
import jp.co.nova.gate.api.features.spaceShuttle.dto.SpaceShuttleDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    /**
     * Tests the properties of a {@link SpaceShuttleBasicDto}.
     * @param spaceShuttleDto The space shuttle to test.
     */
    private void testSpaceShuttleBasicDto(SpaceShuttleBasicDto spaceShuttleDto) {
        assertNotNull(spaceShuttleDto);

        assertNotNull(spaceShuttleDto.id);
        assertNotNull(spaceShuttleDto.name);
        assertNotNull(spaceShuttleDto.modelId);
    }

    @Test
    void findSpaceShuttleById() {
        // Given
        String selectedSpaceShuttleId = "00000000-0000-1000-8000-000000000001";

        // When
        Response response = target.path(String.format("space-shuttles/%s", selectedSpaceShuttleId)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        SpaceShuttleDto spaceShuttleDto = response.readEntity(SpaceShuttleDto.class);
        assertNotNull(spaceShuttleDto);

        assertEquals(selectedSpaceShuttleId, spaceShuttleDto.id);
        assertNotNull(spaceShuttleDto.name);
        assertNotNull(spaceShuttleDto.model);
    }

    @Test
    void getAllShuttles() {
        // When
        Response response = target.path("space-shuttles").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<SpaceShuttleBasicDto> foundSpaceShuttles = response.readEntity(new GenericType<>() {});
        assertNotNull(foundSpaceShuttles);
        assertFalse(foundSpaceShuttles.isEmpty());

        testSpaceShuttleBasicDto(foundSpaceShuttles.getFirst());
    }

}
