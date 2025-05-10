package jp.co.company.space.api.features.authentication.domain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jp.co.company.space.api.features.authentication.exception.AuthenticationError;
import jp.co.company.space.api.features.authentication.exception.AuthenticationException;

import java.security.interfaces.RSAPrivateKey;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * A factory creating a new JWT authentication token.
 */
public class JwtTokenCreationFactory {

    /**
     * The subject of the JWT authentication token.
     */
    private final String subject;

    /**
     * The issuer of the JWT authentication token.
     */
    private final String issuer;

    /**
     * The life span of the JWT authentication token.
     */
    private final Duration lifeSpan;

    /**
     * The public key of the JWT authentication token.
     */
    private final RSAPrivateKey privateKey;

    /**
     * Creates a new {@link JwtTokenCreationFactory} instance.
     *
     * @param subject    The subject of the JWT authentication token.
     * @param issuer     The issuer of the JWT authentication token.
     * @param lifeSpan   The life span of the JWT authentication token.
     * @param privateKey The private key of the JWT authentication token.
     */
    public JwtTokenCreationFactory(String subject, String issuer, Duration lifeSpan, RSAPrivateKey privateKey) {
        this.subject = subject;
        this.issuer = issuer;
        this.lifeSpan = lifeSpan;
        this.privateKey = privateKey;
    }

    /**
     * Creates a new JWT authentication token.
     *
     * @return A JWT authentication token.
     */
    public SignedJWT generate() throws AuthenticationException {
        try {
            ZonedDateTime issueDate = ZonedDateTime.now();
            ZonedDateTime expirationDate = issueDate.plus(lifeSpan);

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(subject)
                    .issuer(issuer)
                    .issueTime(Date.from(issueDate.toInstant()))
                    .expirationTime(Date.from(expirationDate.toInstant()))
                    .build();

            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).build();

            SignedJWT signedJWT = new SignedJWT(header, claims);
            RSASSASigner signer = new RSASSASigner(privateKey);
            signedJWT.sign(signer);

            return signedJWT;
        } catch (JOSEException exception) {
            throw new AuthenticationException(AuthenticationError.TOKEN_CREATE, exception);
        }
    }
}
