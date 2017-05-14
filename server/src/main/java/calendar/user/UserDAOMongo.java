package calendar.user;

import calendar.databaseConnections.MongoDBClient;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Class UserDAOMongo
 *
 * @author Axel Nilsson (axnion)
 */
@Component
class UserDAOMongo implements UserDAO {
    private Jongo client;

    //TODO: Secure from injection
    UserDAOMongo() {
        client = MongoDBClient.getClient();
    }

    /**
     * Takes the id of a potential User and returns the User object. If user does not exist null
     * is returned.
     *
     * @param id    ObjectId of the user in the database
     * @return      A User object with matching id as argument
     */
    public User getUserById(String id) {
        MongoCollection collection = client.getCollection("users");
        return collection.findOne(new ObjectId(id)).as(User.class);
    }

    /**
     * Takes the email of a potential User and returns the User object. If no user is found then
     * null is returned.
     *
     * @param email Email of the User to be returned
     * @return      A User object with matching email as argument
     */
    public User getUserByEmail(String email) {
        MongoCollection collection = client.getCollection("users");
        return collection.findOne("{email: \"" + email + "\"}").as(User.class);
    }

    public User getUserByPasswordRecoveryLink(String urlId) {
        MongoCollection collection = client.getCollection("users");
        return collection.findOne("{validateEmailLink.url: \"" + urlId + "\"}").as(User.class);
    }

    public User getUserByEmailVerificationLink(String urlId) {
        MongoCollection collection = client.getCollection("users");
        return collection.findOne("{validateEmailLink.url: \"" + urlId + "\"}")
                .as(User.class);
    }

    /**
     * Takes the organization name and returns all Users with a matching organization name
     *
     * @param organizationName  Name of the organization which members to be returned
     * @return                  An Arraylist containing all Users within the argument organization.
     */
    public ArrayList<User> getUsersByOrganization(String organizationName) {
        MongoCollection collection = client.getCollection("users");
        return cursorToArray(collection.find(
                "{organization.name: \"" + organizationName + "\"}").as(User.class)
        );
    }

    /**
     * Returns all Users in the database.
     *
     * @return              An ArrayList of all Users in the database
     * @throws Exception    Database errors
     */
    public ArrayList<User> getAllUsers() throws Exception {
        MongoCollection collection = client.getCollection("users");
        return cursorToArray(collection.find("{}").as(User.class));
    }

    public ArrayList<User> getPendingRegistrations(String organization) throws Exception {
        MongoCollection collection = client.getCollection("users");

        ArrayList<User> list;

        list = cursorToArray(collection.find("{organization.approved: false}").as(User.class));
        list.addAll(cursorToArray(collection.find("{organization.changePending: \""
                + organization + "\"}").as(User.class)));

        return list;
    }

    /**
     * Takes a User object and adds it to the database.
     *
     * @param user  The User object to be inserted into the database
     */
    public void add(User user) {
        MongoCollection collection = client.getCollection("users");


        collection.insert(user);
    }

    public void delete(String id) {
        MongoCollection collection = client.getCollection("users");
        collection.remove(new ObjectId(id));
    }

    public void update(User user) {
        MongoCollection collection = client.getCollection("users");
        collection.update(new ObjectId(user.getId())).with(user);
    }

    private static ArrayList<User> cursorToArray(MongoCursor<User> cursor) {
        ArrayList<User> list = new ArrayList<>();

        while(cursor.hasNext()) {
            list.add(cursor.next());
        }

        return list;
    }
}
