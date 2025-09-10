package jp.co.nova.gate.api.features.pod.domain;

/**
 * An enum representing the status of a space shuttle pod.
 */
public enum PodStatus {
    AVAILABLE("available"),
    UNAVAILABLE("unavailable");

    /**
     * The prefix used for the status's label.
     */
    public final static String LABEL_PREFIX = "podStatus";

    /**
     * The key of the pod status.
     */
    private final String key;

    /**
     * The label of the pod status.
     */
    private final String label;

    PodStatus(String key) {
        this.key = key;
        this.label = String.join(".", LABEL_PREFIX, this.key);
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }
}
