package jp.co.nova.gate.api.features.authentication.input;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.nova.gate.api.shared.openApi.Examples.USER_EMAIL_ADDRESS_EXAMPLE;
import static jp.co.nova.gate.api.shared.openApi.Examples.USER_PASSWORD_EXAMPLE;

/**
 * A POJO representing a form with the details of a login request.
 */
public class LoginRequestForm {

    /**
     * The email address of the login request.
     */
    @Schema(description = "The email address of the login request.", required = true, example = USER_EMAIL_ADDRESS_EXAMPLE)
    public String emailAddress;

    /**
     * The password of the login request.
     */
    @Schema(description = "The password of the login request.", required = true, example = USER_PASSWORD_EXAMPLE)
    public String password;

    protected LoginRequestForm() {
    }

    public LoginRequestForm(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }
}
