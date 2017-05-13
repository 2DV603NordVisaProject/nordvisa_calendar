package calendar.user;

import calendar.user.dto.PasswordRecoveryRequestDTO;
import calendar.user.dto.RecoverPasswordDTO;
import calendar.user.dto.RegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Class UserController
 *
 * This class maps to /api/visitor, and contains all available routes for calling the API without
 * having to authenticate.
 *
 * With it you can do the following:
 * Register new account         -   POST    -   /api/visitor/registration
 * Request password recovery    -   POST    -   /api/visitor/request_password_recovery
 * Recover password             -   POST    -   /api/visitor/recover_password
 * Verify email address         -   GET     -   /api/visitor/verify_email
 *
 * @author Axel Nilsson (axnion)
 */
@RestController
@RequestMapping("/api/visitor")
public class VisitorController {
    private UserDAO dao;
    private Email email;
    private UserInformationValidator informationValidator;

    @Autowired
    public VisitorController(UserDAOMongo dao, Email email, UserInformationValidator validator) {
        this.dao = dao;
        this.email = email;
        this.informationValidator = validator;
    }

    /**
     * Runs on POST call to /api/visitor/registration. It registers a user account to the database.
     * If there is no other registered user the first person to register becomes a super
     * administrator.
     *
     * @param dto           Object containing data sent to server from client
     * @throws Exception    Invalid registration data, or database errors
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public void registration(@ModelAttribute RegistrationDTO dto) throws Exception {
        informationValidator.validate(dto);
        ArrayList<User> existingUsers = dao.getAllUsers();
        User user = new User(dto);

        if(existingUsers.size() == 0) {
            user.setRole("SUPER_ADMIN");
        }

        dao.createUser(user);

        email.sendVerificationEmail(user.getValidateEmailLink().getUrl());
    }

    /**
     * Runs on POST call to /api/visitor/request_password_recovery. Takes an email address though
     * the PasswordRecoveryRequestDTO and tells dao to update the database with a password recovery
     * link, and then calls sendPasswordResetEmail on Email object.
     *
     * @param dto           Object containing data sent to server from client
     * @throws Exception    Invalid data, database errors
     */
    @RequestMapping(value = "/request_password_recovery", method = RequestMethod.POST)
    public void requestPasswordRecovery(@ModelAttribute PasswordRecoveryRequestDTO dto)
            throws Exception {
        User user = dao.setPasswordRecoveryLink(dto.getEmail());
        email.sendPasswordResetEmail(user.getResetPasswordLink().getUrl());
    }

    /**
     * Runs on POST call to /api/visitor/request_password_recovery. Takes new password with
     * confirmation, and also the urlId to find the correct user to update. Calls recoverPassword on
     * DAO.
     *
     * @param dto           Object containing data sent to server from client
     * @throws Exception    Invalid data, database errors
     */
    @RequestMapping(value = "/recover_password", method = RequestMethod.POST)
    public void requestPasswordRecovery(@ModelAttribute RecoverPasswordDTO dto) throws Exception {
        informationValidator.validate(dto);
        dao.recoverPassword(dto.getUrlId(), dto.getPassword());
    }

    /**
     * Runs on GET call to /api/visitor/verify_email.
     *
     * @param response      The object used to send the response
     * @param urlId         The query string param "id"
     * @return              A String, if something goes wrong the message is sent to the client.
     * @throws Exception    IOExceptions from HTTPServletResponse.
     */
    @RequestMapping(value = "/verify_email", method = RequestMethod.GET)
    public String verifyEmailAddress(HttpServletResponse response, @RequestParam("id") String urlId)
            throws Exception {
        try {
            dao.verifyEmailAddress(urlId);
        }
        catch (Exception expt) {
            return expt.getMessage();
        }
        response.sendRedirect("/");
        return "";
    }
}
