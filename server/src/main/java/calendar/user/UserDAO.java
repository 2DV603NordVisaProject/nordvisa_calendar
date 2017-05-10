package calendar.user;

/**
 * Interface UserDAO
 *
 * @author Axel Nilsson (axnion)
 */
interface UserDAO {
    User getUserById(String id) throws Exception;
    User getUserByEmail(String email) throws Exception;
    void createUser(User user) throws Exception;
    void updateUser(UserUpdateDTO dto) throws Exception;
    void deleteUser(String id) throws Exception;

    void resetPassword(String password, String passwordConfirmation) throws Exception;
    void verifyEmailAddress(String urlId) throws Exception;

    User[] getPendingRegistrations() throws Exception;
    void approveRegistration(String id) throws Exception;

    void makeAdministrator(String id) throws Exception;
    void removeAdministrator(String id) throws Exception;
    void makeSuperAdministrator(String id) throws Exception;
}
