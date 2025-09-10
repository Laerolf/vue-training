package jp.co.company.space.api.features.spaceStation.endpoint;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.spaceStation.dto.SpaceStationBasicDto;
import jp.co.company.space.api.features.spaceStation.dto.SpaceStationDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    /**
     * Tests the properties of a {@link SpaceStationBasicDto}.
     *
     * @param spaceStationDto The space station to test.
     */
    private void testSpaceStationBasicDto(SpaceStationBasicDto spaceStationDto) {
        assertNotNull(spaceStationDto);

        assertNotNull(spaceStationDto.id);
        assertNotNull(spaceStationDto.code);
        assertNotNull(spaceStationDto.name);
        assertNotNull(spaceStationDto.country);
        assertNotNull(spaceStationDto.locationId);
    }

    @Test
    void getAllStations() {
        // When
        Response response = target.path("space-stations").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<SpaceStationBasicDto> foundSpaceStations = response.readEntity(new GenericType<>() {
        });
        assertNotNull(foundSpaceStations);
        assertFalse(foundSpaceStations.isEmpty());

        testSpaceStationBasicDto(foundSpaceStations.getFirst());
    }

    @Test
    void findSpaceStationById() {
        // Given
        String selectedSpaceStationId = "00000000-0000-1000-8000-000000000001";

        // When
        Response response = target.path(String.format("space-stations/%s", selectedSpaceStationId)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        SpaceStationDto spaceStationDto = response.readEntity(SpaceStationDto.class);
        assertNotNull(spaceStationDto);

        assertEquals(selectedSpaceStationId, spaceStationDto.id);
        assertNotNull(spaceStationDto.code);
        assertNotNull(spaceStationDto.name);
        assertNotNull(spaceStationDto.country);
        assertNotNull(spaceStationDto.location);
    }
}
