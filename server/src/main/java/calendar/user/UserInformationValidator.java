package calendar.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class UserInformationValidator
 *
 * @author Axel Nilsson (axnion)
 */
class UserInformationValidator {
    private UserDAO dao;

    UserInformationValidator(UserDAO dao) {
        this.dao = dao;
    }

    void validate(UserUpdateDTO dto) throws Exception {
        if(matchOldPassword(dto.getEmail(), dto.getOldPassword())) {
            throw new Exception("Incorrect password");
        }
        else if (passwordConfirmationDoesNotMatch(dto.getPassword(), dto.getPasswordConfirmation())) {
            throw new Exception("The passwords did not match");
        }
        else if(passwordNotValid(dto.getPassword())) {
            throw new Exception("The password was not valid");
        }
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

    private boolean invalidEmailFormat(String email) {
        Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    private boolean emailAlreadyRegistered(String email) throws Exception {
        User user = dao.getUserByEmail(email);
        return user != null;
    }

    private boolean passwordConfirmationDoesNotMatch(String password, String passwordConfirmation) {
        return !password.equals(passwordConfirmation);
    }

    private boolean passwordNotValid(String password) {
        return password.length() < 10 || password.length() > 255;
    }

    private boolean matchOldPassword(String email, String oldPassword) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = dao.getUserByEmail(email);
        return encoder.matches(oldPassword, user.getPassword());
    }
}
