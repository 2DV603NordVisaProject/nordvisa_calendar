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
public class VisitorController {
    // TODO: Figure out how to do dependency injection into these
    private UserDAO dao = new UserDAO();
    private Email email = new Email();

    @RequestMapping("/registration")
    public void registration(@ModelAttribute RegistrationDTO dto) throws Exception {
        dto.validate();
        User user = dao.createUser(dto);
        email.sendVerificationEmail(user.getValidateEmailLink());
    }
}
