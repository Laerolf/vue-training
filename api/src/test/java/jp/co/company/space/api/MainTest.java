
package jp.co.company.space.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import io.helidon.microprofile.testing.junit5.HelidonTest;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Unit tests for general API functionality, configured in the {@link Main} class.
 */
@HelidonTest
class MainTest {
        /**
         * The target to test.
         */
        @Inject
        private WebTarget target;

        @Test
        void healthCheckTest() {
                Response response = target.path("health").request().get();
                assertThat(response.getStatus(), is(200));
        }
}
