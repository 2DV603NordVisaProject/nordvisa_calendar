package calendar.user;

import calendar.user.dto.RegistrationDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class User
 *
 * @author Axel Nilsson (axnion)
 */
public class User {
    @MongoId
    @MongoObjectId
    private String id;
    private String email;
    private String password;
    private String role;

    private AuthenticationLink resetPasswordLink;
    private AuthenticationLink validateEmailLink;

    private long createdAt;
    private long updatedAt;

    private ArrayList<String> events;

    @Autowired
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

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public AuthenticationLink getResetPasswordLink() {
        return resetPasswordLink;
    }

    public AuthenticationLink getValidateEmailLink() {
        return validateEmailLink;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public Organization getOrganization() {
        return organization;
    }

    void setId(String id) {
        this.id = id;
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

    void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String[] fetchAuthorities() {
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

    public boolean isValid() {
        return organization.isApproved() && validateEmailLink.getUrl().equals("");
    }

    /**
     * The method determines if this user is authorized to manage the other account. Manage involves
     * editing of events created by target user, unregister the account.
     *
     * @param target    The target user which this user want to do at action on.
     * @return          True if this user can manage the target user
     */
    boolean canManage(User target) {
        boolean answer = false;

        if(id.equals(target.getId())) {
            answer = true;
        }
        else if(target.getRole().equals("SUPER_ADMIN")) {
            answer = false;
        }
        else if(role.equals("SUPER_ADMIN")) {
            answer = true;
        }
        else if(role.equals("ADMIN")) {
            if(getOrganization().getName().equals("")) {
                if(target.getRole().equals("USER")) {
                    answer = true;
                }
                else if(!target.getOrganization().getName().equals("")) {
                    answer = true;
                }
            }
            else if(organization.compare(target.getOrganization()) &&
                    target.getRole().equals("USER")) {
                answer = true;
            }
        }

        return answer;
    }

    boolean canChangeRoleTo(User target, String newRole) {
        if(getRole().equals("USER")) {
            return false;
        }
        else if(target.getRole().equals("SUPER_ADMIN")) {
            return false;
        }
        else if(target.getRole().equals(newRole)) {
            return false;
        }
        else if(newRole.equals("USER")) {
            if(getRole().equals("ADMIN") && (organization.getName().equals("") ||
                    organization.compare(target.getOrganization()))) {

                if(organization.compare(target.getOrganization())) {
                    return true;
                }
                else if(organization.getName().equals("") &&
                        !target.getOrganization().getName().equals("")) {
                    return true;
                }
            }
            else if(getRole().equals("SUPER_ADMIN")) {
                return true;
            }
        }
        else if(newRole.equals("ADMIN")) {
            if(getRole().equals("ADMIN") && organization.compare(target.getOrganization())) {
                return true;
            }
            else if(getRole().equals("ADMIN") && getOrganization().getName().equals("")) {
                return true;
            }
            else if(getRole().equals("SUPER_ADMIN")) {
                return true;
            }
        }
        else if(newRole.equals("SUPER_ADMIN")) {
            if(getRole().equals("SUPER_ADMIN")) {
                return true;
            }
        }

        return false;
    }
}
