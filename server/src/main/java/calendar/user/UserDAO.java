package calendar.user;

import java.util.ArrayList;

/**
 * Interface UserDAO
 *
 * @author Axel Nilsson (axnion)
 */
interface UserDAO {
    User getUserById(String id) throws Exception;
    User getUserByEmail(String email) throws Exception;
    User getUserByPasswordRecoveryLink(String urlId) throws Exception;
    User getUserByEmailVerificationLink(String urlId) throws Exception;
    ArrayList<User> getUsersByOrganization(String organizationName) throws Exception;
    ArrayList<User> getAllUsers() throws Exception;

    ArrayList<User> getPendingRegistrations(String orgazniation) throws Exception;

    void add(User user) throws Exception;
    void delete(String id) throws Exception;
    void update(User user) throws Exception;
}
