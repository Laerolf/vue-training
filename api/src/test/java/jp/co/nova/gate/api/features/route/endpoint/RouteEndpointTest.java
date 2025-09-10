package jp.co.nova.gate.api.features.route.endpoint;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import jp.co.nova.gate.api.features.route.dto.RouteBasicDto;
import jp.co.nova.gate.api.features.route.dto.RouteDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link RouteEndpoint} class.
 */
@HelidonTest
public class RouteEndpointTest {

    static final String JAPAN_SPACE_STATION_ID = "00000000-0000-1000-8000-000000000003";
    static final String MARS_SPACE_STATION_ID = "00000000-0000-1000-8000-000000000012";

    /**
     * The target to test the endpoint with.
     */
    @Inject
    private WebTarget target;

    /**
     * Tests the properties of a {@link RouteBasicDto}.
     * @param routeDto The route to test.
     */
    private void testBasicRouteDto(RouteBasicDto routeDto) {
        assertNotNull(routeDto);

        assertNotNull(routeDto.id);
        assertNotNull(routeDto.originId);
        assertNotNull(routeDto.destinationId);
        assertNotNull(routeDto.shuttleModelId);
    }

    @Test
    void getAllRoutes() {
        // When
        Response response = target.path("routes").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<RouteBasicDto> routes = response.readEntity(new GenericType<>() {});
        assertNotNull(routes);
        assertFalse(routes.isEmpty());

        testBasicRouteDto(routes.getFirst());
    }

    @Test
    void findRouteById() {
        // Given
        String selectedSpaceRouteId = "08bd4df1-97eb-4a9a-aa21-d9b7bed299a3";

        // When
        Response response = target.path(String.format("routes/%s", selectedSpaceRouteId)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        RouteDto foundRoute = response.readEntity(RouteDto.class);
        assertNotNull(foundRoute);

        assertEquals(selectedSpaceRouteId, foundRoute.id);

        assertNotNull(foundRoute.origin);
        assertEquals(JAPAN_SPACE_STATION_ID, foundRoute.origin.id);

        assertNotNull(foundRoute.destination);
        assertEquals(MARS_SPACE_STATION_ID, foundRoute.destination.id);

        assertNotNull(foundRoute.shuttleModel);
    }
}
