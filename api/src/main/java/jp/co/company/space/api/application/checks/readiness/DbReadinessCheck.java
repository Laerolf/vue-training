package jp.co.company.space.api.application.checks.readiness;

import java.time.ZonedDateTime;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * This class is a readiness check for the database.
 */
@Readiness
@ApplicationScoped
public class DbReadinessCheck implements HealthCheck {
    private ZonedDateTime readyTime;

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    public void onStartUp(@Observes @Initialized(ApplicationScoped.class) Object init) {
        readyTime = ZonedDateTime.now();
    }

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("DbReadinessCheck")
                .withData("description", "Tests if the database is ready to be used.");

        if (isReady()) {
            return responseBuilder.up().withData("readyTime", readyTime.toString()).build();
        } else {
            return responseBuilder.down().build();
        }
    }

    /**
     * Tests if the database is ready.
     * 
     * @return true if the database is ready, false otherwise.
     */
    private boolean isReady() {
        try {
            entityManager.createNativeQuery("SELECT 1").getFirstResult();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

}
