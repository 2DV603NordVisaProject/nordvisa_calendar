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

    @Autowired
    private Organization organization;

    private String emailChange;

    /**
     * Constructor used by Jackson when converting from JSON
     */
    public User() {

    }

    /**
     * Constructor used during registration. It fills in the nessecary parts of the User.
     *
     * @param dto A DTO used to receive registration data.
     */
    User(RegistrationDTO dto) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        this.email = dto.getEmail();
        this.password = encoder.encode(dto.getPassword());
        this.role = "USER";

        this.resetPasswordLink = new AuthenticationLink("", 0);
        this.validateEmailLink = new AuthenticationLink("", 0);

        this.createdAt = DateTime.now().getMillis();
        this.updatedAt = DateTime.now().getMillis();

        this.organization = new Organization();

        if(dto.getOrganization().equals("")) {
            this.organization.setChangePending("_");
        }
        else {
            this.organization.setChangePending(dto.getOrganization());
        }

        this.organization.setApproved(false);

        this.emailChange = dto.getEmail();
    }

    /**
     * Getter
     * @return Id of the User
     */
    public String getId() {
        return id;
    }

    /**
     * Getter
     * @return Email of the User.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter
     * @return Hashed password of the User
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter
     * @return Authoratory role of the User
     */
    public String getRole() {
        return role;
    }

    /**
     * Getter
     * @return The reset password link for this User
     */
    public AuthenticationLink getResetPasswordLink() {
        return resetPasswordLink;
    }

    /**
     * Getter
     * @return The validate email link of this User
     */
    public AuthenticationLink getValidateEmailLink() {
        return validateEmailLink;
    }

    /**
     * Getter
     * @return The timestamp when user was created
     */
    public long getCreatedAt() {
        return createdAt;
    }

    /**
     * Getter
     * @return Timestamp when user was last updated
     */
    public long getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Getter
     * @return Organization of the User
     */
    public Organization getOrganization() {
        return organization;
    }

    public String getEmailChange() {
        return emailChange;
    }

    /**
     * Setter
     * @param id The id of the User
     */
    void setId(String id) {
        this.id = id;
    }

    /**
     * Setter
     * @param email Email of the User
     */
    void setEmail(String email) {
        this.email = email;
    }

    /**
     * Setter
     * @param password New unencoded password of the User
     */
    void setPassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    /**
     * Setter
     * @param role New role of the user
     */
    void setRole(String role) {
        this.role = role;
    }

    /**
     * Setter
     * @param resetPasswordLink New reset password link for the User
     */
    void setResetPasswordLink(AuthenticationLink resetPasswordLink) {
        this.resetPasswordLink = resetPasswordLink;
    }

    /**
     * Setter
     * @param validateEmailLink New validate email link for the User
     */
    void setValidateEmailLink(AuthenticationLink validateEmailLink) {
        this.validateEmailLink = validateEmailLink;
    }

    /**
     * Setter
     * @param organization New Organization for the user
     */
    void setOrganization(Organization organization) {
        this.organization = organization;
    }

    void setEmailChange(String emailChange) {
        this.emailChange = emailChange;
    }

    /**
     * Method which returns a list of authorities for Spring Security to use.
     * @return  An array of roles to fit Spring Security
     */
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

    public boolean valid() {
        return organization.isApproved() && !email.equals(emailChange);
    }

    /**
     * The method determines if this user is authorized to manage the other account. Manage involves
     * editing of events created by target user, unregister the account.
     *
     * TODO: Check/Add logic so a user who's registration is not approved can not be managed.
     * @param target    The target user which this user want to do at action on.
     * @return          True if this user can manage the target user
     */
    boolean canManage(User target) {
        if(target == null) {
            return false;
        }
        else if(!target.getOrganization().isApproved()) {
            return false;
        }
        else if(getId().equals(target.getId())) {
            return true;
        }
        else if(target.getRole().equals("SUPER_ADMIN")) {
            return false;
        }
        else if(getRole().equals("SUPER_ADMIN")) {
            return true;
        }
        else if(getRole().equals("ADMIN")) {
            if(getOrganization().getName().equals("")) {
                if(target.getRole().equals("USER")) {
                    return true;
                }
                else if(!target.getOrganization().getName().equals("")) {
                    return true;
                }
            }
            else if(getOrganization().compare(target.getOrganization())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method goes though a large set of logic to find if this user has the authorities to to change
     * the role of the target user to a specified role.
     *
     * @param target    The user this user wants to change role of
     * @param newRole   The new role this user wants to give target
     * @return          True if this user is allowed this action. False if not
     */
    boolean canChangeRoleTo(User target, String newRole) {
        if(target == null) {
            return false;
        }
        else if(getRole().equals("USER")) {
            return false;
        }
        else if(target.getRole().equals("SUPER_ADMIN")) {
            return false;
        }
        else if(target.getRole().equals(newRole)) {
            return false;
        }
        else if(newRole.equals("USER")) {
            if(getRole().equals("ADMIN") && getOrganization().compare(target.getOrganization())) {
                return true;
            }
            else if(getRole().equals("ADMIN") && getOrganization().getName().equals("") &&
                    !target.getOrganization().getName().equals("")) {
                return true;
            }
            else if(getRole().equals("SUPER_ADMIN")) {
                return true;
            }
        }
        else if(newRole.equals("ADMIN")) {
            if(getRole().equals("ADMIN") && getOrganization().compare(target.getOrganization())) {
                return true;
            }
            else if(getRole().equals("ADMIN") && getOrganization().getName().equals("")) {
                return true;
            }
            else if(getRole().equals("SUPER_ADMIN")) {
                return true;
            }
        }
        else if(getRole().equals("SUPER_ADMIN")) {
                return true;
        }

        return false;
    }
}
