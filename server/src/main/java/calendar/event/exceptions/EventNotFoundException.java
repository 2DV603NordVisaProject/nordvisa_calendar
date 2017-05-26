package calendar.event.exceptions;

/**
 * Class EventNotFoundException
 *
 * This class creates a custom exception that is thrown when no events
 * could be found.
 *
 * @author Leif Karlsson (leifkarlsson)
 */
public class EventNotFoundException extends RuntimeException {
    private String message;

    public EventNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
