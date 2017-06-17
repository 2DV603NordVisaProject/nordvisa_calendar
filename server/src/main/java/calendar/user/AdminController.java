package calendar.user;

import calendar.user.dto.RegistrationDecisionDTO;
import calendar.user.dto.UserDTO;
import calendar.user.dto.UserIdDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    private UserDAO dao;
    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private Email email;

    /**
     * This method take a user ID in a UserIdDTO and if conditions are meet then demotes the target
     * User to role USER
     *
     * @param dto           UserIdDTO containing the user ID of the target user
     * @throws Exception    Unauthorized or database errors
     */
    @RequestMapping(value = "/make_user", method = RequestMethod.POST)
    public void makeUser(@ModelAttribute UserIdDTO dto) throws Exception {
        User actor = dao.getUserByEmail(currentUser.getEmailAddress());
        User target = dao.getUserById(dto.getId());

        if(actor.canChangeRoleTo(target, "USER")) {
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
    @RequestMapping(value = "/make_admin", method = RequestMethod.POST)
    public void makeAdmin(@ModelAttribute UserIdDTO dto) throws Exception {
        User actor = dao.getUserByEmail(currentUser.getEmailAddress());
        User target = dao.getUserById(dto.getId());

        if(actor.canChangeRoleTo(target, "ADMIN")) {
            target.setRole("ADMIN");
            dao.update(target);
        }
    }

    /**
     * Fetches all pending registrations and pending organization registration and returns them
     * to the client.
     *
     * @return              ArrayList if User objects of which are waiting to be approved or denied
     */
    @RequestMapping(value = "/registrations", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<UserDTO> getPendingRegistrations() {
        String organization = dao.getUserByEmail(currentUser.getEmailAddress())
                .getOrganization().getName();
        ArrayList<UserDTO> dto = new ArrayList<>();

        for (User user : dao.getPendingRegistrations(organization)) {
            dto.add(new UserDTO(user));
        }


        return dto;
    }

    /**
     * Takes the id of the target user and a boolean value if the registration was approved or not
     * in a RegistrationDecisionDTO. The method checks if it's a registration or orgnization change.
     * These are treated a bit differently. A registation calls approveRegistration if the
     * registration was approved, and deletes the user if the registration was denied.
     *
     * @param dto           Id and approved boolean in an object
     */
    @RequestMapping(value = "/registrations", method = RequestMethod.POST)
    public void registrationDecision(@ModelAttribute RegistrationDecisionDTO dto) {
        User user = dao.getUserById(dto.getId());

        if(dto.isApproved()) {
            boolean isRegistered = user.getOrganization().isApproved();

            user.getOrganization().setApproved(true);
            user.getOrganization().setName(user.getOrganization().getChangePending());
            user.getOrganization().setChangePending("_");
            dao.update(user);

            if(isRegistered) {
                email.sendSuccessEmail(user.getEmail(), "organization change");
            } else {
                email.sendSuccessEmail(user.getEmail(), "registration");
            }
        }
        else {
            if(user.getOrganization().isApproved()) {
                user.getOrganization().setChangePending("_");
                dao.update(user);
                email.sendDenialEmail(user.getEmail(), "organization change");
            }
            else {
                dao.delete(dto.getId());
                email.sendDenialEmail(user.getEmail(), "registration");
            }
        }
    }

    /**
     * Returns all users who the currently logged in user can manage. For example if they can edit
     * events created by the user.
     *
     * @return              An ArrayList containing UserDTOs of all user the current user can manage
     */
    @RequestMapping(value = "/manageableUsers", method = RequestMethod.GET)
    public ArrayList<UserDTO> getManageableUsers() {
        String email = currentUser.getEmailAddress();
        User admin = dao.getUserByEmail(email);

        ArrayList<User> allUsers = dao.getAllUsers();
        ArrayList<UserDTO> manageableUsers = new ArrayList<>();

        for (User user : allUsers) {
            if(admin.canManage(user)) {
                manageableUsers.add(new UserDTO(user));
            }
        }

        return manageableUsers;
    }
}
