package calendar.user.dto;

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
