package calendar.event.exceptions;

public class Error {
    private String message;

    public Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
