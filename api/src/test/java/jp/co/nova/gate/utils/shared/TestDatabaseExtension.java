package jp.co.nova.gate.utils.shared;

import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.BeforeBeanDiscovery;
import jakarta.enterprise.inject.spi.Extension;
import jp.co.nova.gate.api.application.FlywayProvider;

/**
 * Extension to handle the database setup for the tests.
 */
public class TestDatabaseExtension implements Extension {

    /**
     * Sets up the database before the bean discovery.
     *
     * @param event The before bean discovery event
     */
    void beforeBeanDiscovery(@Observes BeforeBeanDiscovery event) {
        FlywayProvider.setup();
    }

}
