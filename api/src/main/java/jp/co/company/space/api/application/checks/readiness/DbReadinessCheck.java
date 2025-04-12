package jp.co.company.space.api.application.checks.readiness;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

/**
 * This class is a readiness check for the database.
 */
@Readiness
@ApplicationScoped
public class DbReadinessCheck implements HealthCheck {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("DbReadinessCheck")
                .withData("description", "Tests if the database is ready to be used.").status(isReady()).build();
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
