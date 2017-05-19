package calendar.event.exceptions;

public class MissingTokenException extends RuntimeException {
    private String message;

    public MissingTokenException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
