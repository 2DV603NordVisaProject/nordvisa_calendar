package calendar.User;

import org.joda.time.DateTime;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Random;

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

    private String resetPasswordLink;
    private String validateEmailLink;

    private DateTime createdAt;
    private DateTime updatedAt;

    private ArrayList<String> events;

    public User() {

    }

    User(RegistrationDTO dto) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        this.email = dto.email;
        this.password = encoder.encode(dto.password);
        this.role = "USER";

        this.resetPasswordLink = "";
        this.validateEmailLink = generateRandomString();

        this.createdAt = DateTime.now();
        this.updatedAt = DateTime.now();

        this.events = new ArrayList<>();
    }

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

    String getResetPasswordLink() {
        return resetPasswordLink;
    }

    String getValidateEmailLink() {
        return validateEmailLink;
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
