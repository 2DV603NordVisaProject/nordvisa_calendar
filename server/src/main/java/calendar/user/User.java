package calendar.user;

import calendar.user.dto.RegistrationDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private AuthenticationLink resetPasswordLink;
    @JsonIgnore
    private AuthenticationLink validateEmailLink;

    private long createdAt;
    private long updatedAt;

    private ArrayList<String> events;

    private Organization organization;

    public User() {

    }

    User(RegistrationDTO dto) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        this.email = dto.getEmail();
        this.password = encoder.encode(dto.getPassword());
        this.role = "USER";

        this.resetPasswordLink = new AuthenticationLink("", 0);
        this.validateEmailLink = new AuthenticationLink("", 0);

        this.createdAt = DateTime.now().getMillis();
        this.updatedAt = DateTime.now().getMillis();

        this.events = new ArrayList<>();
        this.organization = new Organization(dto.getOrganization(), false);
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

    String getRole() {
        return role;
    }

    String[] getAuthorities() {
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

    AuthenticationLink getResetPasswordLink() {
        return resetPasswordLink;
    }

    AuthenticationLink getValidateEmailLink() {
        return validateEmailLink;
    }

    long getCreatedAt() {
        return createdAt;
    }

    long getUpdatedAt() {
        return updatedAt;
    }

    ArrayList<String> getEvents() {
        return events;
    }

    Organization getOrganization() {
        return organization;
    }

    void setEmail(String email) {
        this.email = email;
    }

    void setPassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    void setRole(String role) {
        this.role = role;
    }

    void setResetPasswordLink(AuthenticationLink resetPasswordLink) {
        this.resetPasswordLink = resetPasswordLink;
    }

    void setValidateEmailLink(AuthenticationLink validateEmailLink) {
        this.validateEmailLink = validateEmailLink;
    }

//    void setCreatedAt(long createdAt) {
//        this.createdAt = createdAt;
//    }

    void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    void setEvents(ArrayList<String> events) {
        this.events = events;
    }

    void setOrganization(Organization organization) {
        this.organization = organization;
    }

}
