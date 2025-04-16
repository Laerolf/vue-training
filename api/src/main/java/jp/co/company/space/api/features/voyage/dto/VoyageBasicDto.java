package jp.co.company.space.api.features.voyage.dto;

import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * A POJO representing a brief DTO of a {@link Voyage} instance.
 */
@Schema(description = "The brief details of a voyage.")
public class VoyageBasicDto {
    /**
     * Returns a new {@link VoyageBasicDto} instance based on a {@link Voyage} instance.
     *
     * @param voyage The base {@link Voyage} instance.
     * @return A new {@link VoyageBasicDto} instance.
     */
    public static VoyageBasicDto create(Voyage voyage) {
        if (voyage == null) {
            throw new IllegalArgumentException("The voyage instance is missing.");
        }

        Route voyageRoute = voyage.getRoute();

        return new VoyageBasicDto(voyage.getId(), voyage.getDepartureDate().toString(), voyage.getArrivalDate().toString(), voyage.getDuration().toSeconds(), voyage.getStatus().getKey(),
                voyageRoute.getId(), voyageRoute.getOrigin().getId(), voyageRoute.getDestination().getId(), voyage.getSpaceShuttle().getId());
    }

    /**
     * The ID of the voyage.
     */
    @Schema(description = "The ID of the voyage.", example = "1")
    public String id;

    /**
     * The departure date of the voyage.
     */
    @Schema(description = "The departure date of the voyage.", example = "2050-02-22T08:49:20.507334+09:00")
    public String departureDate;

    /**
     * The arrival date of the voyage.
     */
    @Schema(description = "The arrival date of the voyage.", example = "2050-03-21T17:49:20.507334+09:00")
    public String arrivalDate;

    /**
     * The duration of the voyage in seconds.
     */
    @Schema(description = "The duration of the voyage in seconds.", example = "2365200")
    public long duration;

    /**
     * The status of the voyage.
     */
    @Schema(description = "The status of the voyage.", example = "scheduled")
    public String status;

    /**
     * The ID of the voyage's route.
     */
    @Schema(description = "The ID of the voyage's route.", example = "872123e1-81e3-424b-8e40-607e932e910a")
    public String routeId;

    /**
     * The ID of the voyage's route origin.
     */
    @Schema(description = "The ID of the voyage's route origin.", example = "872123e1-81e3-424b-8e40-607e932e910a")
    public String originId;

    /**
     * The ID of the voyage's route destination.
     */
    @Schema(description = "The ID of the voyage's route destination.", example = "872123e1-81e3-424b-8e40-607e932e910a")
    public String destinationId;

    /**
     * The ID of the voyage's space shuttle.
     */
    @Schema(description = "The ID of the voyage's space shuttle.", example = "00000000-0000-1000-8000-000000000003")
    public String spaceShuttleId;

    protected VoyageBasicDto() {}

    protected VoyageBasicDto(String id, String departureDate, String arrivalDate, Long duration, String status, String routeId, String originId, String destinationId, String spaceShuttleId) {
        if (id == null) {
            throw new IllegalArgumentException("The ID of the voyage is missing.");
        } else if (departureDate == null) {
            throw new IllegalArgumentException("The departure date of the voyage is missing.");
        } else if (arrivalDate == null) {
            throw new IllegalArgumentException("The arrival date of the voyage is missing.");
        } else if (duration == null) {
            throw new IllegalArgumentException("The duration of the voyage is missing.");
        } else if (status == null) {
            throw new IllegalArgumentException("The status of the voyage is missing.");
        } else if (routeId == null) {
            throw new IllegalArgumentException("The ID of the voyage's route is missing.");
        } else if (originId == null) {
            throw new IllegalArgumentException("The ID of the voyage's route origin is missing.");
        } else if (destinationId == null) {
            throw new IllegalArgumentException("The ID of the voyage's route destination is missing.");
        } else if (spaceShuttleId == null) {
            throw new IllegalArgumentException("The ID of the voyage's space shuttle is missing.");
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
