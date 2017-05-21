package calendar.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface UserDAO
 *
 * @author Axel Nilsson (axnion)
 */
public interface UserDAO {
    User getUserById(String id);
    User getUserByEmail(String email);
    User getUserByPasswordRecoveryLink(String urlId) throws Exception;
    User getUserByEmailVerificationLink(String urlId) throws Exception;
    ArrayList<User> getUsersByOrganization(String organizationName) throws Exception;
    ArrayList<User> getAllUsers() throws Exception;
    List<String> getOrganizations() throws Exception;     // TODO: ADD TO DOCS AND DIAGRAMS

    ArrayList<User> getPendingRegistrations(String orgazniation) throws Exception;

    void add(User user) throws Exception;
    void delete(String id) throws Exception;
    void update(User user) throws Exception;
}
