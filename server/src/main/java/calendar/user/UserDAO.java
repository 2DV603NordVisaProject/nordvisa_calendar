package calendar.user;

import calendar.databaseConnections.MongoDBClient;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class UserDAO
 *
 * @author Axel Nilsson (axnion)
 */
class UserDAO {
    // TODO: Create Interface for called UserDAO and change this to UserDAOMongo

    private Jongo client;

    UserDAO() {
        client = MongoDBClient.getClient();
    }

    User createUser(RegistrationDTO dto) {
        MongoCollection collection = client.getCollection("users");
        User user = new User(dto);
        collection.insert(user);
        return user;
    }

    void removeUser(String id) {
        MongoCollection collection = client.getCollection("users");
        collection.remove(new ObjectId(id));
    }

    User getUserById(String id) {
        MongoCollection collection = client.getCollection("users");

        return collection.findOne(new ObjectId(id)).as(User.class);
    }

    User getUserByEmail(String email) {
        MongoCollection collection = client.getCollection("users");

        return collection.findOne("{email: \"" + email + "\"}").as(User.class);
    }

    void verifyEmailAddress(String id) throws Exception {
        MongoCollection collection = client.getCollection("users");
        User user = collection.findOne("{validateEmailLink.url: \"" + id + "\"}").as(User.class);

        if (user == null) {
            throw new Exception("This link is invalid");
        }

        if(user.getValidateEmailLink().hasExpired()) {
            throw new Exception("This link has expired");
        }

        user.setValidateEmailLink(new AuthenticationLink("", DateTime.now().getMillis()));
        collection.update(new ObjectId(user.getId())).with(user);
    }

    User[] getPendingRegistrations() {
        MongoCollection collection = client.getCollection("users");

        return cursorToArray(collection.find("{organization.approved: false}").as(User.class));
    }

    void approveRegistration(String id) {
        MongoCollection collection = client.getCollection("users");
        User user = getUserById(id);

        user.getOrganization().setApproved(true);
        collection.update(new ObjectId(id)).with(user);
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
