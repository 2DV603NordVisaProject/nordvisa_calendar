package calendar.user;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Class AuthorizationChecker
 *
 * A facade object for other packages to easily see if the current user can manage parts of another
 * user which is found by their id.
 *
 * @author Axel Nilsson (axnion)
 */
public class AuthorizationChecker {
    @Autowired
    private UserDAO dao;

    /**
     * A facade method which helps other packages check if the current logged in user can manage
     * another users account.
     *
     * @param id    The id of the target user
     * @return      True if the current user can manage the other user. False if not
     */
    public boolean currentUserCanManage(String id) {
        CurrentUser currentUser = new CurrentUser();
        String email = currentUser.getEmailAddress();
        return dao.getUserByEmail(email).canManage(dao.getUserById(id));
    }

    public List<String> getAllUserIds() {
        ArrayList<User> users;

        try {
            users = dao.getAllUsers();
        } catch (Exception expt) {
            System.err.println("Could not retrieve users.");
            expt.printStackTrace();
            return null;
        }

        List<String> ids = new ArrayList<>();

        for(User user : users) {
            ids.add(user.getId());
        }

        return ids;
    }
}
