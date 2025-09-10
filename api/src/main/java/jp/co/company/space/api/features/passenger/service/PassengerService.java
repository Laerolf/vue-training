package jp.co.company.space.api.features.passenger.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.catalog.domain.MealPreference;
import jp.co.company.space.api.features.catalog.domain.PackageType;
import jp.co.company.space.api.features.catalog.exception.CatalogError;
import jp.co.company.space.api.features.catalog.exception.CatalogException;
import jp.co.company.space.api.features.passenger.domain.Passenger;
import jp.co.company.space.api.features.passenger.domain.PassengerCreationFactory;
import jp.co.company.space.api.features.passenger.domain.PersonalInformation;
import jp.co.company.space.api.features.passenger.domain.PersonalInformationCreationFactory;
import jp.co.company.space.api.features.passenger.exception.PassengerError;
import jp.co.company.space.api.features.passenger.exception.PassengerException;
import jp.co.company.space.api.features.passenger.input.PassengerCreationForm;
import jp.co.company.space.api.features.passenger.input.PersonalInformationCreationForm;
import jp.co.company.space.api.features.passenger.repository.PassengerRepository;
import jp.co.company.space.api.features.passenger.repository.PersonalInformationRepository;
import jp.co.company.space.api.features.pod.domain.PodReservation;
import jp.co.company.space.api.features.pod.exception.PodReservationException;
import jp.co.company.space.api.features.pod.service.PodReservationService;
import jp.co.company.space.api.shared.exception.DomainException;
import jp.co.company.space.api.shared.util.LogBuilder;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A service class handling the {@link Passenger} topic.
 */
@ApplicationScoped
public class PassengerService {

    private static final Logger LOGGER = Logger.getLogger(PassengerService.class.getName());

    @Inject
    private PassengerRepository passengerRepository;

    @Inject
    private PersonalInformationRepository personalInformationRepository;

    @Inject
    private PodReservationService podReservationService;

    protected PassengerService() {
    }

    /**
     * Adds {@link PersonalInformation} to a {@link Passenger}.
     *
     * @param passenger               The subject of the personal information.
     * @param personalInformationForm The information to add.
     * @return A {@link Passenger}.
     */
    private Passenger addPersonalInformation(Passenger passenger, PersonalInformationCreationForm personalInformationForm) throws PassengerException {
        try {
            PersonalInformation personalInformation = new PersonalInformationCreationFactory(passenger, personalInformationForm).create();
            personalInformationRepository.save(personalInformation);
            passenger.assignPersonalInformation(personalInformation);
            return passenger;
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(PassengerError.ADD_PERSONAL_INFORMATION).withException(exception).build());
            throw new PassengerException(PassengerError.ADD_PERSONAL_INFORMATION, exception);
        }
    }

    /**
     * Gets an {@link Optional} {@link Passenger} matching an ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link Passenger}.
     */
    public Optional<Passenger> findById(String id) throws PassengerException {
        try {
            return passengerRepository.findById(id);
        } catch (PassengerException exception) {
            LOGGER.warning(new LogBuilder(PassengerError.FIND_BY_ID).withException(exception).withProperty("id", id).build());
            throw exception;
        }
    }

    /**
     * Creates a {@link List} of {@link Passenger}s.
     *
     * @param booking        The booking for all passengers.
     * @param passengerForms The details of the passengers to create.
     * @return A {@link List} of {@link Passenger}s.
     */
    public List<Passenger> create(Booking booking, List<PassengerCreationForm> passengerForms) throws PassengerException, PodReservationException {
        try {
            if (booking == null) {
                throw new PassengerException(PassengerError.MISSING_BOOKING);
            } else if (passengerForms == null) {
                throw new PassengerException(PassengerError.MISSING_PASSENGER_DETAILS);
            }

            List<Passenger> createdPassengers = passengerForms.stream()
                    .map(passengerForm -> {
                        PackageType selectedPackageType = PackageType.findByKey(passengerForm.packageType).orElseThrow(() -> new CatalogException(CatalogError.PACKAGE_TYPE_MISSING));
                        MealPreference selectedMealPreference = MealPreference.findByKey(passengerForm.mealPreference).orElseThrow(() -> new CatalogException(CatalogError.MEAL_PREFERENCE_MISSING));

                        Passenger newPassenger = new PassengerCreationFactory(booking, selectedPackageType, selectedMealPreference).create();
                        newPassenger = passengerRepository.save(newPassenger);

                        addPersonalInformation(newPassenger, passengerForm.personalInformation);

                        PodReservation podReservation = podReservationService.reservePodForPassenger(booking.getVoyage(), newPassenger, passengerForm.podCode);
                        newPassenger.assignPodReservation(podReservation);

                        return newPassenger;
                    }).toList();

            LOGGER.info(new LogBuilder(String.format("Created %d new passengers for a booking.", createdPassengers.size())).withProperty("booking.id", booking.getId()).build());

            return createdPassengers;
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(PassengerError.CREATE).withException(exception).build());
            throw new PassengerException(PassengerError.CREATE, exception);
        }
    }
}
