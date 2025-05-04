package jp.co.company.space.api.features.authentication.domain;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import jp.co.company.space.api.features.authentication.exception.AuthenticationError;
import jp.co.company.space.api.features.authentication.exception.AuthenticationException;

import java.security.interfaces.RSAPublicKey;
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
     * The expiration time of the JWT authentication token.
     */
    private final Date expirationTime;

    /**
     * The public key of the JWT authentication token.
     */
    private final RSAPublicKey publicKey;

    /**
     * Creates a new {@link JwtTokenCreationFactory} instance.
     *
     * @param subject        The subject of the JWT authentication token.
     * @param issuer         The issuer of the JWT authentication token.
     * @param expirationTime The expiration time of the JWT authentication token.
     * @param publicKey      The public key of the JWT authentication token.
     */
    public JwtTokenCreationFactory(String subject, String issuer, Date expirationTime, RSAPublicKey publicKey) {
        this.subject = subject;
        this.issuer = issuer;
        this.expirationTime = expirationTime;
        this.publicKey = publicKey;
    }

    /**
     * Creates a new JWT authentication token.
     *
     * @return A JWT authentication token.
     */
    public String generate() throws AuthenticationException {
        try {
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(subject)
                    .issuer(issuer)
                    .expirationTime(expirationTime)
                    .build();

            JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM);
            EncryptedJWT encryptedJWT = new EncryptedJWT(header, claims);

            encryptedJWT.encrypt(new RSAEncrypter(publicKey));

            return encryptedJWT.serialize();
        } catch (JOSEException exception) {
            throw new AuthenticationException(AuthenticationError.TOKEN_CREATE, exception);
        }
    }
}
