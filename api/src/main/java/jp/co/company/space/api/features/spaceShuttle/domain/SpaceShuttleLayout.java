package jp.co.company.space.api.features.spaceShuttle.domain;

import jp.co.company.space.api.features.catalog.domain.PackageType;
import jp.co.company.space.api.features.catalog.domain.PodType;
import jp.co.company.space.api.features.passenger.domain.Passenger;
import jp.co.company.space.api.features.pod.domain.Pod;
import jp.co.company.space.api.features.pod.domain.PodReservation;

import java.util.*;

/**
 * A POJO representing a layout of a {@link SpaceShuttle} instance.
 */
public class SpaceShuttleLayout {

    /**
     * Creates a {@link SpaceShuttleLayout} instance.
     *
     * @param podsPerDeck The pods per deck of the space shuttle layout.
     * @return A {@link SpaceShuttleLayout} instance.
     */
    public static SpaceShuttleLayout create(Map<Integer, List<Pod>> podsPerDeck) {
        return new SpaceShuttleLayout(podsPerDeck);
    }

    /**
     * The pods per deck in the space shuttle layout.
     */
    private final Map<Integer, List<Pod>> podsPerDeck;

    public SpaceShuttleLayout(Map<Integer, List<Pod>> podsPerDeck) {
        if (podsPerDeck == null) {
            throw new IllegalArgumentException("The pods per deck of the space shuttle layout is missing.");
        } else if (podsPerDeck.isEmpty()) {
            throw new IllegalArgumentException("The pods per deck of the space shuttle layout is invalid.");
        }

        this.podsPerDeck = podsPerDeck;
    }

    /**
     * Gets a {@link List} of all {@link Pod} instances in the space shuttle layout.
     *
     * @return A {@link List} of {@link Pod} instances.
     */
    public List<Pod> getAllPods() {
        return podsPerDeck.values().stream().flatMap(Collection::stream).toList();
    }

    /**
     * Gets a {@link List} of all {@link Pod} instances in the space shuttle layout for the provided {@link PackageType} instance.
     *
     * @param packageType The {@link PackageType} instance to search with.
     * @return A {@link List} of {@link Pod} instances.
     */
    public List<Pod> getAllPodsByPackageType(PackageType packageType) {
        PodType selectedPodType = PodType.findByPackageType(packageType).orElseThrow();
        return getAllPods().stream().filter(pod -> pod.getType().equals(selectedPodType)).toList();
    }

    /**
     * Gets a {@link List} of all {@link Pod} instances in the space shuttle layout.
     *
     * @param podReservations A list of reserved pods.
     * @return A {@link List} of all {@link Pod} instances.
     */
    public List<Pod> getAllPodsWithAvailability(List<PodReservation> podReservations) {
        return getAllPods().stream().peek(pod -> {
            if (podReservations.stream().anyMatch(podReservation -> podReservation.getPodCode().equals(pod.getCode()))) {
                pod.markAsUnavailable();
            }
        }).toList();
    }

    /**
     * Gets a {@link List} of all {@link Pod} instances on the provided deck in the space shuttle layout.
     *
     * @param deck The deck to search for pods.
     * @return A {@link List} of {@link Pod} instances.
     */
    public List<Pod> getAllPodsByDeck(int deck) {
        return podsPerDeck.get(deck);
    }

    /**
     * Tests whether a {@link PodReservation} instance exists for the provided pod code.
     *
     * @param podCode      The pod code to search for.
     * @param reservations The existing pod reservations.
     * @return True when the pod code does not match a reservation, false otherwise.
     */
    public boolean isPodAvailable(String podCode, List<PodReservation> reservations) {
        return reservations.stream().noneMatch(podReservation -> podReservation.getPodCode().equals(podCode));
    }

    /**
     * Tests whether a {@link Pod} instance matching the provided pod code is available for the provided {@link Passenger} instance.
     *
     * @param podCode     The pod code to search for.
     * @param passenger The {@link Passenger} instance to search with.
     * @return True when the provided pod code matches a {@link Pod} instance that is available for the provided {@link PackageType} instance, false otherwise.
     */
    public boolean isPodAvailableForPassenger(String podCode, Passenger passenger) {
        return getAllPodsByPackageType(passenger.getPackageType()).stream().anyMatch(pod -> pod.getCode().equals(podCode));
    }

    /**
     * Returns the next available {@link Pod} instance.
     *
     * @param passenger    The {@link Passenger} instance to search for.
     * @param reservations A list of reserved pods.
     * @return An {@link Optional} {@link Pod} instance.
     */
    public Optional<Pod> findFirstAvailablePodFor(Passenger passenger, List<PodReservation> reservations) {
        PodType selectedPodType = PodType.findByPackageType(passenger.getPackageType()).orElseThrow();
        return getAllPods().stream().filter(pod -> pod.getType().equals(selectedPodType) && isPodAvailable(pod.getCode(), reservations)).findFirst();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SpaceShuttleLayout that = (SpaceShuttleLayout) o;
        return Objects.equals(podsPerDeck, that.podsPerDeck);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(podsPerDeck);
    }
}
