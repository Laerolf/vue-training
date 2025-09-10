package jp.co.nova.gate.api.shared.exception;

import jp.co.nova.gate.api.features.user.exception.UserError;
import jp.co.nova.gate.api.features.user.exception.UserException;
import jp.co.nova.gate.api.shared.dto.DomainErrorDto;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DomainErrorDtoBuilder} class.
 */
class DomainErrorDtoBuilderTest {

    private static final UserError USER_ERROR = UserError.FIND_BY_ID;
    private static final UserException USER_EXCEPTION = new UserException(USER_ERROR);

    @Nested
    class build {
        @Test
        void withDomainError() {
            // When
            DomainErrorDto errorDto = new DomainErrorDtoBuilder(USER_ERROR).build();

            // Then
            assertEquals(USER_ERROR.getKey(), errorDto.key);
            assertEquals(USER_ERROR.getDescription(), errorDto.message);
            assertNull(errorDto.properties);
        }

        @Test
        void withDomainException() {
            // When
            DomainErrorDto errorDto = new DomainErrorDtoBuilder(USER_EXCEPTION).build();

            // Then
            assertEquals(USER_EXCEPTION.getKey(), errorDto.key);
            assertEquals(USER_EXCEPTION.getMessage(), errorDto.message);
            assertNull(errorDto.properties);
        }

        @Test
        void withADomainExceptionAndACause() {
            // Given
            UserException exceptionCause = new UserException(UserError.SAVE);
            UserException exception = new UserException(UserError.CREATE, new UserException(UserError.SAVE, exceptionCause));

            // When
            DomainErrorDto errorDto = new DomainErrorDtoBuilder(exception).withCause().build();

            // Then
            assertEquals(UserError.CREATE.getKey(), errorDto.key);
            assertEquals(UserError.CREATE.getDescription(), errorDto.message);
            assertNotNull(errorDto.properties);

            DomainErrorDto cause = (DomainErrorDto) errorDto.properties.get("cause");
            assertNotNull(cause);

            assertEquals(UserError.SAVE.getKey(), cause.key);
            assertEquals(UserError.SAVE.getDescription(), cause.message);
            assertNull(cause.properties);
        }
    }

    @Nested
    class withProperty {
        @Test
        void withOneProperty() {
            // Given
            String userId = UUID.randomUUID().toString();

            // When
            DomainErrorDto errorDto = new DomainErrorDtoBuilder(USER_ERROR).withProperty("id", userId).build();

            // Then
            assertEquals(USER_ERROR.getKey(), errorDto.key);
            assertEquals(USER_ERROR.getDescription(), errorDto.message);

            assertNotNull(errorDto.properties);
            assertEquals(1, errorDto.properties.size());

            assertTrue(errorDto.properties.containsKey("id"));
            assertEquals(userId, errorDto.properties.get("id"));
        }

        @Test
        void withMultipleProperties() {
            // Given
            String userId = UUID.randomUUID().toString();
            String requestId = UUID.randomUUID().toString();

            // When
            DomainErrorDto errorDto = new DomainErrorDtoBuilder(USER_ERROR)
                    .withProperty("id", userId)
                    .withProperty("requestId", requestId)
                    .build();

            // Then
            assertEquals(USER_ERROR.getKey(), errorDto.key);
            assertEquals(USER_ERROR.getDescription(), errorDto.message);

            assertNotNull(errorDto.properties);
            assertEquals(2, errorDto.properties.size());

            assertTrue(errorDto.properties.containsKey("id"));
            assertEquals(userId, errorDto.properties.get("id"));
            assertTrue(errorDto.properties.containsKey("requestId"));
            assertEquals(requestId, errorDto.properties.get("requestId"));
        }
    }
}