package calendar.user;

import calendar.user.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.Registration;
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

    @Value("${recaptcha.url}")
    private String recaptchaUrl;
    @Value("${recaptcha.secret}")
    private String recaptchaRes;

    UserInformationValidator(UserDAO dao) {
        this.dao = dao;
    }

    /**
     * Validates the content of a RegistraitonDTO.
     *
     * @param dto           Registration to be validated to assure this is a valid registration
     * @throws Exception    Problems found with the registration
     */
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

    /**
     * Validates the content of a UserDetailsUpdateDTO to check so all updates are valid
     *
     * @param dto           UserDetailsUpdateDTO object containing new information for the user
     * @throws Exception    RPoblems with the update details
     */
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

    /**
     * Validates the change password so passwords meet the standards
     *
     * @param dto           A ChangePasswordDTO to be verified
     * @throws Exception    Problems found with the ChangePasswordDTO
     */
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

    /**
     * Validates a RecoverPasswordDTO so the details given are correct
     *
     * @param dto           RecoverPasswordDTO to be validated
     * @throws Exception    Problems found with the RecoverPasswordDTO
     */
    void validate(RecoverPasswordDTO dto) throws Exception {
        if (passwordConfirmationDoesNotMatch(dto.getPassword(), dto.getPasswordConfirmation())) {
            throw new Exception("The passwords did not match");
        }
        else if (passwordNotValid(dto.getPassword())) {
            throw new Exception("The password was not valid");
        }
    }

    /**
     * Senda a post request to the recaptcha service to validate the reCaptcha done on the client
     * side. Recives and returns a RecaptchaResponseDTO object
     *
     * @param secret        The secret sent to the server from the client
     * @return              A RecatptchaResponseDTO which has the results of the recaptcha request
     * @throws Exception    Problems contacting recaptcha
     */
    RecaptchaResponseDTO validateRecaptcha(String secret) throws Exception {
        RestTemplate rest = new RestTemplate();
        RecaptchaResponseDTO responseDTO;

        if(recaptchaRes.equals("secret_key")) {
            responseDTO = new RecaptchaResponseDTO();
            responseDTO.setSuccess(true);
        }
        else {
            try {
                responseDTO = rest.postForEntity(
                        recaptchaUrl,
                        createBody(secret, recaptchaRes),
                        RecaptchaResponseDTO.class
                ).getBody();

            }
            catch (RestClientException expt) {
                throw new Exception("Could not verify captcha");
            }
        }

        return responseDTO;
    }

    /**
     * Takes a secret and a response and creates a map pair
     * @param secret    A String containing a secret
     * @param response  A String with the response from client
     * @return
     */
    private MultiValueMap<String, String> createBody(String secret, String response) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("secret", secret);
        form.add("response", response);
        return form;
    }

    /**
     * Checks if a user with that id exists
     *
     * @param id            The id of the user we are looking for
     * @return              True if user does not exists, false it user exists
     * @throws Exception    Database errors
     */
    private boolean idDoesNotExist(String id) throws Exception {
        User user = dao.getUserById(id);
        return user == null;
    }

    /**
     * Check if the email is invalid
     *
     * @param email A String with the email address
     * @return True if email is invalid, if not then false
     */
    private boolean invalidEmailFormat(String email) {
        Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    /**
     * Check if the email is already registered to another account
     *
     * @param email         Email address we are looking around for
     * @return              True if email is already registered, false if not
     * @throws Exception    Database errors
     */
    private boolean emailAlreadyRegistered(String email) throws Exception {
        User user = dao.getUserByEmail(email);
        return user != null;
    }

    /**
     * Check if the id given match the user of the email address
     *
     * @param id            Id to compare with
     * @param email         Email to compare with
     * @return              True if the don't match, false if they do
     * @throws Exception    Database errors
     */
    private boolean emailDoesNotBelongToID(String id, String email) throws Exception {
        User user = dao.getUserById(id);
        return !email.equals(user.getEmail());
    }

    /**
     * Check if both password and passwordConfirmation match
     *
     * @param password              The password which has to match
     * @param passwordConfirmation  Password confirmation string which has to match password
     * @return                      True if they don't match, false if they do
     */
    private boolean passwordConfirmationDoesNotMatch(String password, String passwordConfirmation) {
        return !password.equals(passwordConfirmation);
    }

    /**
     * Check if the password is not valid based on length
     *
     * @param password  The password to be measured
     * @return          True if the password is too short or too long. False if it's correct
     */
    private boolean passwordNotValid(String password) {
        return password.length() < 10 || password.length() > 255;
    }

    /**
     * Check if the old password match the registered password
     *
     * @param id            id of the user with the password
     * @param oldPassword   The password given to the system by a user
     * @return              True if the password is incorrect, else it's false
     * @throws Exception    Database errors
     */
    private boolean incorrectOldPassword(String id, String oldPassword) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = dao.getUserById(id);
        return !encoder.matches(oldPassword, user.getPassword());
    }
}
