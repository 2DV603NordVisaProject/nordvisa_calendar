package calendar.user;

import calendar.databaseConnections.MongoDBClient;
import calendar.user.dto.RegistrationDTO;
import calendar.user.dto.UserDetailsUpdateDTO;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class UserDAOMongo
 *
 * @author Axel Nilsson (axnion)
 */
class UserDAOMongo implements UserDAO {
    private Jongo client;

    //TODO: Secure from injection
    UserDAOMongo() {
        client = MongoDBClient.getClient();
    }

    public User getUserById(String id) {
        MongoCollection collection = client.getCollection("users");

        return collection.findOne(new ObjectId(id)).as(User.class);
    }

    public User getUserByEmail(String email) {
        MongoCollection collection = client.getCollection("users");

        return collection.findOne("{email: \"" + email + "\"}").as(User.class);
    }

    public ArrayList<User> getUsersByOrganization(String organizationName) {
        MongoCollection collection = client.getCollection("users");
        return cursorToArray(collection.find(
                "{organization.name: \"" + organizationName + "\"}").as(User.class)
        );
    }

    public ArrayList<User> getAllUsers() throws Exception {
        MongoCollection collection = client.getCollection("users");
        return cursorToArray(collection.find("{}").as(User.class));
    }

    public User createUser(User user) {
        MongoCollection collection = client.getCollection("users");

        user.setValidateEmailLink(new AuthenticationLink(generateRandomString(),
                DateTime.now().getMillis()));

        collection.insert(user);
        return user;
    }

    public void deleteUser(String id) {
        MongoCollection collection = client.getCollection("users");
        collection.remove(new ObjectId(id));
    }

    public void changePassword(String id, String password) {
        MongoCollection collection = client.getCollection("users");

        User user = getUserById(id);
        user.setPassword(password);

        collection.update(new ObjectId(id)).with(user);
    }

    public User setPasswordRecoveryLink(String email) {
        MongoCollection collection = client.getCollection("users");
        User user = getUserByEmail(email);

        user.setResetPasswordLink(new AuthenticationLink(generateRandomString(),
                DateTime.now().getMillis()));

        collection.update(new ObjectId(user.getId())).with(user);

        return user;
    }

    public void verifyEmailAddress(String urlId) throws Exception {
        MongoCollection collection = client.getCollection("users");
        User user = collection.findOne("{validateEmailLink.url: \"" + urlId + "\"}")
                .as(User.class);

        if (user == null) {
            throw new Exception("This link is invalid");
        }

        if(user.getValidateEmailLink().hasExpired()) {
            throw new Exception("This link has expired");
        }

        user.setValidateEmailLink(new AuthenticationLink("", 0));
        collection.update(new ObjectId(user.getId())).with(user);
    }

    public void recoverPassword(String urlId, String password) throws Exception {
        MongoCollection collection = client.getCollection("users");
        User user = collection.findOne("{resetPasswordLink.url: \"" + urlId + "\"}")
                .as(User.class);

        if (user == null) {
            throw new Exception("This link is invalid");
        }

        if(user.getResetPasswordLink().hasExpired()) {
            throw new Exception("This link has expired");
        }

        user.setResetPasswordLink(new AuthenticationLink("", 0));
        collection.update(new ObjectId(user.getId())).with(user);

        changePassword(user.getId(), password);
    }

    public void changeOrganization(String id, boolean approved) {
        User user = getUserById(id);

        if(approved) {
            user.getOrganization().setName(user.getOrganization().getChangePending());
        }

        user.getOrganization().setChangePending("");
    }

    public ArrayList<User> getPendingRegistrations() {
        String adminsOrg = "my_org"; //TODO: Add parameter email of admin and get org from database

        MongoCollection collection = client.getCollection("users");

        ArrayList<User> list = new ArrayList<>();

        list = cursorToArray(collection.find("{organization.approved: false}").as(User.class));
        list.addAll(cursorToArray(collection.find("{organization.changePending: \""
                + adminsOrg + "\"}").as(User.class)));

        return list;
    }

    public void approveRegistration(String id) {
        MongoCollection collection = client.getCollection("users");
        User user = getUserById(id);

        user.getOrganization().setApproved(true);
        collection.update(new ObjectId(id)).with(user);
    }

    public User updateUserDetails(UserDetailsUpdateDTO dto) {
        MongoCollection collection = client.getCollection("users");
        User user = collection.findOne(new ObjectId(dto.getId())).as(User.class);

        // TODO: If email is incorrect the user can't log in again and recover. :(
        if (!user.getEmail().equals(dto.getEmail())) {
            user.setEmail(dto.getEmail());
            user.setValidateEmailLink(new AuthenticationLink(generateRandomString(),
                    DateTime.now().getMillis()));
        }

        user.getOrganization().setChangePending(dto.getOrganization());

        collection.update(new ObjectId(user.getId())).with(user);

        return user;
    }

    public void setRole(String id, String role) {
        MongoCollection collection = client.getCollection("users");

        User user = getUserById(id);

    }

    private static ArrayList<User> cursorToArray(MongoCursor<User> cursor) {
        ArrayList<User> list = new ArrayList<>();

        while(cursor.hasNext()) {
            list.add(cursor.next());
        }

        return list;
    }

    private String generateRandomString() {
        String characters = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ1234567890";
        int length = 20;
        Random rnd = new Random();

        char[] text = new char[length];
        for(int i = 0; i < length; i++) {
            text[i] = characters.charAt(rnd.nextInt(characters.length()));
        }

        return new String(text);
    }
}
