package jp.co.company.space.api.features.location.endpoint;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.location.dto.LocationDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    /**
     * Tests the properties of a {@link LocationDto} instance.
     * @param locationDto The location to test.
     */
    private void testLocationDto(LocationDto locationDto) {
        assertNotNull(locationDto);

        assertNotNull(locationDto.id);
        assertNotNull(locationDto.name);
        assertEquals(0, locationDto.latitude);
        assertNotEquals(0, locationDto.longitude);
        assertNotEquals(0, locationDto.radialDistance);
    }

    @Test
    void getAllLocations() {
        // When
        Response response = target.path("locations").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<LocationDto> locations = response.readEntity(new GenericType<>() {});
        assertNotNull(locations);

        testLocationDto(locations.getFirst());
    }

    @Test
    void findLocationById() {
        // Given
        String locationId = "00000000-0000-1000-8000-000000000001";

        // When
        Response response = target.path(String.format("locations/%s", locationId)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        LocationDto foundLocation = response.readEntity(LocationDto.class);
        assertNotNull(foundLocation);

        assertEquals(locationId, foundLocation.id);
        testLocationDto(foundLocation);
    }
}
