package calendar.user;

import calendar.user.dto.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class UserController
 *
 * This class maps to /api/user, and contains all available routes for calling the API as an
 * authenticated user.
 *
 * The following routes are available:
 * Get role of the current user -   GET     -    /api/user/role
 * Get a User by ID             -   GET     -    /api/user?id=""
 * Get a User by Email          -   GET     -    /api/user?email=""
 * Get Users by Organization    -   GET     -    /api/user?organization=""
 * Get all Users                -   GET     -    /api/user
 * Unregister User              -   POST    -    /api/user/unregister
 * Update User details          -   POST    -    /api/user/update_user_details
 * Change password              -   POST    -    /api/user/change_password
 *
 * @author Axel Nilsson (axnion)
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserDAO dao;
    @Autowired
    private Email email;
    @Autowired
    private UserInformationValidator validator;
    @Autowired
    private CurrentUser currentUser;

    // TODO: Update diagrams with this
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public UserDTO getCurrentUser() throws Exception {
        String email = new CurrentUser().getEmailAddress();
        User user = dao.getUserByEmail(email);
        return new UserDTO(user);
    }

    /**
     * Runs on GET call to /api/user?id="". Takes the id provided and fetches a matching user using
     * the DAO. If no suitable user is found then null is returned.
     *
     * @param id            The ID of the user to be returned
     * @return              User object serialized to JSON format
     * @throws Exception    Database errors
     */
    @RequestMapping(value = "", params = "id", method = RequestMethod.GET)
    public UserDTO getUserById(@RequestParam("id") String id) throws Exception {
        return new UserDTO(dao.getUserById(id));
    }

    /**
     * Runs on GET call to /api/user/email="". Takes the email address provided and fetches a
     * matching user using the DAO and returns it. If no user is found null is returned.
     *
     * @param email         Email of the user to be returned
     * @return              User object serialized to JSON format
     * @throws Exception    Database errors
     */
    @RequestMapping(value = "", params = "email", method = RequestMethod.GET)
    public UserDTO getUserByEmail(@RequestParam("email") String email) throws Exception {
        return new UserDTO(dao.getUserByEmail(email));
    }

    /**
     * Runs on GET call to /api/user/organization="". Takes the organization name and fetches all
     * users which are members of this organization in an ArrayList.
     *
     * @param organization  The name of the organization which users should be returned
     * @return              ArrayList of User objects serialized to JSON format
     * @throws Exception    Database errors
     */
    @RequestMapping(value = "", params = "organization", method = RequestMethod.GET)
    public ArrayList<UserDTO> getUsersByOrganization(
            @RequestParam("organization") String organization) throws Exception {
        ArrayList<UserDTO> dto = new ArrayList<>();

        for (User user : dao.getUsersByOrganization(organization)) {
            dto.add(new UserDTO(user));
        }

        return dto;
    }

    /**
     * Runs on GET call to /api/user. It will return all users in the whole system.
     * @return              ArrayList of User objects serialized to JSON format
     * @throws Exception    Database errors
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ArrayList<UserDTO> getAllUsers() throws Exception {
        ArrayList<UserDTO> dto = new ArrayList<>();

        for (User user : dao.getAllUsers()) {
            dto.add(new UserDTO(user));
        }

        return dto;
    }

    /**
     * Runs on POST call to /api/user/unregister. Takes an id though a UserIdDTO object and tries to
     * delete a user with a matching id. But to delete a user one of the following criterias has to
     * be fulfilled.
     * Actor and target is the same user
     * Actor is a system wide administrator or super administrator
     * Actor is an administrator in the same organization as target
     *
     * @param dto           A UserIdDTO object used to parse form data containing only an ID
     * @throws Exception    Attempt at unauthorized or incorrect deletion of user
     */
    @RequestMapping(value = "/unregister", method = RequestMethod.POST)
    public void unregister(@ModelAttribute UserIdDTO dto) throws Exception {
        User actor = dao.getUserByEmail(currentUser.getEmailAddress());
        User target = dao.getUserById(dto.getId());

        if(target == null) {
            throw new Exception("The user you are trying to unregister does not exist");
        }
        else if(actor.canManage(target)) {
            dao.delete(dto.getId());
        }
        else {
            throw new Exception("You are not authorized to unregister this account");
        }
    }

    /**
     * Runs on POST call to /api/user/update_user_details. Takes details in a UserDetailsUpdateDTO
     * which is validated using the UserInformationValidator. Once validated the database is updated
     * and a email verification email is sent to the new email address.
     *
     * @param dto           Object containing user details to be updated
     * @throws Exception    Invalid data or database errors
     */
    @RequestMapping(value = "/update_user_details", method = RequestMethod.POST)
    public void updateUserDetails(@ModelAttribute UserDetailsUpdateDTO dto) throws Exception {
        validator.validate(dto);

        User user = dao.getUserById(dto.getId());

        if (!user.getEmail().equals(dto.getEmail())) {
            user.setEmail(dto.getEmail());
            user.setValidateEmailLink(new AuthenticationLink(generateRandomString(),
                    DateTime.now().getMillis()));

            email.sendVerificationEmail(user.getValidateEmailLink().getUrl(), user.getEmail());
        }

        user.getOrganization().setChangePending(dto.getOrganization());

        dao.update(user);
    }

    /**
     * Runs on POST call to /api/user/change_password. Takes the data in a ChangePasswordDTo which
     * contains the old password and new password with a confirmation. The data is validated and
     * then te DAO is called to update the database.
     *
     * @param dto           Object containing the old and new password with confirmation
     * @throws Exception    Invalid data or database errors
     */
    @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    public void changePassword(@ModelAttribute ChangePasswordDTO dto) throws Exception {
        validator.validate(dto);

        User user = dao.getUserById(dto.getId());
        user.setPassword(dto.getPassword());

        dao.update(user);
    }

    private String generateRandomString() {
        String characters = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ1234567890";
        int length = 20;
        Random rnd = new Random();

        char[] text = new char[length];
        for(int i = 0; i < length; i++) {
            text[i] = characters.charAt(rnd.nextInt(characters.length()));
        }

        return new String(text);
    }
}
