package calendar.user;

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

    /**
     * A facade method which helps other pacakges check if the current logged in user can manage
     * another users account.
     *
     * @param id    The id of the target user
     * @return      True if the current user can manage the other user. False if not
     */
    public boolean currentUserCanManage(String id) {
        CurrentUser currentUser = new CurrentUser();
        String email = currentUser.getEmailAddress();
        UserDAO dao = new UserDAOMongo();
        return dao.getUserByEmail(email).canManage(dao.getUserById(id));
    }

    // TODO: add to docs and diagrams
    public List<String> getAllUserIds() {
        UserDAO dao = new UserDAOMongo();
        ArrayList<User> users = new ArrayList<>();

        try {
            users = dao.getAllUsers();
        } catch (Exception expt) {
            return null;
        }

        List<String> ids = new ArrayList<>();

        for(User user : users) {
            ids.add(user.getId());
        }

        return ids;
    }
}
