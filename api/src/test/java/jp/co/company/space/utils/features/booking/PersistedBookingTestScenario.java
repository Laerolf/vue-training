package jp.co.company.space.utils.features.booking;

import jakarta.inject.Inject;
import jp.co.company.space.api.features.booking.domain.Booking;
import jp.co.company.space.api.features.booking.exception.BookingException;
import jp.co.company.space.api.features.booking.repository.BookingRepository;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.utils.features.user.PersistedUserTestScenario;
import jp.co.company.space.utils.features.voyage.PersistedVoyageTestScenario;
import jp.co.company.space.utils.shared.testScenario.TestScenario;
import jp.co.company.space.utils.shared.testScenario.TestScenarioException;

/**
 * A {@link TestScenario} with a persisted booking.
 * Nested scenarios: {@link PersistedVoyageTestScenario} and {@link PersistedVoyageTestScenario}
 */
public class PersistedBookingTestScenario implements TestScenario {

    @Inject
    private PersistedUserTestScenario persistedUserTestScenario;

    @Inject
    private PersistedVoyageTestScenario persistedVoyageTestScenario;

    @Inject
    private BookingRepository repository;

    private Booking persistedBooking;

    protected PersistedBookingTestScenario() {
    }

    @Override
    public void setup() {
        try {
            persistedUserTestScenario.setup();
            persistedVoyageTestScenario.setup();

            Booking booking = new BookingTestDataBuilder().create(
                    persistedUserTestScenario.getPersistedUser(),
                    persistedVoyageTestScenario.getPersistedVoyage()
            );

            persistedBooking = repository.save(booking);
        } catch (TestScenarioException | BookingException exception) {
            throw new TestScenarioException("Failed to setup a booking test scenario", exception);
        }
    }

    public Booking getPersistedBooking() {
        return persistedBooking;
    }

    public String getAuthenticationHeader() {
        return persistedUserTestScenario.generateAuthenticationHeader();
    }

    public User getPersistedUser() {
        return persistedBooking.getUser();
    }

    public Voyage getPersistedVoyage() {
        return persistedBooking.getVoyage();
    }
}
