package jp.co.nova.gate.api.features.authentication.service;

import com.nimbusds.jwt.SignedJWT;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.ObserverException;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jp.co.nova.gate.api.features.authentication.domain.JwtTokenCreationFactory;
import jp.co.nova.gate.api.features.authentication.exception.AuthenticationError;
import jp.co.nova.gate.api.features.authentication.exception.AuthenticationException;
import jp.co.nova.gate.api.features.authentication.exception.AuthenticationRuntimeException;
import jp.co.nova.gate.api.shared.util.LogBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * A service class handling the authentication token topic.
 */
@ApplicationScoped
public class AuthenticationTokenService {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationTokenService.class.getName());

    /**
     * The default token life span in seconds.
     */
    private static final int DEFAULT_TOKEN_LIFE_SPAN = 30 * 60 * 1000;

    /**
     * The default name of the token issuer.
     */
    private static final String DEFAULT_TOKEN_ISSUER = "NovaGate API";

    @Inject
    @ConfigProperty(name = "mp.jwt.create.privatekey.location")
    private String tokenPrivateKeyFilePath;

    @Inject
    @ConfigProperty(name = "mp.jwt.verify.publickey.location")
    private String tokenPublicKeyFilePath;

    @Inject
    @ConfigProperty(name = "mp.jwt.verify.token.age")
    private String tokenLifeSpan;

    @Inject
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String tokenIssuer;

    private RSAPrivateKey privateKey;

    protected AuthenticationTokenService() {}

    /**
     * Initializes the {@link AuthenticationTokenService}.
     *
     * @param init The event that triggers the initialization.
     */
    @Transactional
    public void onStartUp(@Observes @Initialized(ApplicationScoped.class) Object init) throws AuthenticationRuntimeException {
        try {
            LOGGER.info(new LogBuilder("Initializing the authentication token service.").build());

            try {
                validateTokenPublicKey();
                privateKey = loadPrivateKey();
            } catch (AuthenticationException exception) {
                LOGGER.severe(new LogBuilder(AuthenticationError.TOKEN_SERVICE_LOAD_INITIAL_DATA).withException(exception).build());
                throw exception;
            }

            LOGGER.info(new LogBuilder("The authentication token service is ready!").build());
        } catch (IllegalArgumentException | ObserverException exception) {
            LOGGER.severe(new LogBuilder(AuthenticationError.TOKEN_SERVICE_START_UP).withException(exception).build());
            throw new AuthenticationRuntimeException(AuthenticationError.TOKEN_SERVICE_START_UP, exception);
        }
    }

    /**
     * Generates a new authentication token for the provided subject.
     *
     * @param subject The subject of the authentication token.
     * @return An authentication token.
     */
    public SignedJWT generateToken(String subject) {
        try {
            return new JwtTokenCreationFactory(subject, getTokenIssuer(), getTokenLifeSpan(), privateKey).generate();
        } catch (AuthenticationException exception) {
            LOGGER.warning(new LogBuilder(AuthenticationError.TOKEN_CREATE).withException(exception).build());
            throw new AuthenticationException(AuthenticationError.TOKEN_CREATE, exception);
        }
    }

    private String getTokenIssuer() {
        return Optional.ofNullable(tokenIssuer).orElse(DEFAULT_TOKEN_ISSUER);
    }

    private Duration getTokenLifeSpan() {
        try {
            int lifeSpan = DEFAULT_TOKEN_LIFE_SPAN;

            try {
                lifeSpan = Integer.parseInt(tokenLifeSpan);
            } catch (NumberFormatException exception) {
                LOGGER.warning(
                        new LogBuilder(AuthenticationError.TOKEN_CREATE_INVALID_EXPIRATION_MINUTES)
                                .withException(exception)
                                .withProperty("expirationTime", tokenLifeSpan)
                                .build()
                );
                LOGGER.info(
                        new LogBuilder("Using the default authentication token expiration time.")
                                .withProperty("defaultExpirationTime", DEFAULT_TOKEN_LIFE_SPAN)
                                .build()
                );
            }

            return Duration.ofSeconds(lifeSpan);
        } catch (ArithmeticException exception) {
            LOGGER.warning(new LogBuilder(AuthenticationError.TOKEN_CREATE_INVALID_LIFE_SPAN).withException(exception).build());
            throw new AuthenticationException(AuthenticationError.TOKEN_CREATE_INVALID_LIFE_SPAN);
        }
    }

    /**
     * Validates the authentication token's public key.
     */
    private void validateTokenPublicKey() throws AuthenticationException {
        try (InputStream publicKeyStream = AuthenticationTokenService.class.getClassLoader().getResourceAsStream(tokenPublicKeyFilePath)) {
            if (publicKeyStream == null) {
                throw new IllegalArgumentException("The authentication token's public key file could not be found with the provided path.");
            }

            String pemContent = readPemFile(publicKeyStream);
            byte[] decoded = Base64.getDecoder().decode(pemContent);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
            KeyFactory.getInstance("RSA").generatePublic(keySpec);
        } catch (IOException | IllegalArgumentException | NullPointerException | NoSuchAlgorithmException |
                 InvalidKeySpecException exception) {
            LOGGER.warning(
                    new LogBuilder(AuthenticationError.MISSING_TOKEN_PUBLIC_KEY)
                            .withException(exception)
                            .withProperty("filePath", tokenPublicKeyFilePath)
                            .build()
            );
            throw new AuthenticationException(AuthenticationError.MISSING_TOKEN_PUBLIC_KEY, exception);
        }
    }

    /**
     * Returns the authentication token's private key.
     *
     * @return A {@link RSAPrivateKey}.
     */
    private RSAPrivateKey loadPrivateKey() throws AuthenticationException {
        try (InputStream privateKeyStream = AuthenticationTokenService.class.getClassLoader().getResourceAsStream(tokenPrivateKeyFilePath)) {
            if (privateKeyStream == null) {
                throw new IllegalArgumentException("The authentication token's private key file could not be found with the provided path.");
            }

            String pemContent = readPemFile(privateKeyStream);
            byte[] decoded = Base64.getDecoder().decode(pemContent);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        } catch (IOException | IllegalArgumentException | NullPointerException | NoSuchAlgorithmException |
                 InvalidKeySpecException exception) {
            LOGGER.warning(
                    new LogBuilder(AuthenticationError.MISSING_TOKEN_PRIVATE_KEY)
                            .withException(exception)
                            .withProperty("filePath", tokenPrivateKeyFilePath)
                            .build()
            );
            throw new AuthenticationException(AuthenticationError.MISSING_TOKEN_PRIVATE_KEY, exception);
        }
    }

    /**
     * Reads the content of a .pem file from the provided {@link InputStream}.
     *
     * @param keyInputStream The stream to read from.
     * @return A {@link String} representing the content of a .pem file.
     */
    private String readPemFile(InputStream keyInputStream) {
        try (Scanner scanner = new Scanner(keyInputStream, StandardCharsets.UTF_8)) {
            return scanner.useDelimiter("\\A").next()
                    .replaceAll("-----BEGIN ([A-Z ]+)-----", "")
                    .replaceAll("-----END ([A-Z ]+)-----", "")
                    .replaceAll("\\s+", "");
        }
    }
}
