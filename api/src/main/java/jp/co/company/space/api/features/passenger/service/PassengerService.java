package jp.co.company.space.api.features.passenger.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.catalog.domain.MealPreference;
import jp.co.company.space.api.features.catalog.domain.PackageType;
import jp.co.company.space.api.features.passenger.domain.Passenger;
import jp.co.company.space.api.features.passenger.domain.PassengerCreationFactory;
import jp.co.company.space.api.features.passenger.input.PassengerCreationForm;
import jp.co.company.space.api.features.passenger.repository.PassengerRepository;
import jp.co.company.space.api.features.pod.domain.PodReservation;
import jp.co.company.space.api.features.pod.service.PodReservationService;

import java.util.List;
import java.util.Optional;

/**
 * A service class handling the {@link Passenger} topic.
 */
@ApplicationScoped
public class PassengerService {

    @Inject
    private PassengerRepository passengerRepository;

    @Inject
    private PodReservationService podReservationService;

    protected PassengerService() {
    }

    /**
     * Gets an {@link Optional} {@link Passenger} instance matching an ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link Passenger} instance.
     */
    public Optional<Passenger> findById(String id) {
        return passengerRepository.findById(id);
    }

    public List<Passenger> create(Booking booking, List<PassengerCreationForm> passengerForms) {
        return passengerForms.stream()
                .map(passengerForm -> {
                    PackageType selectedPackageType = PackageType.findByKey(passengerForm.packageType).orElseThrow();
                    MealPreference selectedMealPreference = MealPreference.findByKey(passengerForm.mealPreference).orElseThrow();

                    Passenger newPassenger = new PassengerCreationFactory(booking, selectedPackageType, selectedMealPreference).create();
                    newPassenger = passengerRepository.save(newPassenger);

                    PodReservation podReservation = podReservationService.reservePodForPassenger(booking.getVoyage(), newPassenger, passengerForm.podCode);

                    newPassenger.assignPodReservation(podReservation);

                    return newPassenger;
                })
                .toList();
    }
}
