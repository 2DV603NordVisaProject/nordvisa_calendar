package calendar.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Class CurrentUser
 *
 * Exist for making mock of getting the currently logged in used possible.
 *
 * @author Axel Nilsson (axnion)
 */
@Component
public class CurrentUser {

    /**
     * Return the email addres of the currently logged in user
     * @return email of the current user
     */
    public String getEmailAddress() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
