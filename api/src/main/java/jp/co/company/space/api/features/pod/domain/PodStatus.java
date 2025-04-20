package jp.co.company.space.api.features.pod.domain;

/**
 * An enum representing the status of a space shuttle pod.
 */
public enum PodStatus {
    AVAILABLE("available"),
    UNAVAILABLE("unavailable");

    /**
     * The key of the pod status.
     */
    private final String key;

    PodStatus(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
