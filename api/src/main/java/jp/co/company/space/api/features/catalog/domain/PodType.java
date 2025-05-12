package jp.co.company.space.api.features.catalog.domain;

import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * An enum representing a pod type for a passenger's booking.
 */
public enum PodType implements CatalogItem {
    STANDARD_POD("standardPod", "S", PackageType.ECONOMY),
    ENHANCED_POD("enhancedPod", "E", PackageType.FIRST_CLASS),
    PRIVATE_SUITE_POD("privateSuitePod", "PS", PackageType.BUSINESS);

    private static final String CATALOG_PREFIX = "podType";

    /**
     * The key of the package type.
     */
    private final String key;

    /**
     * The label of the package type.
     */
    private final String label;

    /**
     * The prefix for the pod code.
     */
    private final String podCodePrefix;

    /**
     * The minimum package type for the pod type.
     */
    private final PackageType availableFrom;

    PodType(String key, String podCodePrefix, PackageType availableFrom) {
        this.key = key;
        this.label = new StringJoiner(".").add(CATALOG_PREFIX).add(key).toString();
        this.podCodePrefix = podCodePrefix;
        this.availableFrom = availableFrom;
    }

    /**
     * Finds a {@link PodType} instance by a {@link PackageType} instance.
     *
     * @param packageType The package type to search with.
     * @return An {@link Optional} {@link PodType} instance.
     */
    public static Optional<PodType> findByPackageType(PackageType packageType) {
        return Arrays.stream(values()).filter(podType -> podType.availableFrom.equals(packageType)).findFirst();
    }

    /**
     * Finds a {@link PodType} instance by its key.
     *
     * @param key The key to search with.
     * @return An {@link Optional} {@link PodType} instance.
     */
    public static Optional<PodType> findByKey(String key) {
        return Arrays.stream(values()).filter(podType -> podType.key.equals(key)).findFirst();
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public String getPodCodePrefix() {
        return podCodePrefix;
    }

    public PackageType getAvailableFrom() {
        return availableFrom;
    }
}
