package calendar.event.exceptions;

public class EventNotFoundException extends RuntimeException {
    private String message;

    public EventNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
