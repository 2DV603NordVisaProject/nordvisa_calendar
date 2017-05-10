package calendar.user;

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
    public void updateUser(@ModelAttribute UserUpdateDTO dto) throws Exception {
        informationValidator.validate(dto);
        dao.updateUser(dto);
    }
}
