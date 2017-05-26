package calendar.event.exceptions;

/**
 * Class MissingTokenException
 *
 * This class creates a custom exception that is thrown when no token
 * was supplied by the client.
 *
 * @author Leif Karlsson (leifkarlsson)
 */
public class MissingTokenException extends RuntimeException {
    private String message;

    public MissingTokenException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
