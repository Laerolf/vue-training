package jp.co.company.space.api.shared.util;

import io.helidon.http.Status;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the {@link ResponseFactory} class.
 */
class ResponseFactoryTest {

    @Test
    void notFound() {
        // When
        Response notFoundResponse = ResponseFactory.notFound().build();

        // Then
        assertNotNull(notFoundResponse);
        assertEquals(Status.NOT_FOUND_404.code(), notFoundResponse.getStatus());
    }
}