package calendar.user;

import calendar.databaseConnections.MongoDBClient;
import calendar.user.dto.UserDetailsUpdateDTO;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class UserDAOMongo
 *
 * @author Axel Nilsson (axnion)
 */
class UserDAOMongo implements UserDAO {
    private Jongo client;

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

    public void createUser(User user) {
        MongoCollection collection = client.getCollection("users");
        collection.insert(user);
    }

    public void updateUser(UserDetailsUpdateDTO dto) {
        System.out.println("UserDAOMongo.updateUser is not implemented");
    }

    public void deleteUser(String id) {
        MongoCollection collection = client.getCollection("users");
        collection.remove(new ObjectId(id));
    }

    public void resetPassword(String password, String passwordConfirmation) {
        System.out.println("UserDAOMongo.resetPassword is not implemented");
    }

    public void verifyEmailAddress(String id) throws Exception {
        MongoCollection collection = client.getCollection("users");
        User user = collection.findOne("{validateEmailLink.url: \"" + id + "\"}").as(User.class);

        if (user == null) {
            throw new Exception("This link is invalid");
        }

        if(user.getValidateEmailLink().hasExpired()) {
            throw new Exception("This link has expired");
        }

        user.setValidateEmailLink(new AuthenticationLink("", 0));
        collection.update(new ObjectId(user.getId())).with(user);
    }

    public User[] getPendingRegistrations() {
        MongoCollection collection = client.getCollection("users");

        return cursorToArray(collection.find("{organization.approved: false}").as(User.class));
    }

    public void approveRegistration(String id) {
        MongoCollection collection = client.getCollection("users");
        User user = getUserById(id);

        user.getOrganization().setApproved(true);
        collection.update(new ObjectId(id)).with(user);
    }

    public void makeAdministrator(String id) {
        System.out.println("UserDAOMongo.makeAdministrator is not implemented");
    }

    public void removeAdministrator(String id) {
        System.out.println("UserDAOMongo.removeAdministrator is not implemented");
    }

    public void makeSuperAdministrator(String id) {
        System.out.println("UserDAOMongo.makeSuperAdministrator is not implemented");
    }

    private static User[] cursorToArray(MongoCursor<User> cursor) {
        List<User> list = new ArrayList<>();

        while(cursor.hasNext()) {
            list.add(cursor.next());
        }
        User[] arr = new User[list.size()];
        return list.toArray(arr);
    }
}
