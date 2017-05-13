package calendar.user;

import calendar.user.dto.ChangePasswordDTO;
import calendar.user.dto.RecoverPasswordDTO;
import calendar.user.dto.RegistrationDTO;
import calendar.user.dto.UserDetailsUpdateDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class UserInformationValidator
 *
 * @author Axel Nilsson (axnion)
 */
@Component
class UserInformationValidator {
    private UserDAO dao;

    UserInformationValidator(UserDAO dao) {
        this.dao = dao;
    }

    void validate(RegistrationDTO dto) throws Exception {
        if(invalidEmailFormat(dto.getEmail())) {
            throw new Exception("The email address was invalid");
        }
        else if(emailAlreadyRegistered(dto.getEmail())) {
            throw new Exception("Email address " + dto.getEmail() +
                    " is already registered in the system");
        }
        else if (passwordConfirmationDoesNotMatch(dto.getPassword(),
                dto.getPasswordConfirmation())) {
            throw new Exception("The passwords did not match");
        }
        else if(passwordNotValid(dto.getPassword())) {
            throw new Exception("The password was not valid");
        }
    }

    void validate(UserDetailsUpdateDTO dto) throws Exception {
        if(idDoesNotExist(dto.getId())) {
            throw new Exception("Invalid id");
        }
        else if (invalidEmailFormat(dto.getEmail())) {
            throw new Exception("The email address was invalid");
        }
        else if(emailAlreadyRegistered(dto.getEmail()) &&
                emailDoesNotBelongToID(dto.getId(), dto.getEmail())) {
            throw new Exception("Email is already registered to another account");
        }
    }

    void validate(ChangePasswordDTO dto) throws Exception {
        if(incorrectOldPassword(dto.getId(), dto.getOldPassword())) {
            throw new Exception("Incorrect password");
        }
        else if (passwordConfirmationDoesNotMatch(dto.getPassword(),
                dto.getPasswordConfirmation())) {
            throw new Exception("The passwords did not match");
        }
        else if(passwordNotValid(dto.getPassword())) {
            throw new Exception("The password was not valid");
        }
    }

    void validate(RecoverPasswordDTO dto) throws Exception {
        if (passwordConfirmationDoesNotMatch(dto.getPassword(), dto.getPasswordConfirmation())) {
            throw new Exception("The passwords did not match");
        }
        else if (passwordNotValid(dto.getPassword())) {
            throw new Exception("The password was not valid");
        }
    }

    private boolean idDoesNotExist(String id) throws Exception {
        User user = dao.getUserById(id);
        return user == null;
    }

    private boolean invalidEmailFormat(String email) {
        Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    private boolean emailAlreadyRegistered(String email) throws Exception {
        User user = dao.getUserByEmail(email);
        return user != null;
    }

    private boolean emailDoesNotBelongToID(String id, String email) throws Exception {
        User user = dao.getUserById(id);
        return !email.equals(user.getEmail());
    }

    private boolean passwordConfirmationDoesNotMatch(String password, String passwordConfirmation) {
        return !password.equals(passwordConfirmation);
    }

    private boolean passwordNotValid(String password) {
        return password.length() < 10 || password.length() > 255;
    }

    private boolean incorrectOldPassword(String id, String oldPassword) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = dao.getUserById(id);
        return !encoder.matches(oldPassword, user.getPassword());
    }
}
