package calendar.event.exceptions;

/**
 * Class Error
 *
 * @author Leif Karlsson (leifkarlsson)
 */
public class Error {
    private int status;
    private String message;

    public Error(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
