package jp.co.company.space.api.application.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@SecurityScheme(securitySchemeName = "jwt", description = "A JWT as a HTTP header for authentication.", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@LoginConfig(authMethod = "MP-JWT")
@ApplicationScoped
public class ProtectedApplication extends Application {
}
