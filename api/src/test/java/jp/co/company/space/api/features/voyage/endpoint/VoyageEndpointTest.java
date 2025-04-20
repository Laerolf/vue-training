package jp.co.company.space.api.features.voyage.endpoint;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.catalog.domain.PodType;
import jp.co.company.space.api.features.pod.domain.PodCodeFactory;
import jp.co.company.space.api.features.pod.domain.PodStatus;
import jp.co.company.space.api.features.pod.dto.PodDto;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.features.voyage.dto.VoyageBasicDto;
import jp.co.company.space.api.features.voyage.dto.VoyageDto;
import jp.co.company.space.utils.features.spaceShuttle.SpaceShuttleTestDataBuilder;
import jp.co.company.space.utils.features.spaceShuttleModel.SpaceShuttleModelTestDataBuilder;
import jp.co.company.space.utils.features.user.UserTestDataBuilder;
import jp.co.company.space.utils.features.voyage.user.VoyageTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttleLayoutFactory.DISTRIBUTION_RATIOS_BY_TYPE;
import static jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttleLayoutFactory.MAX_PODS_PER_DECK_BY_TYPE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link VoyageEndpoint} class.
 */
@Transactional(Transactional.TxType.REQUIRED)
@HelidonTest
public class VoyageEndpointTest {

    private final static String JAPAN_SPACE_STATION_ID = "00000000-0000-1000-8000-000000000003";
    private final static String MARS_SPACE_STATION_ID = "00000000-0000-1000-8000-000000000012";

    private final static SpaceShuttleModel SPACE_SHUTTLE_MODEL = new SpaceShuttleModelTestDataBuilder().withMaxCapacity(20).create();
    private final static SpaceShuttle SPACE_SHUTTLE = new SpaceShuttleTestDataBuilder().withModel(SPACE_SHUTTLE_MODEL).create();

    private final static User USER = new UserTestDataBuilder().create();
    private final static Voyage VOYAGE = new VoyageTestDataBuilder().withSpaceShuttle(SPACE_SHUTTLE).create();
    private final static Booking BOOKING = Booking.create(USER, VOYAGE);

    @Inject
    private WebTarget target;

    @PersistenceContext(unitName = "domain")
    EntityManager entityManager;

    @BeforeEach
    void beforeEach() {
        if (entityManager.find(User.class, USER.getId()) == null) {
            entityManager.merge(USER);
        }

        if (entityManager.find(SpaceShuttleModel.class, VOYAGE.getSpaceShuttle().getModel().getId()) == null) {
            entityManager.merge(VOYAGE.getSpaceShuttle().getModel());
        }

        if (entityManager.find(Booking.class, BOOKING.getId()) == null) {
            entityManager.merge(VOYAGE.getSpaceShuttle());
            entityManager.merge(VOYAGE.getSpaceShuttle().getModel());
            entityManager.merge(VOYAGE.getRoute().getOrigin().getLocation());
            entityManager.merge(VOYAGE.getRoute().getOrigin());
            entityManager.merge(VOYAGE.getRoute().getDestination().getLocation());
            entityManager.merge(VOYAGE.getRoute().getDestination());
            entityManager.merge(VOYAGE.getRoute().getShuttleModel());
            entityManager.merge(VOYAGE.getRoute());
            entityManager.merge(VOYAGE);

            entityManager.merge(BOOKING);
        }
    }

    /**
     * Tests the properties of a {@link VoyageBasicDto} instance.
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
     * Tests the sizes of a collection of {@link PodDto} instances.
     *
     * @param pods The pod DTOs to test.
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
        Response response = target.path(String.format("/voyages/%s/pods", VOYAGE.getId())).request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<PodDto> pods = response.readEntity(new GenericType<>() {
        });
        assertNotNull(pods);
        assertFalse(pods.isEmpty());
        assertEquals(SPACE_SHUTTLE_MODEL.getMaxCapacity(), pods.size());

        List.of(
                PodType.STANDARD_POD,
                PodType.ENHANCED_POD,
                PodType.PRIVATE_SUITE_POD
        ).forEach(expectedPodType -> {
            AtomicInteger podCount = new AtomicInteger(1);

            pods.stream()
                    .filter(podDto -> podDto.type.equals(expectedPodType.getKey()))
                    .forEach(podDto -> {
                        String expectedPodCode = new PodCodeFactory(expectedPodType.getPodCodePrefix(), podDto.deck, podCount.getAndIncrement()).create();

                        assertEquals(expectedPodCode, podDto.code);
                        assertEquals(expectedPodType.getKey(), podDto.type);
                        assertEquals(PodStatus.AVAILABLE.getKey(), podDto.status);
                        assertTrue(podDto.row > 0);
                        assertTrue(podDto.column > 0);
                    });
        });
    }

    @Test
    void decksShouldHaveAPodMax() {
        // Given
        String voyageId = "5f485136-20a8-41f3-9073-4156d32c9c36";

        // When
        Response response = target.path(String.format("/voyages/%s/pods", voyageId)).request().get();

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
