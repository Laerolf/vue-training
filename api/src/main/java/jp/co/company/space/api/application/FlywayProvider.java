package jp.co.company.space.api.application;

import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

/**
 * This class provides methods to interact with {@link Flyway}.
 */
@Provider
public class FlywayProvider {

    private static final Flyway flyway;

    static {
        Config config = ConfigProvider.getConfig();

        String url = config.getOptionalValue("javax.sql.DataSource.spaceApiDs.dataSource.url", String.class)
                .orElse(null);
        String username = config.getOptionalValue("javax.sql.DataSource.spaceApiDs.dataSource.user", String.class)
                .orElse(null);
        String password = config.getOptionalValue("javax.sql.DataSource.spaceApiDs.dataSource.password", String.class)
                .orElse(null);
        boolean cleanDisabled = config.getOptionalValue("flyway.cleanDisabled", Boolean.class).orElse(true);

        flyway = Flyway.configure().dataSource(url, username, password).cleanDisabled(cleanDisabled)
                .baselineOnMigrate(true).load();
    }

    public static void setup() {
        try {
            flyway.migrate();
        } catch (FlywayException exception) {
            throw new RuntimeException("Failed to setup the database with Flyway.", exception);
        }
    }

    public static void clean() {
        try {
            flyway.clean();
        } catch (FlywayException exception) {
            throw new RuntimeException("Failed to clean the database with Flyway.", exception);
        }
    }

    public static void reset() {
        try {
            clean();
            setup();
        } catch (FlywayException exception) {
            throw new RuntimeException("Failed to clean the database with Flyway.", exception);
        }
    }
}
