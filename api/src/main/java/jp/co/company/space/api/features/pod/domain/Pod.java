package jp.co.company.space.api.features.pod.domain;

import jp.co.company.space.api.features.catalog.domain.PodType;

import java.util.Objects;

/**
 * A POJO representing a pod in a space shuttle.
 */
public class Pod {

    /**
     * Creates a {@link Pod} instance.
     *
     * @param code   The code of the pod.
     * @param type   The type of the pod.
     * @param deck   The deck of the pod.
     * @param row    The row of the pod.
     * @param column The column of the pod.
     * @param status The status of the pod.
     * @return A {@link Pod} instance.
     */
    public static Pod create(String code, PodType type, int deck, int row, int column, PodStatus status) {
        return new Pod(code, type, deck, row, column, status);
    }

    /**
     * The code of the pod.
     */
    private final String code;

    /**
     * The type of the pod.
     */
    private final PodType type;

    /**
     * The deck of the pod in the space shuttle layout.
     */
    private final int deck;

    /**
     * The row of the pod in the space shuttle layout.
     */
    private final int row;

    /**
     * The column of the pod in the space shuttle layout.
     */
    private final int column;

    /**
     * The status of the pod.
     */
    private PodStatus status;

    protected Pod(String code, PodType type, int deck, int row, int column, PodStatus status) {
        if (code == null) {
            throw new IllegalArgumentException("The code of the pod is missing.");
        } else if (type == null) {
            throw new IllegalArgumentException("The type of the pod is missing.");
        } else if (deck <= 0) {
            throw new IllegalArgumentException("The deck of the pod is invalid.");
        } else if (row <= 0) {
            throw new IllegalArgumentException("The row of the pod is invalid.");
        } else if (column <= 0) {
            throw new IllegalArgumentException("The row of the pod is invalid.");
        } else if (status == null) {
            throw new IllegalArgumentException("The status of the pod is missing.");
        }

        this.code = code;
        this.type = type;
        this.deck = deck;
        this.row = row;
        this.column = column;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public PodType getType() {
        return type;
    }

    public int getDeck() {
        return deck;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public PodStatus getStatus() {
        return status;
    }

    /**
     * Set the status of this pod to unavailable.
     *
     * @return A {@link Pod} instance.
     */
    public Pod markAsUnavailable() {
        status = PodStatus.UNAVAILABLE;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pod pod = (Pod) o;
        return deck == pod.deck && row == pod.row && column == pod.column && Objects.equals(code, pod.code) && type == pod.type && status == pod.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, type, deck, row, column, status);
    }
}
