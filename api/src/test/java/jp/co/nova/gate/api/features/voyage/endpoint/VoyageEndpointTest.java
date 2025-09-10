package jp.co.nova.gate.api.features.voyage.endpoint;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.AddBean;
import io.helidon.microprofile.testing.junit5.AddBeans;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jp.co.nova.gate.api.features.catalog.domain.PodType;
import jp.co.nova.gate.api.features.pod.dto.PodDto;
import jp.co.nova.gate.api.features.voyage.dto.VoyageBasicDto;
import jp.co.nova.gate.api.features.voyage.dto.VoyageDto;
import jp.co.nova.gate.utils.features.route.PersistedRouteTestScenario;
import jp.co.nova.gate.utils.features.spaceShuttle.PersistedSpaceShuttleTestScenario;
import jp.co.nova.gate.utils.features.spaceShuttleModel.PersistedSpaceShuttleModelTestScenario;
import jp.co.nova.gate.utils.features.user.PersistedUserTestScenario;
import jp.co.nova.gate.utils.features.voyage.PersistedVoyageTestScenario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static jp.co.nova.gate.api.features.spaceShuttle.domain.SpaceShuttleLayoutFactory.DISTRIBUTION_RATIOS_BY_TYPE;
import static jp.co.nova.gate.api.features.spaceShuttle.domain.SpaceShuttleLayoutFactory.MAX_PODS_PER_DECK_BY_TYPE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link VoyageEndpoint} class.
 */
@Transactional(Transactional.TxType.REQUIRED)
@HelidonTest
@AddBeans({
        @AddBean(PersistedVoyageTestScenario.class),
        @AddBean(PersistedUserTestScenario.class),
        @AddBean(PersistedSpaceShuttleTestScenario.class),
        @AddBean(PersistedSpaceShuttleModelTestScenario.class),
        @AddBean(PersistedRouteTestScenario.class)
})
public class VoyageEndpointTest {

    private static final String JAPAN_SPACE_STATION_ID = "00000000-0000-1000-8000-000000000003";
    private static final String MARS_SPACE_STATION_ID = "00000000-0000-1000-8000-000000000012";

    @Inject
    private PersistedVoyageTestScenario persistedVoyageTestScenario;

    @Inject
    private PersistedUserTestScenario persistedUserTestScenario;

    @Inject
    private WebTarget target;

    @BeforeEach
    public void beforeEach() {
        persistedVoyageTestScenario.setup();
        persistedUserTestScenario.setup();
    }

    /**
     * Tests the properties of a {@link VoyageBasicDto}.
     *
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

    /**
     * Tests the sizes of a collection of {@link PodDto}s.
     *
     * @param pods    The pod DTOs to test.
     * @param podType The type of pods to test.
     */
    private void testTypePodDtoCollection(List<PodDto> pods, PodType podType) {
        int expectedTotalAmountOfTypePods = Math.toIntExact(Math.round(pods.size() * DISTRIBUTION_RATIOS_BY_TYPE.get(podType)));
        int expectedAmountOfDecksWithTypePods = expectedTotalAmountOfTypePods / MAX_PODS_PER_DECK_BY_TYPE.get(podType);

        List<PodDto> podsWithType = pods.stream().filter(podDto -> podDto.type.equals(podType.getKey())).toList();
        assertEquals(expectedTotalAmountOfTypePods, podsWithType.size());
        assertEquals(expectedAmountOfDecksWithTypePods, podsWithType.getLast().deck - podsWithType.getFirst().deck);
    }

    @Test
    void getAllVoyages() {
        // When
        Response response = target.path("/voyages").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<VoyageBasicDto> voyages = response.readEntity(new GenericType<>() {
        });
        assertNotNull(voyages);

        testVoyageBasicDto(voyages.getFirst());
    }

    @Test
    void getVoyageById() {
        // Given
        String voyageId = "5f485136-20a8-41f3-9073-4156d32c9c36";

        // When
        Response response = target.path(String.format("/voyages/%s", voyageId)).request().get();

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
        Response response = target.path(String.format("/voyages/from/%s", JAPAN_SPACE_STATION_ID)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<VoyageBasicDto> voyages = response.readEntity(new GenericType<>() {
        });
        assertNotNull(voyages);

        voyages.forEach(voyage -> assertEquals(JAPAN_SPACE_STATION_ID, voyage.originId));

        testVoyageBasicDto(voyages.getFirst());
    }

    @Test
    void getVoyagesTo() {
        // When
        Response response = target.path(String.format("/voyages/to/%s", MARS_SPACE_STATION_ID)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<VoyageBasicDto> voyages = response.readEntity(new GenericType<>() {
        });
        assertNotNull(voyages);

        voyages.forEach(voyage -> assertEquals(MARS_SPACE_STATION_ID, voyage.destinationId));

        testVoyageBasicDto(voyages.getFirst());
    }

    @Test
    void getVoyagesFromTo() {
        // When
        Response response = target.path(String.format("/voyages/from/%s/to/%s", JAPAN_SPACE_STATION_ID, MARS_SPACE_STATION_ID)).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<VoyageBasicDto> voyages = response.readEntity(new GenericType<>() {
        });
        assertNotNull(voyages);

        voyages.forEach(voyage -> {
            assertEquals(JAPAN_SPACE_STATION_ID, voyage.originId);
            assertEquals(MARS_SPACE_STATION_ID, voyage.destinationId);
        });

        testVoyageBasicDto(voyages.getFirst());
    }

    @Test
    void getAllVoyagePods() {
        // When
        Response response = target.path(String.format("/voyages/%s/pods", persistedVoyageTestScenario.getPersistedVoyage().getId())).request()
                .header(HttpHeaders.AUTHORIZATION, persistedUserTestScenario.generateAuthenticationHeader())
                .get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<PodDto> pods = response.readEntity(new GenericType<>() {
        });
        assertNotNull(pods);
        assertFalse(pods.isEmpty());
        assertEquals(persistedVoyageTestScenario.getPersistedSpaceShuttle().getModel().getMaxCapacity(), pods.size());
    }

    @Test
    void decksShouldHaveAPodMax() {
        // Given
        String voyageId = "5f485136-20a8-41f3-9073-4156d32c9c36";

        // When
        Response response = target.path(String.format("/voyages/%s/pods", voyageId)).request()
                .header(HttpHeaders.AUTHORIZATION, persistedUserTestScenario.generateAuthenticationHeader())
                .get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<PodDto> pods = response.readEntity(new GenericType<>() {
        });
        assertNotNull(pods);
        assertFalse(pods.isEmpty());

        testTypePodDtoCollection(pods, PodType.STANDARD_POD);
        testTypePodDtoCollection(pods, PodType.ENHANCED_POD);
        testTypePodDtoCollection(pods, PodType.PRIVATE_SUITE_POD);
    }
}
