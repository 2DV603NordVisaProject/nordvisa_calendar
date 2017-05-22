package calendar.user;

import calendar.databaseConnections.MongoDBClient;
import org.bson.types.ObjectId;
import org.jongo.Distinct;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class UserDAOMongo
 *
 * This is the MongoDB implementation of UserDAO which uses Jongo to communicate with the MongoDB
 * database
 *
 * @author Axel Nilsson (axnion)
 */
@Component
public class UserDAOMongo implements UserDAO {
    private Jongo client;

    /**
     * Default constructor
     */
    public UserDAOMongo() {
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

    /**
     * Find a User with a matching password recovery urlId and return the User
     *
     * @param urlId A urlId from a password recovery link
     * @return      A User which has a password recovery link which matches the given urlId
     */
    public User getUserByPasswordRecoveryLink(String urlId) {
        MongoCollection collection = client.getCollection("users");
        return collection.findOne("{validateEmailLink.url: \"" + urlId + "\"}").as(User.class);
    }

    /**
     * Find a User wih a matching email verification link id and returns the User
     *
     * @param urlId A urlIdfrom a email verification link
     * @return      A User with a matching urlId to the given urlId
     */
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


    /**
     * Find all pending registraitons and organization changes which are relevent to the
     * administrator based on their organization.
     *
     * @param organization  Organization of the administrator
     * @return              An ArrayList containing User which have pending registrations or
     *                      organizaton changes relevant to the administrator
     * @throws Exception    Database errors
     */
    // TODO: Users who want to join the global group can't
    public ArrayList<User> getPendingRegistrations(String organization) throws Exception {
        MongoCollection collection = client.getCollection("users");

        ArrayList<User> finalList = new ArrayList<>();
        ArrayList<User> unapproved = cursorToArray(collection.find(
                "{organization.approved: false}"
        ).as(User.class));

        ArrayList<User> changingOrg;

        if(organization.equals("")) {
            changingOrg = cursorToArray(collection.find(
                    "{organization.changePending: \"\"}"
            ).as(User.class));
        }
        else {
            changingOrg = cursorToArray(collection.find(
                    "{organization.changePending: \"" + organization + "\"}"
            ).as(User.class));
        }

        Iterator<User> changingOrgIterator = changingOrg.iterator();

        while(changingOrgIterator.hasNext()) {
            User next = changingOrgIterator.next();

            if(next.getOrganization().getChangePending().equals("")) {
                changingOrgIterator.remove();
            }
        }

        addToList(finalList, unapproved);
        addToList(finalList, changingOrg);

        for(User user : unapproved) {
            System.out.println("2 " + user.getEmail());
        }

        for(User user :changingOrg) {
            System.out.println("3 " + user.getEmail());
        }

        return finalList;
    }

    /**
     * Returns a List of String with the names of every organization in the system
     * @return  A list of organization namesA list of organization names
     */
    public List<String> getOrganizations() {
        MongoCollection collection = client.getCollection("users");
        Distinct distinct = collection.distinct("organization.name");
        return distinct.as(String.class);
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

    /**
     * Deletes the User with a matching id from the database
     * @param id    Id of the User to be deleted
     */
    public void delete(String id) {
        MongoCollection collection = client.getCollection("users");
        collection.remove(new ObjectId(id));
    }

    /**
     * Updated a user with a matching id to the argument user, with the content of the argument user
     * @param user  An updates User object
     */
    public void update(User user) {
        MongoCollection collection = client.getCollection("users");
        collection.update(new ObjectId(user.getId())).with(user);
    }

    /**
     * Takes a finalList and a toBeAdded, both ArrayList. It will go though the toBeAdded list and
     * add each User who does not have a duplication in the finalList. This means finalList will end
     * up as a Set in an ArrayList
     *
     * @param finalList The final list which User objects are added to if no duplicate is found
     * @param toBeAdded The arraylist User objects are taken from
     */
    private void addToList(ArrayList<User> finalList, ArrayList<User> toBeAdded) {
        for(User user : toBeAdded) {
            boolean doesNotExist = true;

            for (User existingUser : finalList) {
                if(existingUser.getId().equals(user.getId())) {
                    doesNotExist = false;
                    break;
                }
            }

            if (doesNotExist) {
                finalList.add(user);
            }
        }
    }

    /**
     * Takes a MongoCurson containing User object and moving them to an ArrayList.
     *
     * @param cursor    MongoCursor to be conveted to an ArrayList
     * @return          An ArrayList containing all user object from the cursor
     */
    private ArrayList<User> cursorToArray(MongoCursor<User> cursor) {
        ArrayList<User> list = new ArrayList<>();

        while(cursor.hasNext()) {
            list.add(cursor.next());
        }

        return list;
    }
}
