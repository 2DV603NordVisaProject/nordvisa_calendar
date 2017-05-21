package calendar.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Class CurrentUser
 *
 * Exist for making mocking of getting the currently logged in used possible.
 *
 * @author Axel Nilsson (axnion)
 */
@Component
class CurrentUser {

    /**
     * Return the email addres of the currently logged in user
     * @return email of the current user
     */
    String getEmailAddress() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
