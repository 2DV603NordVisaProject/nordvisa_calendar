package calendar.user;

import calendar.user.dto.UserIdDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class SuperAdminController
 *
 * @author Axel Nilsson (axnion)
 */
@RestController
@RequestMapping("/api/super_admin")
public class SuperAdminController {
    private UserDAO dao = new UserDAOMongo();

    // TODO: NOT TESTED!
    @RequestMapping(value = "/make_super_admin", method = RequestMethod.POST)
    public void makeSuperAdministrator(@ModelAttribute UserIdDTO dto) throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User actor = dao.getUserByEmail("test@test.com"); //TODO: Change argument to email
        User target = dao.getUserById(dto.getId());

        if(actor.getRole().equals("SUPER_ADMIN") && !target.getRole().equals("SUPER_ADMIN")) {
            dao.setRole(dto.getId(), "SUPER_ADMIN");
        }
    }
}
