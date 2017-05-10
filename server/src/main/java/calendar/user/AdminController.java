package calendar.user;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Class AdminController
 *
 * @author Axel Nilsson (axnion)
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private UserDAO dao = new UserDAO();

    @RequestMapping(value = "/registrations", method = RequestMethod.GET)
    @ResponseBody
    public User[] getPendingRegistrations() {
        return dao.getPendingRegistrations();
    }

    @RequestMapping(value = "/registrations", method = RequestMethod.POST)
    public void registrationDecision(@ModelAttribute RegistrationDecisionDTO dto) {

    }
}
