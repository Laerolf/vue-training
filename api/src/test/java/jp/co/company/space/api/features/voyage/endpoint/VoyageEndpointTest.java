package jp.co.company.space.api.features.voyage.endpoint;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.voyage.dto.VoyageBasicDto;
import jp.co.company.space.api.features.voyage.dto.VoyageDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link VoyageEndpoint} class.
 */
@HelidonTest
public class VoyageEndpointTest {

    private final static String JAPAN_SPACE_STATION_ID = "00000000-0000-1000-8000-000000000003";
    private final static String MARS_SPACE_STATION_ID = "00000000-0000-1000-8000-000000000012";

    @Inject
    private WebTarget webTarget;

    /**
     * Tests the properties of a {@link VoyageBasicDto} instance.
     * @param voyageDto The voyage to test.
     */
    private void testVoyageBasicDto(VoyageBasicDto voyageDto) {
        assertNotNull(voyageDto);

        assertNotNull(voyageDto.id);
        assertNotNull(voyageDto.departureDate);
        assertNotNull(voyageDto.arrivalDate);
        assertNotNull(voyageDto.routeId);
        assertNotNull(voyageDto.originId);
        assertNotNull(voyageDto.destinationId);
        assertTrue(voyageDto.duration > 0);
        assertNotNull(voyageDto.spaceShuttleId);
        assertNotNull(voyageDto.status);
    }

    @Test
    void getAllVoyages() {
        // When
        Response response = webTarget.path("/voyages").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<VoyageBasicDto> voyages = response.readEntity(new GenericType<>() {});
        assertNotNull(voyages);

        testVoyageBasicDto(voyages.getFirst());
    }

    @Test
    void getVoyageById() {
        // Given
        String voyageId = "5f485136-20a8-41f3-9073-4156d32c9c36";

        // When
        Response response = webTarget.path(String.format("/voyages/%s", voyageId)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        VoyageDto voyage = response.readEntity(VoyageDto.class);
        assertNotNull(voyage);

        assertEquals(voyageId, voyage.id);
        assertNotNull(voyage.departureDate);
        assertNotNull(voyage.arrivalDate);
        assertTrue(voyage.duration > 0);
        assertNotNull(voyage.spaceShuttle);
        assertNotNull(voyage.route);
        assertNotNull(voyage.status);
    }

    @Test
    void getVoyagesFrom() {
        // When
       Response response = webTarget.path(String.format("/voyages/from/%s", JAPAN_SPACE_STATION_ID)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<VoyageBasicDto> voyages = response.readEntity(new GenericType<>() {});
        assertNotNull(voyages);

        voyages.forEach(voyage -> assertEquals(JAPAN_SPACE_STATION_ID, voyage.originId));

        testVoyageBasicDto(voyages.getFirst());
    }

    @Test
    void getVoyagesTo() {
        // When
        Response response = webTarget.path(String.format("/voyages/to/%s", MARS_SPACE_STATION_ID)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<VoyageBasicDto> voyages = response.readEntity(new GenericType<>() {});
        assertNotNull(voyages);

        voyages.forEach(voyage -> assertEquals(MARS_SPACE_STATION_ID, voyage.destinationId));

        testVoyageBasicDto(voyages.getFirst());
    }

    @Test
    void getVoyagesFromTo() {
        // When
        Response response = webTarget.path(String.format("/voyages/from/%s/to/%s", JAPAN_SPACE_STATION_ID, MARS_SPACE_STATION_ID)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<VoyageBasicDto> voyages = response.readEntity(new GenericType<>() {});
        assertNotNull(voyages);

        voyages.forEach(voyage -> {
            assertEquals(JAPAN_SPACE_STATION_ID, voyage.originId);
            assertEquals(MARS_SPACE_STATION_ID, voyage.destinationId);
        });

        testVoyageBasicDto(voyages.getFirst());
    }
}
