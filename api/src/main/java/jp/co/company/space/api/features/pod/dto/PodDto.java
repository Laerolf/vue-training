package jp.co.company.space.api.features.pod.dto;

import jp.co.company.space.api.features.pod.domain.Pod;
import jp.co.company.space.api.features.pod.exception.PodError;
import jp.co.company.space.api.features.pod.exception.PodException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.Examples.*;

/**
 * A POJO representing a DTO of pod in a space shuttle.
 */
@Schema(name = "Pod", description = "The details of a pod in a space shuttle.")
public class PodDto {

    /**
     * Creates a {@link PodDto} based on a {@link Pod}.
     *
     * @param pod The base of the {@link PodDto}.
     * @return A {@link PodDto}.
     */
    public static PodDto create(Pod pod) throws PodException {
        return new PodDto(pod.getCode(), pod.getType().getKey(), pod.getDeck(), pod.getRow(), pod.getColumn(), pod.getStatus().getKey());
    }

    /**
     * The code of the pod.
     */
    @Schema(description = "The code of the pod.", example = POD_CODE_EXAMPLE)
    public String code;

    /**
     * The type of the pod.
     */
    @Schema(description = "The type of the pod.", example = POD_TYPE_EXAMPLE)
    public String type;

    /**
     * The deck of the pod in the space shuttle layout.
     */
    @Schema(description = "The deck of the pod.", example = POD_DECK_NUMBER_EXAMPLE)
    public int deck;

    /**
     * The row of the pod in the space shuttle layout.
     */
    @Schema(description = "The row of the pod.", example = POD_ROW_NUMBER_EXAMPLE)
    public int row;

    /**
     * The column of the pod in the space shuttle layout.
     */
    @Schema(description = "The column of the pod.", example = POD_COLUMN_NUMBER_EXAMPLE)
    public int column;

    /**
     * The status of the pod.
     */
    @Schema(description = "The status of the pod.", example = POD_STATUS_EXAMPLE)
    public String status;

    protected PodDto() {
    }

    protected PodDto(String code, String type, int deck, int row, int column, String status) throws PodException {
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
}
