package jp.co.company.space.api.features.booking.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.booking.domain.BookingCreationFactory;
import jp.co.company.space.api.features.booking.input.BookingCreationForm;
import jp.co.company.space.api.features.booking.repository.BookingRepository;
import jp.co.company.space.api.features.passenger.domain.Passenger;
import jp.co.company.space.api.features.passenger.service.PassengerService;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.user.service.UserService;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.features.voyage.service.VoyageService;
import jp.co.company.space.api.shared.exception.DomainException;

import java.util.List;
import java.util.Optional;

/**
 * A service class handling the {@link Booking} topic.
 */
@ApplicationScoped
public class BookingService {

    @Inject
    private BookingRepository bookingRepository;

    @Inject
    private UserService userService;

    @Inject
    private VoyageService voyageService;

    @Inject
    private PassengerService passengerService;

    protected BookingService() {
    }

    /**
     * Creates a new {@link Booking} instance.
     *
     * @param creationForm The form with the details for the new {@link Booking} instance.
     * @return A new {@link Booking} instance.
     */
    public Booking create(BookingCreationForm creationForm) {
        if (creationForm == null) {
            throw new IllegalArgumentException("The form for a new booking is missing.");
        } else if (creationForm.passengers == null) {
            throw new IllegalArgumentException("The passengers for a new booking is missing.");
        } else if (creationForm.passengers.isEmpty()) {
            // TODO: add specific exceptions for each domain model + add codes with OpenAPI documentation
            throw new DomainException("A booking needs at least one passenger.");
        }

        User selectedUser = userService.findById(creationForm.userId).orElseThrow(() -> new IllegalArgumentException("The new booking's user could not be found."));
        Voyage selectedVoyage = voyageService.findById(creationForm.voyageId).orElseThrow(() -> new IllegalArgumentException("The new booking's voyage could not be found."));

        Booking newBooking = new BookingCreationFactory(selectedUser, selectedVoyage).create();
        newBooking.setCreated();
        newBooking = bookingRepository.save(newBooking);

        List<Passenger> passengers = passengerService.create(newBooking, creationForm.passengers);

        newBooking.assignPassengers(passengers);

        return newBooking;
    }

    /**
     * Returns an {@link Optional} saved {@link Booking} instance matching the provided booking ID.
     *
     * @param id The booking ID to search with.
     * @return An {@link Optional} saved {@link Booking} instance.
     */
    public Optional<Booking> findById(String id) {
        return bookingRepository.findById(id);
    }

    /**
     * Returns a {@link List} of saved {@link Booking} instances matching the provided user ID.
     *
     * @param userId The user ID to search with.
     * @return A {@link List} of saved {@link Booking} instances.
     */
    public List<Booking> getAllByUserId(String userId) {
        return bookingRepository.getAllByUserId(userId);
    }

    /**
     * Returns a {@link List} of saved {@link Booking} instances matching the provided voyage ID.
     *
     * @param voyageId The voyage ID to search with.
     * @return A {@link List} of saved {@link Booking} instances.
     */
    public List<Booking> getAllByVoyageId(String voyageId) {
        return bookingRepository.getAllByVoyageId(voyageId);
    }

    /**
     * Saves a {@link Booking} instance.
     *
     * @param booking The {@link Booking} instance to save.
     * @return A saved {@link Booking} instance.
     */
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    /**
     * Saves a {@link List} of {@link Booking} instances.
     *
     * @param bookings The a {@link List} of {@link Booking} instances to save.
     * @return A {@link List} of saved {@link Booking} instances.
     */
    public List<Booking> save(List<Booking> bookings) {
        return bookingRepository.save(bookings);
    }
}
