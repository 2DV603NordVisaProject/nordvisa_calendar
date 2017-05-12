package calendar.user;

import calendar.user.dto.RegistrationDecisionDTO;
import calendar.user.dto.UserIdDTO;
import org.springframework.security.core.context.SecurityContextHolder;
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

    // TODO: NOT TESTED!
    @RequestMapping(value = "/make_user", method = RequestMethod.POST)
    public void makeUser(@ModelAttribute UserIdDTO dto) throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User actor = dao.getUserByEmail("test@test.com"); //TODO: Change argument to email
        User target = dao.getUserById(dto.getId());

        if(target.getRole().equals("ADMIN")) {
            if(actor.getRole().equals("SUPER_ADMIN")) {
                dao.setRole(dto.getId(), "USER");
            }
            else if(actor.getOrganization().getName().equals(target.getOrganization().getName()) &&
                    !actor.getOrganization().getName().equals("")) {
                dao.setRole(dto.getId(), "USER");
            }
        }

    }

    // TODO: NOT TESTED!
    @RequestMapping(value = "/make_admin", method = RequestMethod.POST)
    public void makeAdmin(@ModelAttribute UserIdDTO dto) throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User actor = dao.getUserByEmail("test@test.com"); //TODO: Change argument to email
        User target = dao.getUserById(dto.getId());

        if(target.getRole().equals("USER")) {
            if(target.getOrganization().getName().equals(actor.getOrganization().getName())) {
                dao.setRole(dto.getId(), "ADMIN");
            }
        }
    }

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
