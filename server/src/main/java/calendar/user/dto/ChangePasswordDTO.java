package calendar.user.dto;

/**
 * Class ChangePasswordDTO
 *
 * @author Axel Nilsson (axnion)
 */
public class ChangePasswordDTO {
    private String id;
    private String oldPassword;
    private String password;
    private String passwordConfirmation;

    public String getId() {
        return id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setId(String id) {
        this.id = id;
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
}
