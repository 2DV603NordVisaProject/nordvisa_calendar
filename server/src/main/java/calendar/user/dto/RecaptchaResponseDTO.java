package calendar.user.dto;

/**
 * Class RecaptchaResponseDTO
 *
 * @author Axel Nilsson (axnion)
 */
public class RecaptchaResponseDTO {
    private boolean success;
    private String[] errorCodes;

    public boolean isSuccess() {
        return success;
    }

    public String[] getErrorCodes() {
        return errorCodes;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setErrorCodes(String[] errorCodes) {
        this.errorCodes = errorCodes;
    }
}
