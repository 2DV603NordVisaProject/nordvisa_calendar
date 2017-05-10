package calendar.User;

import calendar.DatabaseConnections.MongoDBClient;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

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

    User getUserById(String id) {
        MongoCollection collection = client.getCollection("users");

        return collection.findOne(new ObjectId(id)).as(User.class);
    }

    User getUserByEmail(String email) {
        MongoCollection collection = client.getCollection("users");

        return collection.findOne("{email: \"" + email + "\"}").as(User.class);
    }

    void verifyEmailAddress(String id) {
        MongoCollection collection = client.getCollection("users");
        User user = collection.findOne("{validateEmailLink: \"" + id + "\"}").as(User.class);

        user.setValidateEmailLink("");
        collection.update(new ObjectId(user.getId())).with(user);
    }
}
