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

        return collection.findOne("{email = \"" + email + "\"}").as(User.class);
    }
}
