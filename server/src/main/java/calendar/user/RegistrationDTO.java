package calendar.user;

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

    void validate() throws Exception {
        // TODO: Verify email format

        if(emailAlreadyRegistered()) {
            throw new Exception("Email address " + email + " is already registered in the system");
        }
        else if (passwordConfirmationDoesNotMatch()) {
            throw new Exception("The passwords did not match");
        }
        else if(passwordNotValid()) {
            throw new Exception("The password was not valid");
        }
    }

    private boolean emailAlreadyRegistered() {
        UserDAO dao = new UserDAO();
        User user = dao.getUserByEmail(email);
        return user != null;
    }

    private boolean passwordConfirmationDoesNotMatch() {
        return !password.equals(passwordConfirmation);
    }

    private boolean passwordNotValid() {
        return password.length() <= 10 && password.length() > 255;
    }
}
