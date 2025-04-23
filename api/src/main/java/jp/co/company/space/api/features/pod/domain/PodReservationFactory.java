package jp.co.company.space.api.features.pod.domain;

import jp.co.company.space.api.features.passenger.domain.Passenger;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttleLayout;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.shared.exception.DomainException;

import java.util.List;
import java.util.Optional;

/**
 * A POJO representing a factory creating {@link PodReservation} instances.
 */
public class PodReservationFactory {

    private final Voyage voyage;
    private final Passenger passenger;
    private final String requestedPodCode;
    private final List<PodReservation> existingReservations;

    public PodReservationFactory(Voyage voyage, Passenger passenger, String requestedPodCode, List<PodReservation> existingReservations) {
        this.voyage = voyage;
        this.passenger = passenger;
        this.requestedPodCode = requestedPodCode;
        this.existingReservations = existingReservations;
    }

    /**
     * Creates a new {@link PodReservation} instance.
     *
     * @return A {@link PodReservation} instance.
     */
    public PodReservation create() {
        SpaceShuttleLayout layout = voyage.getSpaceShuttle().getLayout();

        return Optional.ofNullable(requestedPodCode).map(podCode -> {
            if (!layout.isPodAvailable(podCode, existingReservations)) {
                throw new DomainException("The request pod is already reserved.");
            } else if (!layout.isPodAvailableForPassenger(podCode, passenger)) {
                throw new DomainException("The request pod is not available for the passenger's package type.");
            }

            return PodReservation.create(podCode, passenger, voyage);
        }).orElseGet(() -> {
            Pod selectedPod = layout.findFirstAvailablePodFor(passenger, existingReservations).orElseThrow(() -> new DomainException("The space shuttle is full."));
            return PodReservation.create(selectedPod.getCode(), passenger, voyage);
        });
    }
}
