package calendar.user.exceptions;

/**
 * Class UserDoesNotExistException
 *
 * @author Axel Nilsson (axnion)
 */
public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
