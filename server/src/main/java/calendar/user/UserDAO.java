package calendar.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface UserDAO
 *
 * The interface between the application and the database driver. Any database driver should
 * implement this interface
 *
 * @author Axel Nilsson (axnion)
 */
public interface UserDAO {
    User getUserById(String id);
    User getUserByEmail(String email);
    User getUserByPasswordRecoveryLink(String urlId);
    User getUserByEmailVerificationLink(String urlId);
    ArrayList<User> getUsersByOrganization(String organizationName);
    ArrayList<User> getAllUsers();
    List<String> getOrganizations();

    ArrayList<User> getPendingRegistrations(String orgazniation);

    void add(User user);
    void delete(String id);
    void update(User user);
}
