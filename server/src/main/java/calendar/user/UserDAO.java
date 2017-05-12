package calendar.user;

import calendar.user.dto.ChangePasswordDTO;
import calendar.user.dto.RegistrationDTO;
import calendar.user.dto.UserDetailsUpdateDTO;

import java.util.ArrayList;

/**
 * Interface UserDAO
 *
 * @author Axel Nilsson (axnion)
 */
interface UserDAO {
    User getUserById(String id) throws Exception;
    User getUserByEmail(String email) throws Exception;
    ArrayList<User> getUsersByOrganization(String organizationName) throws Exception;
    ArrayList<User> getAllUsers() throws Exception;

    User createUser(RegistrationDTO dto) throws Exception;
    void deleteUser(String id) throws Exception;

    User updateUserDetails(UserDetailsUpdateDTO dto) throws Exception;
    void changePassword(String id, String password) throws Exception;

    User setPasswordRecoveryLink(String email) throws Exception;

    void verifyEmailAddress(String urlId) throws Exception;
    void recoverPassword(String url, String password) throws Exception;

    ArrayList<User> getPendingRegistrations() throws Exception;
    void approveRegistration(String id) throws Exception;
    void changeOrganization(String id, boolean approved) throws Exception;

    void setRole(String id, String role) throws Exception;
}
