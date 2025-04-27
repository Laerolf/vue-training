package jp.co.company.space.api.shared.util;

import jp.co.company.space.api.features.booking.exception.BookingError;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link LogBuilder} class.
 */
class LogBuilderTest {

    @Nested
    class build {

        private static final String BASE_MESSAGE = "Hello world";

        @Test
        void withMessageOnly() {
            // When
            String logMessage = new LogBuilder(BASE_MESSAGE).build();

            // Then
            assertEquals(BASE_MESSAGE, logMessage);
        }

        @Test
        void withDomainErrorOnly() {
            // When
            String logMessage = new LogBuilder(BookingError.MISSING_ID).build();

            // Then
            assertEquals(String.format("[%s] %s", BookingError.MISSING_ID.getKey(), BookingError.MISSING_ID.getDescription()), logMessage);
        }

        @Test
        void withAnException() {
            // Given
            RuntimeException exception = new RuntimeException("The roof is on fire!");

            // When
            String logMessage = new LogBuilder(BASE_MESSAGE).withException(exception).build();

            // Then
            assertEquals(String.format("%s: %s", BASE_MESSAGE, exception.getMessage()), logMessage);
        }

        @Test
        void withAProperty() {
            // Given
            String propertyKey = "id";
            String propertyValue = "404";

            // When
            String logMessage = new LogBuilder(BASE_MESSAGE).withProperty(propertyKey, propertyValue).build();

            // Then
            assertEquals(String.format("%s [%s=%s]", BASE_MESSAGE, propertyKey, propertyValue), logMessage);
        }

        @Test
        void withMultipleProperties() {
            // Given
            String propertyAKey = "id";
            String propertyAValue = "404";

            String propertyBKey = "env";
            String propertyBValue = "test";

            // When
            String logMessage = new LogBuilder(BASE_MESSAGE).withProperty(propertyAKey, propertyAValue).withProperty(propertyBKey, propertyBValue).build();

            // Then
            assertEquals(String.format("%s [%s=%s, %s=%s]", BASE_MESSAGE, propertyAKey, propertyAValue, propertyBKey, propertyBValue), logMessage);
        }

        @Test
        void withAnExceptionAndAProperty() {
            // Given
            RuntimeException exception = new RuntimeException("Something smells bad.");

            String propertyKey = "id";
            String propertyValue = "404";

            // When
            String logMessage = new LogBuilder(BASE_MESSAGE).withException(exception).withProperty(propertyKey, propertyValue).build();

            // Then
            assertEquals(String.format("%s: %s [%s=%s]", BASE_MESSAGE, exception.getMessage(), propertyKey, propertyValue), logMessage);
        }
    }

}