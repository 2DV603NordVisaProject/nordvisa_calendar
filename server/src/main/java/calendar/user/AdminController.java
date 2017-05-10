package calendar.user;

import org.springframework.web.bind.annotation.*;

/**
 * Class AdminController
 *
 * @author Axel Nilsson (axnion)
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private UserDAO dao = new UserDAOMongo();

    @RequestMapping(value = "/registrations", method = RequestMethod.GET)
    @ResponseBody
    public User[] getPendingRegistrations() throws Exception {
        return dao.getPendingRegistrations();
    }

    @RequestMapping(value = "/registrations", method = RequestMethod.POST)
    public void registrationDecision(@ModelAttribute RegistrationDecisionDTO dto) throws Exception {
        if (dto.isApproved()) {
            dao.approveRegistration(dto.getId());
        } else {
            dao.deleteUser(dto.getId());
        }
    }
}
