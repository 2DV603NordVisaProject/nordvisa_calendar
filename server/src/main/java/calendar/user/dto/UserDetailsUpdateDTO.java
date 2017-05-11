package calendar.user.dto;

/**
 * Class UserDetailsUpdateDTO
 *
 * @author Axel Nilsson (axnion)
 */
public class UserDetailsUpdateDTO {
    private String id;
    private String email;
    private String organization;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getOrganization() {
        return organization;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}