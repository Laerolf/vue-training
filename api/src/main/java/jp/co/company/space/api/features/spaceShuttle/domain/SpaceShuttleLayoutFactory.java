package jp.co.company.space.api.features.spaceShuttle.domain;

import jp.co.company.space.api.features.catalog.domain.PodType;
import jp.co.company.space.api.features.pod.domain.Pod;
import jp.co.company.space.api.features.pod.domain.PodCodeFactory;
import jp.co.company.space.api.features.pod.domain.PodStatus;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * A factory that creates a {@link SpaceShuttleLayout} instance.
 */
public class SpaceShuttleLayoutFactory {

    public final static Map<PodType, Double> DISTRIBUTION_RATIOS_BY_TYPE = Map.ofEntries(
            Map.entry(PodType.STANDARD_POD, 0.7),
            Map.entry(PodType.ENHANCED_POD, 0.27),
            Map.entry(PodType.PRIVATE_SUITE_POD, 0.03)
    );

    public final static Map<PodType, Integer> MAX_PODS_PER_DECK_BY_TYPE = Map.ofEntries(
            Map.entry(PodType.STANDARD_POD, 30),
            Map.entry(PodType.ENHANCED_POD, 20),
            Map.entry(PodType.PRIVATE_SUITE_POD, 10)
    );

    private final static Map<PodType, Integer> MAX_ROWS_PER_DECK_BY_TYPE = Map.ofEntries(
            Map.entry(PodType.STANDARD_POD, 3),
            Map.entry(PodType.ENHANCED_POD, 2),
            Map.entry(PodType.PRIVATE_SUITE_POD, 1)
    );

    private final SpaceShuttleModel spaceShuttleModel;

    public SpaceShuttleLayoutFactory(SpaceShuttleModel spaceShuttleModel) {
        this.spaceShuttleModel = spaceShuttleModel;
    }

    /**
     * Creates a {@link SpaceShuttleLayout} instance.
     *
     * @return A {@link SpaceShuttleLayout} instance.
     */
    public SpaceShuttleLayout create() {
        return SpaceShuttleLayout.create(createPodsMapByDeck());
    }

    /**
     * Creates a {@link Map} of {@link Pod} instances for each deck of a space shuttle layout.
     *
     * @return A {@link Map} of {@link Pod} instances.
     */
    private Map<Integer, List<Pod>> createPodsMapByDeck() {
        AtomicInteger deckCounter = new AtomicInteger(1);

        Map<Integer, List<Pod>> podsByDeck = new HashMap<>();

        List<PodType> podTypes = List.of(PodType.STANDARD_POD, PodType.ENHANCED_POD, PodType.PRIVATE_SUITE_POD);

        for (PodType podType : podTypes) {
            double ratio = Optional.ofNullable(DISTRIBUTION_RATIOS_BY_TYPE.get(podType)).orElseThrow();

            int totalPodsForType = Math.toIntExact(Math.round(spaceShuttleModel.getMaxCapacity() * ratio));
            int maxPodsPerDeck = Optional.ofNullable(MAX_PODS_PER_DECK_BY_TYPE.get(podType)).orElseThrow();
            int podsPerRow = Optional.ofNullable(MAX_ROWS_PER_DECK_BY_TYPE.get(podType)).orElseThrow();

            int remainingPods = totalPodsForType;

            while (remainingPods > 0) {
                int podsOnThisDeck = Math.min(maxPodsPerDeck, remainingPods);
                List<Pod> pods = createPodsForDeck(podType, deckCounter.get(), podsOnThisDeck, podsPerRow);

                podsByDeck.put(deckCounter.getAndIncrement(), pods);

                remainingPods -= podsOnThisDeck;
            }
        }
        return podsByDeck;
    }

    /**
     * Creates a {@link List} of {@link Pod} instances for a deck.
     *
     * @param podType    The type of pods.
     * @param deckNumber The current deck number.
     * @param podCount   The current pod count.
     * @param podsPerRow The max amount of pods per row.
     * @return A {@link List} of {@link Pod} instances.
     */
    private List<Pod> createPodsForDeck(PodType podType, int deckNumber, int podCount, int podsPerRow) {
        return IntStream.rangeClosed(1, podCount)
                .mapToObj(indexOnDeck -> {
                    int podRow = (indexOnDeck / podsPerRow) + 1;
                    int podColumn = ((indexOnDeck - 1) % podsPerRow) + 1;

                    String podCode = new PodCodeFactory(podType.getPodCodePrefix(), deckNumber, indexOnDeck).create();
                    return Pod.create(podCode, podType, deckNumber, podRow, podColumn, PodStatus.AVAILABLE);
                })
                .toList();
    }
}
