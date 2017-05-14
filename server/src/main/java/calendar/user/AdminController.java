package calendar.user;

import calendar.user.dto.RegistrationDecisionDTO;
import calendar.user.dto.UserIdDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    @Autowired
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
        User actor = dao.getUserByEmail(new CurrentUser().getEmailAddres());
        User target = dao.getUserById(dto.getId());

        if(actor.canDemote(target)) {
            target.setRole("USER");
            dao.update(target);
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
        User actor = dao.getUserByEmail(new CurrentUser().getEmailAddres());
        User target = dao.getUserById(dto.getId());

        if(actor.canPromoteToAdmin(target)) {
            target.setRole("ADMIN");
            dao.update(target);
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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String organization = dao.getUserByEmail(email).getOrganization().getName();

        return dao.getPendingRegistrations(organization);
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

        if(user.getOrganization().getChangePending().equals("")) {
            if (dto.isApproved()) {
                user.getOrganization().setApproved(true);
                dao.update(user);
            }
            else
                dao.delete(dto.getId());
        }
        else {
            if(dto.isApproved()) {
                user.getOrganization().setName(user.getOrganization().getChangePending());
            }

            user.getOrganization().setChangePending("");
            user.getOrganization().setApproved(true);

            dao.update(user);
        }
    }
}
