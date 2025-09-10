package jp.co.company.space.api.features.voyage.dto;

import jp.co.company.space.api.features.route.dto.RouteDto;
import jp.co.company.space.api.features.spaceShuttle.dto.SpaceShuttleDto;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.features.voyage.exception.VoyageError;
import jp.co.company.space.api.features.voyage.exception.VoyageException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.Examples.*;

/**
 * A POJO representing a DTO of a {@link Voyage}.
 */
@Schema(name = "Voyage", description = "The details of a voyage.")
public class VoyageDto {

    /**
     * Returns a new {@link VoyageDto} based on a {@link Voyage}.
     *
     * @param voyage The base {@link Voyage}.
     * @return A new {@link VoyageDto}.
     */
    public static VoyageDto create(Voyage voyage) throws VoyageException {
        if (voyage == null) {
            throw new VoyageException(VoyageError.MISSING);
        }

        return new VoyageDto(
                voyage.getId(),
                voyage.getDepartureDate().toString(), voyage.getArrivalDate().toString(),
                voyage.getDuration().toSeconds(),
                voyage.getStatus().getLabel(),
                RouteDto.create(voyage.getRoute()),
                SpaceShuttleDto.create(voyage.getSpaceShuttle())
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
    public Long duration;

    /**
     * The status of the voyage.
     */
    @Schema(description = "The status of the voyage.", example = VOYAGE_STATUS_EXAMPLE)
    public String status;

    /**
     * The route of the voyage.
     */
    @Schema(description = "The route of the voyage.")
    public RouteDto route;

    /**
     * The space shuttle of the voyage.
     */
    @Schema(description = "The space shuttle of the voyage.")
    public SpaceShuttleDto spaceShuttle;

    protected VoyageDto() {
    }

    protected VoyageDto(String id, String departureDate, String arrivalDate, Long duration, String status, RouteDto route, SpaceShuttleDto spaceShuttle) throws VoyageException {
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
        } else if (route == null) {
            throw new VoyageException(VoyageError.MISSING_ROUTE);
        } else if (spaceShuttle == null) {
            throw new VoyageException(VoyageError.MISSING_SPACE_SHUTTLE);
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
