package calendar.User;

/**
 * Class RegistrationDTO
 *
 * @author Axel Nilsson (axnion)
 */
class RegistrationDTO {
    String email;
    String password;
    String passwordConfiration;
    String organization;

    ErrorResponse validate() {
        ErrorResponse response = null;

        if(emailAlreadyRegistered()) {
            response = new ErrorResponse("Email address " + email + " is already registered in the system");
        }
        else if (passwordConfirmationDoesNotMatch()) {
            response = new ErrorResponse("The passwords did not match");
        }
        else if(passwordNotValid()) {
            response = new ErrorResponse("The password was not valid");
        }

        return response;

    }

    private boolean emailAlreadyRegistered() {
        UserDAO dao = new UserDAO();
        User user = dao.getUserByEmail(email);
        return user != null;
    }

    private boolean passwordConfirmationDoesNotMatch() {
        return password.equals(passwordConfiration);
    }

    private boolean passwordNotValid() {
        return password.length() <= 10 && password.length() > 255;
    }
}
