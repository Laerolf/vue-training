package jp.co.company.space.api.features.pod.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jp.co.company.space.api.features.passenger.domain.Passenger;
import jp.co.company.space.api.features.pod.domain.PodReservation;
import jp.co.company.space.api.features.pod.domain.PodReservationFactory;
import jp.co.company.space.api.features.pod.repository.PodReservationRepository;
import jp.co.company.space.api.features.voyage.domain.Voyage;

import java.util.List;

/**
 * A service class handling the {@link PodReservation} topic.
 */
@ApplicationScoped
public class PodReservationService {

    @Inject
    private PodReservationRepository podReservationRepository;

    protected PodReservationService() {
    }

    /**
     * Creates a new {@link PodReservation} instance for the provided {@link Voyage} based on the provided {@link Passenger} instance and pod code.
     *
     * @param voyage          The voyage of the new pod reservation.
     * @param passenger       The passenger of the new pod reservation.
     * @param requestedPodCode The pod code of the requested pod.
     * @return A {@link PodReservation} instance.
     */
    public PodReservation reservePodForPassenger(Voyage voyage, Passenger passenger, String requestedPodCode) {
        if (voyage == null) {
            throw new IllegalArgumentException("The voyage for the pod reservation is missing.");
        } else if (passenger == null) {
            throw new IllegalArgumentException("The passenger for the pod reservation is missing.");
        }

        List<PodReservation> reservations = getAllPodReservationsByVoyage(voyage);

        PodReservation newPodReservation = new PodReservationFactory(voyage, passenger, requestedPodCode, reservations).create();

        return podReservationRepository.save(newPodReservation);
    }

    /**
     * Returns a {@link List} of all {@link PodReservation} instances matching a {@link Voyage} instance.
     *
     * @param voyage The voyage search for.
     * @return A {@link List} of {@link PodReservation} instances.
     */
    public List<PodReservation> getAllPodReservationsByVoyage(Voyage voyage) {
        return podReservationRepository.getAllBySpaceShuttleAndVoyage(voyage);
    }
}
