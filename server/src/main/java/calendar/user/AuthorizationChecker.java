package calendar.user;

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
}
