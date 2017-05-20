package calendar.user.dto;

import calendar.user.Organization;
import calendar.user.User;

import java.util.ArrayList;

/**
 * Class UserDTO
 *
 * @author Axel Nilsson (axnion)
 */
public class UserDTO {
    private String id;
    private String email;
    private String role;

    private long createdAt;
    private long updatedAt;

    private Organization organization;

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();

        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();

        this.organization = user.getOrganization();
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public Organization getOrganization() {
        return organization;
    }
}
