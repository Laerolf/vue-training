package jp.co.company.space.api.features.pod.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jp.co.company.space.api.features.passenger.domain.Passenger;
import jp.co.company.space.api.features.pod.domain.PodReservation;
import jp.co.company.space.api.features.pod.domain.PodReservationFactory;
import jp.co.company.space.api.features.pod.exception.PodCodeException;
import jp.co.company.space.api.features.pod.exception.PodException;
import jp.co.company.space.api.features.pod.exception.PodReservationError;
import jp.co.company.space.api.features.pod.exception.PodReservationException;
import jp.co.company.space.api.features.pod.repository.PodReservationRepository;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.shared.util.LogBuilder;

import java.util.List;
import java.util.logging.Logger;

/**
 * A service class handling the {@link PodReservation} topic.
 */
@ApplicationScoped
public class PodReservationService {

    private static final Logger LOGGER = Logger.getLogger(PodReservationService.class.getName());

    @Inject
    private PodReservationRepository repository;

    protected PodReservationService() {
    }

    /**
     * Creates a new {@link PodReservation} for the provided {@link Voyage} based on the provided {@link Passenger} and pod code.
     *
     * @param voyage           The voyage of the new pod reservation.
     * @param passenger        The passenger of the new pod reservation.
     * @param requestedPodCode The pod code of the requested pod.
     * @return A {@link PodReservation}.
     */
    public PodReservation reservePodForPassenger(Voyage voyage, Passenger passenger, String requestedPodCode) throws PodReservationException {
        try {
            if (voyage == null) {
                throw new PodReservationException(PodReservationError.MISSING_VOYAGE);
            } else if (passenger == null) {
                throw new PodReservationException(PodReservationError.MISSING_PASSENGER);
            }

            List<PodReservation> reservations = getAllPodReservationsByVoyage(voyage);

            PodReservation newPodReservation = new PodReservationFactory(voyage, passenger, requestedPodCode, reservations).create();

            return repository.save(newPodReservation);
        } catch (PodException | PodReservationException | PodCodeException exception) {
            LOGGER.warning(new LogBuilder("Failed to reserve a pod for a passenger").withException(exception).build());
            throw exception;
        }
    }

    /**
     * Returns a {@link List} of all {@link PodReservation}s matching a {@link Voyage}.
     *
     * @param voyage The voyage search for.
     * @return A {@link List} of {@link PodReservation}s.
     */
    public List<PodReservation> getAllPodReservationsByVoyage(Voyage voyage) throws PodReservationException {
        try {
            return repository.getAllBySpaceShuttleAndVoyage(voyage);
        } catch (PodReservationException exception) {
            LOGGER.warning(new LogBuilder("Failed to get all pod reservations of the provided voyage").withException(exception).build());
            throw exception;
        }
    }
}
