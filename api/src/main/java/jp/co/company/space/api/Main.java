package jp.co.company.space.api;

import java.io.IOException;
import io.helidon.microprofile.server.Server;
import jp.co.company.space.api.application.FlywayProvider;

public final class Main {
    protected Main() {}

    public static void main(final String[] args) throws IOException {
        FlywayProvider.setup();

        startServer();
    }

    /**
     * Starts the server.
     */
    protected static void startServer() {
        Server.create().start();
    }
}
