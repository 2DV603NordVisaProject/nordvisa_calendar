package calendar.user;

import calendar.user.dto.UserDetailsUpdateDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private UserInformationValidator informationValidator = new UserInformationValidator(dao);

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateUser(@ModelAttribute UserDetailsUpdateDTO dto) throws Exception {
        informationValidator.validate(dto);
        dao.updateUser(dto);
    }
}
