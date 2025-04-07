package jp.co.company.space.api.features.location.endpoint;

import java.util.List;
import java.util.Optional;

import io.helidon.http.Status;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.location.dto.LocationDto;
import org.junit.jupiter.api.Test;

import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;

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

    private void testLocationDto(LocationDto location) {
        assertNotNull(location);

        assertNotNull(location.id);
        assertNotNull(location.name);
        assertEquals(0, location.latitude);
        assertNotEquals(0, location.longitude);
        assertNotEquals(0, location.radialDistance);
    }

    @Test
    void getAllLocations() {
        // When
        Response response = target.path("locations").request().get();

        // Then
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
        assertEquals(Status.OK_200.code(), response.getStatus());

        Optional<LocationDto> optionalLocation = Optional.ofNullable(response.readEntity(LocationDto.class));
        assertFalse(optionalLocation.isEmpty());

        testLocationDto(optionalLocation.get());
    }
}
