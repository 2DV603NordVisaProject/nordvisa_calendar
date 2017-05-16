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
    String getEmailAddress() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
