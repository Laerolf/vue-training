package jp.co.company.space.api.features.user.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link User} class.
 */
public class UserTest {

    private static final String EXAMPLE_ID = UUID.randomUUID().toString();
    private static final String EXAMPLE_LAST_NAME = "Jekyll";
    private static final String EXAMPLE_FIRST_NAME = "Henry";
    private static final String EXAMPLE_EMAIL_ADDRESS = "henry.jekyll@example.com";
    private static final String EXAMPLE_PASSWORD = "******";

    @Nested
    class create {
        @Test
        void withAllArguments() {
            // When
            User user = User.create(EXAMPLE_LAST_NAME, EXAMPLE_FIRST_NAME, EXAMPLE_EMAIL_ADDRESS, EXAMPLE_PASSWORD);

            // Then
            assertNotNull(user.getId());
            assertEquals(EXAMPLE_LAST_NAME, user.getLastName());
            assertEquals(EXAMPLE_FIRST_NAME, user.getFirstName());
            assertEquals(EXAMPLE_EMAIL_ADDRESS, user.getEmailAddress());
            assertEquals(EXAMPLE_PASSWORD, user.getPassword());
        }

        @Test
        void withoutALastName() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> User.create(null, EXAMPLE_FIRST_NAME, EXAMPLE_EMAIL_ADDRESS, EXAMPLE_PASSWORD));

            // Then
            assertNotNull("The last name of the user is missing.", exception.getMessage());
        }

        @Test
        void withoutAFirstName() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> User.create(EXAMPLE_LAST_NAME, null, EXAMPLE_EMAIL_ADDRESS, EXAMPLE_PASSWORD));

            // Then
            assertNotNull("The last name of the user is missing.", exception.getMessage());
        }

        @Test
        void withoutAnEmailAddress() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> User.create(EXAMPLE_LAST_NAME, EXAMPLE_FIRST_NAME, null, EXAMPLE_PASSWORD));

            // Then
            assertNotNull("The email address of the user is missing.", exception.getMessage());
        }

        @Test
        void withoutAPassword() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> User.create(EXAMPLE_LAST_NAME, EXAMPLE_FIRST_NAME, EXAMPLE_EMAIL_ADDRESS, null));

            // Then
            assertNotNull("The password of the user is missing.", exception.getMessage());
        }
    }

    @Nested
    class reconstruct {

        @Test
        void withAllArguments() {
            // When
            User user = User.reconstruct(EXAMPLE_ID, EXAMPLE_LAST_NAME, EXAMPLE_FIRST_NAME, EXAMPLE_EMAIL_ADDRESS,
                    EXAMPLE_PASSWORD);

            // Then
            assertEquals(EXAMPLE_ID, user.getId());
            assertEquals(EXAMPLE_LAST_NAME, user.getLastName());
            assertEquals(EXAMPLE_FIRST_NAME, user.getFirstName());
            assertEquals(EXAMPLE_EMAIL_ADDRESS, user.getEmailAddress());
            assertEquals(EXAMPLE_PASSWORD, user.getPassword());
        }

        @Test
        void withoutAnId() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> User
                    .reconstruct(null, EXAMPLE_LAST_NAME, EXAMPLE_FIRST_NAME, EXAMPLE_EMAIL_ADDRESS, EXAMPLE_PASSWORD));

            // Then
            assertNotNull("The ID of the user is missing.", exception.getMessage());
        }

        @Test
        void withoutALastName() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> User
                    .reconstruct(EXAMPLE_ID, null, EXAMPLE_FIRST_NAME, EXAMPLE_EMAIL_ADDRESS, EXAMPLE_PASSWORD));

            // Then
            assertNotNull("The last name of the user is missing.", exception.getMessage());
        }

        @Test
        void withoutAFirstName() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> User
                    .reconstruct(EXAMPLE_ID, EXAMPLE_LAST_NAME, null, EXAMPLE_EMAIL_ADDRESS, EXAMPLE_PASSWORD));

            // Then
            assertNotNull("The last name of the user is missing.", exception.getMessage());
        }

        @Test
        void withoutAnEmailAddress() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> User.reconstruct(EXAMPLE_ID, EXAMPLE_LAST_NAME, EXAMPLE_FIRST_NAME, null, EXAMPLE_PASSWORD));

            // Then
            assertNotNull("The email address of the user is missing.", exception.getMessage());
        }

        @Test
        void withoutAPassword() {
            // When
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> User
                    .reconstruct(EXAMPLE_ID, EXAMPLE_LAST_NAME, EXAMPLE_FIRST_NAME, EXAMPLE_EMAIL_ADDRESS, null));

            // Then
            assertNotNull("The password of the user is missing.", exception.getMessage());
        }
    }

    @Test
    void whenMatched() {
        // Given
        User userA = User.create(EXAMPLE_LAST_NAME, EXAMPLE_FIRST_NAME, EXAMPLE_EMAIL_ADDRESS, EXAMPLE_PASSWORD);
        User duplicatedUserA = User.reconstruct(userA.getId(), EXAMPLE_LAST_NAME, EXAMPLE_FIRST_NAME,
                EXAMPLE_EMAIL_ADDRESS, EXAMPLE_PASSWORD);

        // Then
        assertEquals(userA, duplicatedUserA);
    }

    @Test
    void whenNotMatched() {
        // Given
        User userA = User.create(EXAMPLE_LAST_NAME, EXAMPLE_FIRST_NAME, EXAMPLE_EMAIL_ADDRESS, EXAMPLE_PASSWORD);
        User userB = User.create(EXAMPLE_LAST_NAME, EXAMPLE_FIRST_NAME, EXAMPLE_EMAIL_ADDRESS, EXAMPLE_PASSWORD);

        // Then
        assertNotEquals(userA, userB);
    }
}
