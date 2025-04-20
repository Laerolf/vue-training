package jp.co.company.space.api.features.passenger.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jp.co.company.space.api.features.passenger.domain.Passenger;
import jp.co.company.space.api.features.passenger.repository.PassengerRepository;

import java.util.Optional;

/**
 * A service class handling the {@link Passenger} topic.
 */
@ApplicationScoped
public class PassengerService {

    @Inject
    private PassengerRepository passengerRepository;

    protected PassengerService() {}

    /**
     * Gets an {@link Optional} {@link Passenger} instance matching an ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link Passenger} instance.
     */
    public Optional<Passenger> findById(String id) {
        return passengerRepository.findById(id);
    }
}
