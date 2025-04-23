package jp.co.company.space.api.features.pod.dto;

import jp.co.company.space.api.features.pod.domain.PodReservation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.ZonedDateTime;

import static jp.co.company.space.api.shared.openApi.examples.*;

/**
 * A POJO representing a DTO of {@link PodReservation} instance.
 */
@Schema(name = "PodReservation", description = "The details of a pod reservation.")
public class PodReservationDto {

    /**
     * Creates a {@link PodReservationDto} instance based on a {@link PodReservation} instance.
     *
     * @param podReservation The base of the {@link PodReservationDto} instance.
     * @return A {@link PodReservationDto} instance.
     */
    public static PodReservationDto create(PodReservation podReservation) {
        if (podReservation == null) {
            throw new IllegalArgumentException("The base pod reservation for this pod reservation dto is missing.");
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

    protected PodReservationDto(String id, String podCode, ZonedDateTime creationDate, String passengerId, String voyageId) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the pod reservation is missing.");
        } else if (podCode == null) {
            throw new IllegalArgumentException("The pod code of the pod reservation is missing.");
        } else if (creationDate == null) {
            throw new IllegalArgumentException("The creation date of the pod reservation is missing.");
        } else if (passengerId == null) {
            throw new IllegalArgumentException("The passenger ID of the pod reservation is missing.");
        } else if (voyageId == null) {
            throw new IllegalArgumentException("The voyage ID of the pod reservation is missing.");
        }

        this.id = id;
        this.podCode = podCode;
        this.creationDate = creationDate;
        this.passengerId = passengerId;
        this.voyageId = voyageId;
    }
}
