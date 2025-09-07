package jp.co.company.space.utils.shared;

import jp.co.company.space.api.application.FlywayProvider;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class DatabaseResetExtension implements AfterEachCallback {
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        FlywayProvider.reset();
    }
}
