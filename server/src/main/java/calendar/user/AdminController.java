package calendar.user;

import calendar.user.dto.RegistrationDecisionDTO;
import calendar.user.dto.UserIdDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Class AdminController
 *
 * This class maps to /api/admin, and contains all available routes for calling the API as an
 * authenticated administrator.
 *
 * The following routes are available:
 * Make User                    -   POST    -   /api/admin/make_user
 * Make Administrator           -   POST    -   /api/admin/make_admin
 * Get pending registrations    -   GET     -   /api/admin/registrations
 * Accept/Deny Registration     -   POST    -   /api/admin/registrations
 *
 * @author Axel Nilsson (axnion)
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private UserDAO dao = new UserDAOMongo();

    /**
     * This method take a user ID in a UserIdDTO and if conditions are meet then demotes the target
     * User to role USER
     *
     * @param dto           UserIdDTO containing the user ID of the target user
     * @throws Exception    Unauthorized or database errors
     */
    // TODO: NOT TESTED!
    @RequestMapping(value = "/make_user", method = RequestMethod.POST)
    public void makeUser(@ModelAttribute UserIdDTO dto) throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User actor = dao.getUserByEmail(email);
        User target = dao.getUserById(dto.getId());

        if(actor.canDemote(target)) {
            dao.setRole(dto.getId(), "USER");
        }
    }

    /**
     * Takes a user ID in a UserIdDTO and if conditions are meet then promotes the target user to
     * the role ADMIN
     *
     * @param dto           UserIdDTO containing the user ID of the target user
     * @throws Exception    Unauthorized or database errors
     */
    // TODO: NOT TESTED!
    @RequestMapping(value = "/make_admin", method = RequestMethod.POST)
    public void makeAdmin(@ModelAttribute UserIdDTO dto) throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User actor = dao.getUserByEmail(email);
        User target = dao.getUserById(dto.getId());

        if(actor.canPromoteToAdmin(target)) {
            dao.setRole(dto.getId(), "ADMIN");
        }
    }

    /**
     * Fetches all pending registrations and pending organization registration and returns them
     * to the client.
     *
     * @return              ArrayList if User objects of which are waiting to be approved or denied
     * @throws Exception    Database errors
     */
    @RequestMapping(value = "/registrations", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<User> getPendingRegistrations() throws Exception {
        return dao.getPendingRegistrations();
    }

    /**
     * Takes the id of the target user and a boolean value if the registration was approved or not
     * in a RegistrationDecisionDTO. The method checks if it's a registration or orgnization change.
     * These are treated a bit differently. A registation calls approveRegistration if the
     * registration was approved, and deletes the user if the registration was denied.
     *
     * @param dto           Id and approved boolean in an object
     * @throws Exception    Database errors.
     */
    @RequestMapping(value = "/registrations", method = RequestMethod.POST)
    public void registrationDecision(@ModelAttribute RegistrationDecisionDTO dto) throws Exception {
        User user = dao.getUserById(dto.getId());

        // TODO: Double check this logic!
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
