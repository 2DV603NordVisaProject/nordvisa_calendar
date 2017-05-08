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
    @RequestMapping("/registration")
    public void registration(@ModelAttribute RegistrationDTO dto) {
        // TODO: Validation
    }
}
