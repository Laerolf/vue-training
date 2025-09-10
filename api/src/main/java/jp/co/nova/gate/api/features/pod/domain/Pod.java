package jp.co.nova.gate.api.features.pod.domain;

import jp.co.nova.gate.api.features.catalog.domain.PodType;
import jp.co.nova.gate.api.features.pod.exception.PodError;
import jp.co.nova.gate.api.features.pod.exception.PodException;

import java.util.Objects;

/**
 * A POJO representing a pod in a space shuttle.
 */
public class Pod {

    /**
     * Creates a {@link Pod}.
     *
     * @param code   The code of the pod.
     * @param type   The type of the pod.
     * @param deck   The deck of the pod.
     * @param row    The row of the pod.
     * @param column The column of the pod.
     * @param status The status of the pod.
     * @return A {@link Pod}.
     */
    public static Pod create(String code, PodType type, int deck, int row, int column, PodStatus status) throws PodException {
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

    protected Pod(String code, PodType type, int deck, int row, int column, PodStatus status) throws PodException {
        if (code == null) {
            throw new PodException(PodError.MISSING_CODE);
        } else if (type == null) {
            throw new PodException(PodError.MISSING_TYPE);
        } else if (deck <= 0) {
            throw new PodException(PodError.INVALID_DECK_NUMBER);
        } else if (row <= 0) {
            throw new PodException(PodError.INVALID_ROW_NUMBER);
        } else if (column <= 0) {
            throw new PodException(PodError.INVALID_COLUMN_NUMBER);
        } else if (status == null) {
            throw new PodException(PodError.MISSING_STATUS);
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
     */
    public void markAsUnavailable() {
        status = PodStatus.UNAVAILABLE;
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
