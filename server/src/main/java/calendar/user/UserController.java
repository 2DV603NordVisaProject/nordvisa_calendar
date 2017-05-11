package calendar.user;

import calendar.user.dto.ChangePasswordDTO;
import calendar.user.dto.UserDetailsUpdateDTO;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class UserController
 *
 * @author Axel Nilsson (axnion)
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserDAO dao = new UserDAOMongo();
    private Email email = new Email();
    private UserInformationValidator informationValidator = new UserInformationValidator(dao);

    @RequestMapping(value = "/update_user_details", method = RequestMethod.POST)
    public void updateUserDetails(@ModelAttribute UserDetailsUpdateDTO dto) throws Exception {
        informationValidator.validate(dto);
        User user = dao.updateUserDetails(dto);
        email.sendVerificationEmail(user.getValidateEmailLink().getUrl());
    }

    @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    public void changePassword(@ModelAttribute ChangePasswordDTO dto) throws Exception {
        informationValidator.validate(dto);
        dao.changePassword(dto.getId(), dto.getPassword(), dto.getPasswordConfirmation());
    }
}
