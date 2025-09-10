package jp.co.company.space.api.features.voyage.dto;

import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.features.voyage.exception.VoyageError;
import jp.co.company.space.api.features.voyage.exception.VoyageException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.Examples.*;

/**
 * A POJO representing a brief DTO of a {@link Voyage}.
 */
@Schema(name = "BasicVoyage", description = "The brief details of a voyage.")
public class VoyageBasicDto {
    /**
     * Returns a new {@link VoyageBasicDto} based on a {@link Voyage}.
     *
     * @param voyage The base {@link Voyage}.
     * @return A new {@link VoyageBasicDto}.
     */
    public static VoyageBasicDto create(Voyage voyage) throws VoyageException {
        if (voyage == null) {
            throw new VoyageException(VoyageError.MISSING);
        }

        Route voyageRoute = voyage.getRoute();

        return new VoyageBasicDto(
                voyage.getId(),
                voyage.getDepartureDate().toString(), voyage.getArrivalDate().toString(),
                voyage.getDuration().toSeconds(),
                voyage.getStatus().getKey(),
                voyageRoute.getId(), voyageRoute.getOrigin().getId(), voyageRoute.getDestination().getId(),
                voyage.getSpaceShuttle().getId()
        );
    }

    /**
     * The ID of the voyage.
     */
    @Schema(description = "The ID of the voyage.", example = VOYAGE_ID_EXAMPLE)
    public String id;

    /**
     * The departure date of the voyage.
     */
    @Schema(description = "The departure date of the voyage.", example = VOYAGE_DEPARTURE_DATE_EXAMPLE)
    public String departureDate;

    /**
     * The arrival date of the voyage.
     */
    @Schema(description = "The arrival date of the voyage.", example = VOYAGE_ARRIVAL_DATE_EXAMPLE)
    public String arrivalDate;

    /**
     * The duration of the voyage in seconds.
     */
    @Schema(description = "The duration of the voyage in seconds.", example = VOYAGE_DURATION_EXAMPLE)
    public long duration;

    /**
     * The status of the voyage.
     */
    @Schema(description = "The status of the voyage.", example = VOYAGE_STATUS_EXAMPLE)
    public String status;

    /**
     * The ID of the voyage's route.
     */
    @Schema(description = "The ID of the voyage's route.", example = ROUTE_ID_EXAMPLE)
    public String routeId;

    /**
     * The ID of the voyage's route origin.
     */
    @Schema(description = "The ID of the voyage's route origin.", example = ROUTE_ORIGIN_ID_EXAMPLE)
    public String originId;

    /**
     * The ID of the voyage's route destination.
     */
    @Schema(description = "The ID of the voyage's route destination.", example = ROUTE_DESTINATION_ID_EXAMPLE)
    public String destinationId;

    /**
     * The ID of the voyage's space shuttle.
     */
    @Schema(description = "The ID of the voyage's space shuttle.", example = SPACE_SHUTTLE_ID_EXAMPLE)
    public String spaceShuttleId;

    protected VoyageBasicDto() {
    }

    protected VoyageBasicDto(String id, String departureDate, String arrivalDate, Long duration, String status, String routeId, String originId, String destinationId, String spaceShuttleId) throws VoyageException {
        if (id == null) {
            throw new VoyageException(VoyageError.MISSING_ID);
        } else if (departureDate == null) {
            throw new VoyageException(VoyageError.MISSING_DEPARTURE_DATE);
        } else if (arrivalDate == null) {
            throw new VoyageException(VoyageError.MISSING_ARRIVAL_DATE);
        } else if (duration == null) {
            throw new VoyageException(VoyageError.MISSING_DURATION);
        } else if (status == null) {
            throw new VoyageException(VoyageError.MISSING_STATUS);
        } else if (routeId == null) {
            throw new VoyageException(VoyageError.MISSING_ROUTE_ID);
        } else if (originId == null) {
            throw new VoyageException(VoyageError.MISSING_ORIGIN_ID);
        } else if (destinationId == null) {
            throw new VoyageException(VoyageError.MISSING_DESTINATION_ID);
        } else if (spaceShuttleId == null) {
            throw new VoyageException(VoyageError.MISSING_SPACE_SHUTTLE_ID);
        }

        this.id = id;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.duration = duration;
        this.status = status;
        this.routeId = routeId;
        this.originId = originId;
        this.destinationId = destinationId;
        this.spaceShuttleId = spaceShuttleId;
    }
}
