package calendar.user;

/**
 * Class RegistrationDTO
 *
 * @author Axel Nilsson (axnion)
 */
class RegistrationDTO {
    private String email;
    private String password;
    private String passwordConfirmation;
    private String organization;

    String getEmail() {
        return email;
    }

    String getPassword() {
        return password;
    }

    String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    String getOrganization() {
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
