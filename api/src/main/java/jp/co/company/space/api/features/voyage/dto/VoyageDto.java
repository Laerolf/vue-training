package jp.co.company.space.api.features.voyage.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.voyage.domain.Voyage;

/**
 * A POJO representing a DTO of a {@link Voyage} instance.
 */
@Schema(name = "VoyageDetailDto", description = "The details of a voyage.")
public class VoyageDto {

    /**
     * Returns a new {@link VoyageDto} instance based on a {@link Voyage} instance.
     * 
     * @param voyage The base {@link Voyage} instance.
     * @return A new {@link VoyageDto} instance.
     */
    public static VoyageDto create(Voyage voyage) {
        if (voyage == null) {
            throw new IllegalArgumentException("The voyage instance is missing.");
        }

        return new VoyageDto(voyage.getId(), voyage.getDepartureDate().toString(), voyage.getArrivalDate().toString(), voyage.getDuration().toSeconds(), voyage.getStatus().getLocaleCode(),
                voyage.getRoute(), voyage.getSpaceShuttle());
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
     * The route of the voyage.
     */
    @Schema(description = "The route of the voyage.", implementation = Route.class)
    public Route route;

    /**
     * The space shuttle of the voyage.
     */
    @Schema(description = "The space shuttle of the voyage.", implementation = SpaceShuttle.class)
    public SpaceShuttle spaceShuttle;

    protected VoyageDto() {}

    private VoyageDto(String id, String departureDate, String arrivalDate, Long duration, String status, Route route, SpaceShuttle spaceShuttle) {
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
        } else if (route == null) {
            throw new IllegalArgumentException("The route of the voyage is missing.");
        } else if (spaceShuttle == null) {
            throw new IllegalArgumentException("The space shuttle of the voyage is missing.");
        }

        this.id = id;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.duration = duration;
        this.status = status;
        this.route = route;
        this.spaceShuttle = spaceShuttle;
    }
}
