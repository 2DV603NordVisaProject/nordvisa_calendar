package calendar.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class RegistrationDTO
 *
 * @author Axel Nilsson (axnion)
 */
public class RegistrationDTO {
    private String email;
    private String password;
    private String passwordConfirmation;
    private String organization;
    @JsonProperty("g-recaptcha-response")
    private String recaptcha;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public String getOrganization() {
        return organization;
    }

    public String getRecaptcha() {
        return recaptcha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
