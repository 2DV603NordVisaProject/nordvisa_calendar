package calendar.user.dto;


/**
 * Class RegistrationDecisionDTO
 *
 * @author Axel Nilsson (axnion)
 */
public class RegistrationDecisionDTO {
    private String id;
    private boolean approved;

    public String getId() {
        return id;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setId(String id) {
        this.id = id;
    }
}
