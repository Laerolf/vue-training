package jp.co.company.space.api;

import io.helidon.microprofile.server.Server;
import jp.co.company.space.api.application.FlywayProvider;

public final class Main {
    public static void main(final String[] args) {
        FlywayProvider.setup();

        startServer();
    }

    /**
     * Starts the server.
     */
    private static void startServer() {
        Server.create().start();
    }
}
