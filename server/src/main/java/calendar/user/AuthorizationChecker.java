package calendar.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
public class AuthorizationChecker {
    @Autowired
    private CurrentUser currentUser;
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
        String email = currentUser.getEmailAddress();
        User user = dao.getUserByEmail(email);
        User target = dao.getUserById(id);

        return user != null && target != null && user.canManage(target);
    }

    public List<String> getAllUserIds() {
        ArrayList<User> users;
        List<String> ids = new ArrayList<>();

        users = dao.getAllUsers();

        for(User user : users) {
            ids.add(user.getId());
        }

        return ids;
    }
}
