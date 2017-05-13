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
 * This class maps to /api/super_admin, and contains available routes for calling the API as an
 * authenticated user.
 *
 * The following routes are available:
 * Make Super Administrator     -   POST    -   /api/super_admin/make_super_admin
 *
 * @author Axel Nilsson (axnion)
 */
@RestController
@RequestMapping("/api/super_admin")
public class SuperAdminController {
    private UserDAO dao = new UserDAOMongo();

    /**
     * Runs on POST call to /api/super_admin/make_super_admin. Takes an ID though a UserIdDTO, and
     * if the actor user and target user fulfills some requirements then the role of the target user
     * is changed
     *
     * @param dto           UserIdDTO containing the user ID of the target user
     * @throws Exception    Database errors
     */
    // TODO: NOT TESTED!
    @RequestMapping(value = "/make_super_admin", method = RequestMethod.POST)
    public void makeSuperAdministrator(@ModelAttribute UserIdDTO dto) throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User actor = dao.getUserByEmail(email);
        User target = dao.getUserById(dto.getId());

        if(actor.canPromoteToSuperAdmin(target)) {
            dao.setRole(dto.getId(), "SUPER_ADMIN");
        }
    }
}
