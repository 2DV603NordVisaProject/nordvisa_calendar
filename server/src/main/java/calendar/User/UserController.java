package calendar.User;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Class UserController
 *
 * @author Axel Nilsson (axnion)
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    // TODO: Figure out how to do dependency injection into these
    private UserDAO dao = new UserDAO();
    private Email email = new Email();

    @RequestMapping("/registration")
    public ErrorResponse registration(@ModelAttribute RegistrationDTO dto) {
        ErrorResponse error = dto.validate();

        if(error == null) {
            User user = dao.createUser(dto);
            email.sendVerificationEmail(user.getValidateEmailLink());
            return null;
        }
        else {
            return error;
        }
    }
}
