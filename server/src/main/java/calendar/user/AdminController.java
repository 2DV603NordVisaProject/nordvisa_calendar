package calendar.user;

import calendar.user.dto.RegistrationDecisionDTO;
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
    private UserDAO dao = new UserDAOMongo();

    @RequestMapping(value = "/registrations", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<User> getPendingRegistrations() throws Exception {
        return dao.getPendingRegistrations();
    }

    @RequestMapping(value = "/registrations", method = RequestMethod.POST)
    public void registrationDecision(@ModelAttribute RegistrationDecisionDTO dto) throws Exception {
        User user = dao.getUserById(dto.getId());

        if(user.getOrganization().getChangePending().equals("")) {
            if (dto.isApproved())
                dao.approveRegistration(dto.getId());
            else
                dao.deleteUser(dto.getId());
        }
        else {
            dao.changeOrganization(dto.getId(), dto.isApproved());
        }
    }
}
