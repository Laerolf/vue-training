
package jp.co.company.space.api;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for global API functionality.
 */
@HelidonTest
class MainTest {
        @Inject
        private WebTarget target;

        @Test
        void healthCheck() {
                // When
                Response response = target.path("health").request().get();

                // Then
                assertEquals(Status.OK_200.code(), response.getStatus());
        }

        @Test
        void notFound() {
                // When
                Response response = target.path(UUID.randomUUID().toString()).request().get();

                // Then
                assertEquals(Status.NOT_FOUND_404.code(), response.getStatus());
        }
}
