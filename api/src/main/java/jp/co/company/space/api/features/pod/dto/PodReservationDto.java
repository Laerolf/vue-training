package jp.co.company.space.api.features.pod.dto;

import jp.co.company.space.api.features.pod.domain.PodReservation;
import jp.co.company.space.api.features.pod.exception.PodReservationError;
import jp.co.company.space.api.features.pod.exception.PodReservationException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.ZonedDateTime;

import static jp.co.company.space.api.shared.openApi.Examples.*;

/**
 * A POJO representing a DTO of {@link PodReservation}.
 */
@Schema(name = "PodReservation", description = "The details of a pod reservation.")
public class PodReservationDto {

    /**
     * Creates a {@link PodReservationDto} based on a {@link PodReservation}.
     *
     * @param podReservation The base of the {@link PodReservationDto}.
     * @return A {@link PodReservationDto}.
     */
    public static PodReservationDto create(PodReservation podReservation) throws PodReservationException {
        if (podReservation == null) {
            throw new PodReservationException(PodReservationError.MISSING);
        }

        return new PodReservationDto(
                podReservation.getId(),
                podReservation.getPodCode(),
                podReservation.getCreationDate(),
                podReservation.getPassenger().getId(),
                podReservation.getVoyage().getId()
        );
    }

    /**
     * The ID of the pod reservation.
     */
    @Schema(description = "The ID of the pod reservation", example = ID_EXAMPLE)
    public String id;

    /**
     * The pod code of the pod reservation.
     */
    @Schema(description = "The pod code of the pod reservation", example = POD_CODE_EXAMPLE)
    public String podCode;

    /**
     * The creation date of the pod reservation.
     */
    @Schema(description = "The creation date of the pod reservation.", example = CREATION_DATE_EXAMPLE)
    public ZonedDateTime creationDate;

    /**
     * The passenger ID of the pod reservation.
     */
    @Schema(description = "The passenger ID of the pod reservation.", example = ID_EXAMPLE)
    public String passengerId;

    /**
     * The voyage ID of the pod reservation.
     */
    @Schema(description = "The voyage ID of the pod reservation.", example = VOYAGE_ID_EXAMPLE)
    public String voyageId;

    protected PodReservationDto() {
    }

    protected PodReservationDto(String id, String podCode, ZonedDateTime creationDate, String passengerId, String voyageId) throws PodReservationException {
        if (id == null) {
            throw new PodReservationException(PodReservationError.MISSING_ID);
        } else if (podCode == null) {
            throw new PodReservationException(PodReservationError.MISSING_CODE);
        } else if (creationDate == null) {
            throw new PodReservationException(PodReservationError.MISSING_CREATION_DATE);
        } else if (passengerId == null) {
            throw new PodReservationException(PodReservationError.MISSING_PASSENGER_ID);
        } else if (voyageId == null) {
            throw new PodReservationException(PodReservationError.MISSING_VOYAGE_ID);
        }

        this.id = id;
        this.podCode = podCode;
        this.creationDate = creationDate;
        this.passengerId = passengerId;
        this.voyageId = voyageId;
    }
}
