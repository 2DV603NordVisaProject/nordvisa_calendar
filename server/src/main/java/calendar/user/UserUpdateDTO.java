package calendar.user;

/**
 * Class UserUpdateDTO
 *
 * @author Axel Nilsson (axnion)
 */
class UserUpdateDTO {
    private String email;
    private String oldPassword;
    private String password;
    private String passwordConfirmation;
    private String organization;

    String getEmail() {
        return email;
    }

    String getOldPassword() {
        return oldPassword;
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

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
