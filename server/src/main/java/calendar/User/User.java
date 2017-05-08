package calendar.User;

import org.joda.time.DateTime;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.ArrayList;

/**
 * Class User
 *
 * @author Axel Nilsson (axnion)
 */
class User {
    @MongoId
    @MongoObjectId
    private String id;
    private String email;
    private String password;
    private String role;

    private DateTime createdAt;
    private DateTime updatedAt;

    private ArrayList<String> events;

    String getId() {
        return id;
    }

    String getEmail() {
        return email;
    }

    String getPassword() {
        return password;
    }

    String[] getRole() {
        String[] roles = null;

        if(role.equals("USER")) {
            roles = new String[] {"USER"};
        }

        if(role.equals("ADMIN")) {
            roles = new String[] {"USER", "ADMIN"};
        }

        if(role.equals("SUPER_ADMIN")) {
            roles = new String[] {"USER", "ADMIN", "SUPER_ADMIN"};
        }

        return roles;
    }

    DateTime getCreatedAt() {
        return createdAt;
    }

    DateTime getUpdatedAt() {
        return updatedAt;
    }

    ArrayList<String> getEvents() {
        return events;
    }

    void update(UserUpdateDTO userUpdate) {
        System.out.println("UPDATE IS NOT IMPLEMENTED");
    }
}
