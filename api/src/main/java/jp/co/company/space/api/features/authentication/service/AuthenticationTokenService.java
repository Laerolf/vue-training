package jp.co.company.space.api.features.authentication.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.ObserverException;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jp.co.company.space.api.features.authentication.domain.JwtTokenCreationFactory;
import jp.co.company.space.api.features.authentication.exception.AuthenticationError;
import jp.co.company.space.api.features.authentication.exception.AuthenticationException;
import jp.co.company.space.api.features.authentication.exception.AuthenticationRuntimeException;
import jp.co.company.space.api.shared.util.LogBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * A service class handling the authentication token topic.
 */
@ApplicationScoped
public class AuthenticationTokenService {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationTokenService.class.getName());

    private static final int DEFAULT_TOKEN_LIFE_SPAN = 30;
    private static final String DEFAULT_TOKEN_ISSUER = "localhost";

    private final String tokenPrivateKeyFilePath;
    private final String tokenPublicKeyFilePath;

    private final String tokenLifeSpan;
    private final String tokenIssuer;

    private RSAPublicKey publicKey;

    @Inject
    protected AuthenticationTokenService(@ConfigProperty(name = "mp.jwt.decrypt.key.location") String privateKeyFilePath, @ConfigProperty(name = "mp.jwt.verify.publickey.location") String publicKeyFilePath, @ConfigProperty(name = "mp.jwt.verify.token.age") String lifeSpan, @ConfigProperty(name = "mp.jwt.verify.issuer") String issuer) {
        tokenPrivateKeyFilePath = privateKeyFilePath;
        tokenPublicKeyFilePath = publicKeyFilePath;
        tokenLifeSpan = lifeSpan;
        tokenIssuer = issuer;
    }

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
                validatePrivateKey();
                publicKey = loadTokenPublicKey();
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
    public String generateToken(String subject) {
        try {
            return new JwtTokenCreationFactory(subject, getTokenIssuer(), getTokenExpirationTime(), publicKey).generate();
        } catch (Exception exception) {
            LOGGER.warning(new LogBuilder(AuthenticationError.TOKEN_CREATE).withException(exception).build());
            throw new AuthenticationException(AuthenticationError.TOKEN_CREATE, exception);
        }
    }

    private String getTokenIssuer() {
        return Optional.ofNullable(tokenIssuer).orElse(DEFAULT_TOKEN_ISSUER);
    }

    private Date getTokenExpirationTime() {
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

        return Date.from(ZonedDateTime.now().plusMinutes(lifeSpan).toInstant());
    }

    /**
     * Loads the authentication token's public key to encrypt the token.
     *
     * @return The authentication token's public key.
     */
    private RSAPublicKey loadTokenPublicKey() throws AuthenticationException {
        try (InputStream publicKeyStream = AuthenticationTokenService.class.getClassLoader().getResourceAsStream(tokenPublicKeyFilePath)) {
            if (publicKeyStream == null) {
                throw new IllegalArgumentException("The authentication token's public key file could not be found with the provided path.");
            }

            String pemContent = readPemFile(publicKeyStream)
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s+", "");

            byte[] decoded = Base64.getDecoder().decode(pemContent);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(keySpec);
        } catch (IOException | IllegalArgumentException | NullPointerException | NoSuchAlgorithmException |
                 InvalidKeySpecException exception) {
            LOGGER.warning(
                    new LogBuilder(AuthenticationError.TOKEN_PUBLIC_KEY)
                            .withException(exception)
                            .withProperty("filePath", tokenPublicKeyFilePath)
                            .build()
            );
            throw new AuthenticationException(AuthenticationError.TOKEN_PUBLIC_KEY, exception);
        }
    }

    /**
     * Validates the authentication token's private key.
     */
    private void validatePrivateKey() throws AuthenticationException {
        try (InputStream privateKeyStream = AuthenticationTokenService.class.getClassLoader().getResourceAsStream(tokenPrivateKeyFilePath)) {
            if (privateKeyStream == null) {
                throw new IllegalArgumentException("The authentication token's private key file could not be found with the provided path.");
            }

            String pemContent = readPemFile(privateKeyStream)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            byte[] decoded = Base64.getDecoder().decode(pemContent);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        } catch (IOException | IllegalArgumentException | NullPointerException | NoSuchAlgorithmException |
                 InvalidKeySpecException exception) {
            LOGGER.warning(
                    new LogBuilder(AuthenticationError.TOKEN_PRIVATE_KEY)
                            .withException(exception)
                            .withProperty("filePath", tokenPrivateKeyFilePath)
                            .build()
            );
            throw new AuthenticationException(AuthenticationError.TOKEN_PRIVATE_KEY, exception);
        }
    }

    /**
     * Reads the content of a .pem file from the provided {@link InputStream} instance.
     *
     * @param keyInputStream The stream to read from.
     * @return A {@link String} instance representing the content of a .pem file.
     */
    private String readPemFile(InputStream keyInputStream) {
        try (Scanner scanner = new Scanner(keyInputStream, StandardCharsets.UTF_8)) {
            return scanner.useDelimiter("\\A").next();
        }
    }
}
