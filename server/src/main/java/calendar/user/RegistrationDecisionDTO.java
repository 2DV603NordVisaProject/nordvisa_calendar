package calendar.user;

/**
 * Class RegistrationDecisionDTO
 *
 * @author Axel Nilsson (axnion)
 */
class RegistrationDecisionDTO {
    private String id;
    private boolean approved;

    String getId() {
        return id;
    }

    boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setId(String id) {
        this.id = id;
    }
}
