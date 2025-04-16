package jp.co.company.space.api.features.catalog.domain;

/**
 * An enum representing a pod type for a passenger's booking.
 */
public enum PodType {
    STANDARD_POD("standardPod", PackageType.ECONOMY),
    ENHANCED_POD("enhancedPod", PackageType.BUSINESS),
    PRIVATE_SUITE_POD("privateSuitePod", PackageType.FIRST_CLASS);

    /**
     * The key of the package type.
     */
    private final String key;

    /**
     * The minimum package type for the pod type.
     */
    private final PackageType availableFrom;

    PodType(String key, PackageType availableFrom) {
        this.key = key;
        this.availableFrom = availableFrom;
    }

    public String getKey() {
        return key;
    }

    public PackageType getAvailableFrom() {
        return availableFrom;
    }
}
