package jp.co.company.space.api.features.spaceShuttle.domain;

import jp.co.company.space.api.features.pod.domain.Pod;
import jp.co.company.space.api.features.pod.domain.PodReservation;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
     * Gets a {@link List} of all {@link Pod} instances in the space shuttle layout.
     *
     * @param podReservations A list of reserved pods.
     * @return A {@link List} of all {@link Pod} instances.
     */
    public List<Pod> getAllPods(List<PodReservation> podReservations) {
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
