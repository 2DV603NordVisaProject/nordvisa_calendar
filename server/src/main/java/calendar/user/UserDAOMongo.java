package calendar.user;

import calendar.databaseConnections.MongoDBClient;
import calendar.user.dto.RegistrationDTO;
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

    public User createUser(RegistrationDTO dto) {
        MongoCollection collection = client.getCollection("users");

        User user = new User(dto);
        collection.insert(user);

        return user;
    }

    public void deleteUser(String id) {
        MongoCollection collection = client.getCollection("users");
        collection.remove(new ObjectId(id));
    }

    public User updateUserDetails(UserDetailsUpdateDTO dto) {
        MongoCollection collection = client.getCollection("users");
        User user = collection.findOne(new ObjectId(dto.getId())).as(User.class);

        // TODO: If email is incorrect the user can't log in again and recover. :(
        if (!user.getEmail().equals(dto.getEmail())) {
            user.setEmail(dto.getEmail());
            user.createValidateEmailLink();
        }

        user.getOrganization().setChangePending(dto.getOrganization());

        collection.update(new ObjectId(user.getId())).with(user);

        return user;
    }

    public void changePassword(String id, String password, String passwordConfirmation) {
        System.out.println("UserDAOMongo.changePassword is not implemented");
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

    public void makeAdministrator(String id) {
        System.out.println("UserDAOMongo.makeAdministrator is not implemented");
    }

    public void removeAdministrator(String id) {
        System.out.println("UserDAOMongo.removeAdministrator is not implemented");
    }

    public void makeSuperAdministrator(String id) {
        System.out.println("UserDAOMongo.makeSuperAdministrator is not implemented");
    }

    private static ArrayList<User> cursorToArray(MongoCursor<User> cursor) {
        ArrayList<User> list = new ArrayList<>();

        while(cursor.hasNext()) {
            list.add(cursor.next());
        }

        return list;
    }
}
