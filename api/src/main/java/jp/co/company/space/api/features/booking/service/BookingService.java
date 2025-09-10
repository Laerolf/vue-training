package jp.co.company.space.api.features.booking.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.booking.domain.BookingCreationFactory;
import jp.co.company.space.api.features.booking.exception.BookingError;
import jp.co.company.space.api.features.booking.exception.BookingException;
import jp.co.company.space.api.features.booking.input.BookingCreationForm;
import jp.co.company.space.api.features.booking.repository.BookingRepository;
import jp.co.company.space.api.features.passenger.domain.Passenger;
import jp.co.company.space.api.features.passenger.service.PassengerService;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.user.service.UserService;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.features.voyage.service.VoyageService;
import jp.co.company.space.api.shared.exception.DomainException;
import jp.co.company.space.api.shared.util.LogBuilder;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A service class handling the {@link Booking} topic.
 */
@ApplicationScoped
public class BookingService {

    private static final Logger LOGGER = Logger.getLogger(BookingService.class.getName());

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
     * Creates a new {@link Booking}.
     *
     * @param creationForm The form with the details for the new {@link Booking}.
     * @return A new {@link Booking}.
     * @throws BookingException When the form is missing or the form's passengers are missing or invalid.
     */
    public Booking create(Principal userPrincipal, BookingCreationForm creationForm) throws BookingException {
        // TODO: it should not be possible to adjust a booking with the status "created"
        try {
            if (creationForm == null) {
                throw new BookingException(BookingError.NEW_MISSING_CREATION_FORM);
            } else if (creationForm.passengers == null) {
                throw new BookingException(BookingError.MISSING_PASSENGERS);
            } else if (creationForm.passengers.isEmpty()) {
                throw new BookingException(BookingError.INVALID_PASSENGER_COUNT);
            }

            User selectedUser = userService.findById(userPrincipal.getName()).orElseThrow(() -> new BookingException(BookingError.NEW_USER_NOT_FOUND));
            Voyage selectedVoyage = voyageService.findById(creationForm.voyageId).orElseThrow(() -> new BookingException(BookingError.NEW_VOYAGE_NOT_FOUND));

            Booking newBooking = new BookingCreationFactory(selectedUser, selectedVoyage).create();
            newBooking.setCreated();
            newBooking = bookingRepository.save(newBooking);

            List<Passenger> passengers = passengerService.create(newBooking, creationForm.passengers);
            newBooking.assignPassengers(passengers);

            LOGGER.info(new LogBuilder("A new booking has been saved to the database.").build());

            return newBooking;
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(BookingError.CREATE).withException(exception).build());
            throw exception;
        }
    }

    /**
     * Returns an {@link Optional} saved {@link Booking} matching the provided booking ID.
     *
     * @param id The booking ID to search with.
     * @return An {@link Optional} saved {@link Booking}.
     */
    public Optional<Booking> findById(String id) throws BookingException {
        try {
            return bookingRepository.findById(id);
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder("Failed to find the booking with the provided ID").withException(exception).withProperty("id", id).build());
            throw exception;
        }
    }

    /**
     * Returns a {@link List} of saved {@link Booking}s matching the provided user ID.
     *
     * @param userId The user ID to search with.
     * @return A {@link List} of saved {@link Booking}s.
     */
    public List<Booking> getAllByUserId(String userId) throws BookingException {
        try {
            return bookingRepository.getAllByUserId(userId);
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder("Failed to find the booking with the provided user ID").withException(exception).withProperty("userId", userId).build());
            throw exception;
        }
    }

    /**
     * Returns a {@link List} of saved {@link Booking}s matching the provided voyage ID.
     *
     * @param voyageId The voyage ID to search with.
     * @return A {@link List} of saved {@link Booking}s.
     */
    public List<Booking> getAllByVoyageId(String voyageId) throws BookingException {
        try {
            return bookingRepository.getAllByVoyageId(voyageId);
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder("Failed to find the booking with the provided voyage ID").withException(exception).withProperty("voyageId", voyageId).build());
            throw exception;
        }
    }

    /**
     * Saves a {@link Booking}.
     *
     * @param booking The {@link Booking} to save.
     * @return A saved {@link Booking}.
     */
    public Booking save(Booking booking) throws BookingException {
        try {
            return bookingRepository.save(booking);
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(BookingError.SAVE).withException(exception).withProperty("booking.id", booking.getId()).build());
            throw exception;
        }
    }

    /**
     * Saves a {@link List} of {@link Booking}s.
     *
     * @param bookings The a {@link List} of {@link Booking}s to save.
     * @return A {@link List} of saved {@link Booking}s.
     */
    public List<Booking> save(List<Booking> bookings) throws BookingException {
        try {
            return bookingRepository.save(bookings);
        } catch (DomainException exception) {
            LOGGER.warning(new LogBuilder(BookingError.SAVE_LIST).withException(exception).build());
            throw exception;
        }
    }
}
