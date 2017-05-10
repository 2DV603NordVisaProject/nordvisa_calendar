package calendar.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class AdminController
 *
 * @author Axel Nilsson (axnion)
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @RequestMapping(value = "/registrations", method = RequestMethod.GET)
    public void getPendingRegistrations() {

    }

    @RequestMapping(value = "/registrations", method = RequestMethod.POST)
    public void registrationDecision() {

    }
}
