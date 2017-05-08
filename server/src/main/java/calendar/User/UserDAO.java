package calendar.User;

import calendar.DatabaseConnections.MongoDBClient;
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
}
