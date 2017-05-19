package calendar.user;

/**
 * Class AuthorizationChecker
 *
 * @author Axel Nilsson (axnion)
 */
// TODO: Add to class diagram
public class AuthorizationChecker {
    public boolean currentUserCanManage(String id) {
        CurrentUser currentUser = new CurrentUser();
        String email = currentUser.getEmailAddress();
        UserDAO dao = new UserDAOMongo();
        return dao.getUserByEmail(email).canManage(dao.getUserById(id));
    }
}
