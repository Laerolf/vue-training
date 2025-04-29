package jp.co.company.space.api.features.booking.domain;

import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.utils.features.user.UserTestDataBuilder;
import jp.co.company.space.utils.features.voyage.user.VoyageTestDataBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Booking} class.
 */
class BookingTest {

    private static final String ID = UUID.randomUUID().toString();
    private static final ZonedDateTime CREATION_DATE = ZonedDateTime.now();
    private static final BookingStatus STATUS = BookingStatus.CREATED;
    private static final User USER = new UserTestDataBuilder().create();
    private static final Voyage VOYAGE = new VoyageTestDataBuilder().create();

    @Nested
    class create {
        @Test
        void withValidArguments() {
            // When
            Booking booking = Booking.create(USER, VOYAGE);

            // Then
            assertNotNull(booking);

            assertNotNull(booking.getId());
            assertNotNull(booking.getCreationDate());
            assertEquals(ZonedDateTime.now().getDayOfYear(), booking.getCreationDate().getDayOfYear());
            assertEquals(BookingStatus.DRAFT, booking.getStatus());
            assertEquals(USER, booking.getUser());
            assertEquals(VOYAGE, booking.getVoyage());
        }

        @Test
        void withoutUser() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Booking.create(null, VOYAGE));

            // Then
            assertEquals("The user of the booking is missing.", exception.getMessage());
        }

        @Test
        void withoutVoyage() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Booking.create(USER, null));

            // Then
            assertEquals("The voyage of the booking is missing.", exception.getMessage());
        }
    }

    @Nested
    class reconstruct {
        @Test
        void withValidArguments() {
            // When
            Booking booking = Booking.reconstruct(ID, CREATION_DATE, STATUS, USER, VOYAGE);

            // Then
            assertNotNull(booking);

            assertEquals(ID, booking.getId());
            assertEquals(CREATION_DATE, booking.getCreationDate());
            assertEquals(STATUS, booking.getStatus());
            assertEquals(USER, booking.getUser());
            assertEquals(VOYAGE, booking.getVoyage());
        }

        @Test
        void withoutId() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Booking.reconstruct(null, CREATION_DATE, STATUS, USER, VOYAGE));

            // Then
            assertEquals("The ID of the booking is missing.", exception.getMessage());
        }

        @Test
        void withoutCreationDate() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Booking.reconstruct(ID, null, STATUS, USER, VOYAGE));

            // Then
            assertEquals("The creation date of the booking is missing.", exception.getMessage());
        }

        @Test
        void withoutStatus() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Booking.reconstruct(ID, CREATION_DATE, null, USER, VOYAGE));

            // Then
            assertEquals("The status of the booking is missing.", exception.getMessage());
        }

        @Test
        void withoutUser() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Booking.reconstruct(ID, CREATION_DATE, STATUS, null, VOYAGE));

            // Then
            assertEquals("The user of the booking is missing.", exception.getMessage());
        }

        @Test
        void withoutVoyage() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Booking.reconstruct(ID, CREATION_DATE, STATUS, USER, null));

            // Then
            assertEquals("The voyage of the booking is missing.", exception.getMessage());
        }
    }

    @Test
    void whenMatched() {
        // When
        Booking bookingA = Booking.create(USER, VOYAGE);
        Booking bookingB = Booking.reconstruct(bookingA.getId(), bookingA.getCreationDate(), bookingA.getStatus(), bookingA.getUser(), bookingA.getVoyage());

        // Then
        assertEquals(bookingA, bookingB);
    }

    @Test
    void whenNotMatched() {
        // When
        Booking bookingA = Booking.create(USER, VOYAGE);
        Booking bookingB = Booking.create(USER, VOYAGE);

        // Then
        assertNotEquals(bookingA, bookingB);
    }
}