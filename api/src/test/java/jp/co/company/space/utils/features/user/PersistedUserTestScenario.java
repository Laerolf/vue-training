package jp.co.company.space.utils.features.user;

import jakarta.inject.Inject;
import jp.co.company.space.api.features.authentication.domain.JwtTokenCreationFactory;
import jp.co.company.space.api.features.authentication.exception.AuthenticationException;
import jp.co.company.space.api.features.user.domain.User;
import jp.co.company.space.api.features.user.exception.UserException;
import jp.co.company.space.api.features.user.repository.UserRepository;
import jp.co.company.space.utils.shared.testScenario.TestScenario;
import jp.co.company.space.utils.shared.testScenario.TestScenarioException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.Scanner;

/**
 * A {@link TestScenario} with a persisted user.
 */
public class PersistedUserTestScenario implements TestScenario {

    @Inject
    private UserRepository userRepository;

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

    @Inject
    @ConfigProperty(name = "mp.jwt.token.cookie")
    private String authenticationCookieName;

    private RSAPrivateKey privateKey;

    private User persistedUser;
    private User unpersistedUser;

    @Override
    public void setup() throws TestScenarioException {
        try {
            privateKey = loadPrivateKey();

            unpersistedUser = new UserTestDataBuilder().create();
            User userToPersist = new UserTestDataBuilder()
                    .withId(unpersistedUser.getId())
                    .withLastName(unpersistedUser.getLastName())
                    .withFirstName(unpersistedUser.getFirstName())
                    .withEmailAddress(unpersistedUser.getEmailAddress())
                    .withPassword(unpersistedUser.getPassword())
                    .withHashPassword()
                    .create();
            persistedUser = userRepository.save(userToPersist);
        } catch (UserException | IOException | NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw new TestScenarioException("Failed to setup a registered user test scenario", exception);
        }
    }

    /**
     * Returns the authentication token's private key.
     *
     * @return A {@link RSAPrivateKey} instance.
     */
    private RSAPrivateKey loadPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        try (InputStream privateKeyStream = PersistedUserTestScenario.class.getClassLoader().getResourceAsStream(tokenPrivateKeyFilePath)) {
            if (privateKeyStream == null) {
                throw new IllegalArgumentException("The authentication token's private key file could not be found with the provided path.");
            }

            String pemContent = readPemFile(privateKeyStream)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            byte[] decoded = Base64.getDecoder().decode(pemContent);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(keySpec);
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

    public String generateAuthenticationToken() throws AuthenticationException, IllegalStateException {
        Duration lifeSpan = Duration.ofSeconds(Integer.parseInt(tokenLifeSpan));
        return new JwtTokenCreationFactory(persistedUser.getId(), tokenIssuer, lifeSpan, privateKey).generate().serialize();
    }

    public String generateAuthenticationHeader() {
        String authenticationToken = generateAuthenticationToken();
        return String.format("%s %s", authenticationCookieName, authenticationToken);
    }

    public User getUnpersistedUser() {
        return unpersistedUser;
    }

    public User getPersistedUser() {
        return persistedUser;
    }
}
