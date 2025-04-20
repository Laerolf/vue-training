package jp.co.company.space.api.features.pod.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jp.co.company.space.api.features.pod.domain.Pod;
import jp.co.company.space.api.features.pod.domain.PodReservation;
import jp.co.company.space.api.features.pod.repository.PodReservationRepository;
import jp.co.company.space.api.features.voyage.domain.Voyage;

import java.util.List;

/**
 * A service class handling the {@link Pod} topic.
 */
@ApplicationScoped
public class PodService {

    @Inject
    private PodReservationRepository podReservationRepository;

    protected PodService() {
    }

    /**
     * Returns a {@link List} of all {@link PodReservation} instances matching a {@link Voyage} instance.
     *
     * @param voyage The voyage search for.
     * @return A {@link List} of {@link PodReservation} instances.
     */
    public List<PodReservation> getAllPodReservationsByVoyage(Voyage voyage) {
        return podReservationRepository.getAllBySpaceShuttleAndVoyage(voyage.getSpaceShuttle(), voyage);
    }
}
