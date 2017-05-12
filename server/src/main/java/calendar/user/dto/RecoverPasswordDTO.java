package calendar.user.dto;

/**
 * Class RecoverPasswordDTO
 *
 * @author Axel Nilsson (axnion)
 */
public class RecoverPasswordDTO {
    private String urlId;
    private String password;
    private String passwordConfirmation;

    public String getUrlId() {
        return urlId;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
