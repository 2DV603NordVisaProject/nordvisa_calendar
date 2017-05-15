package calendar.user;

import org.springframework.stereotype.Component;

/**
 * Class Organization
 *
 * @author Axel Nilsson (axnion)
 */
@Component
public class Organization {
    private String name;
    private boolean approved;
    private String changePending;

    Organization() {

    }

    Organization(String name, boolean approved) {
        this.name = name;
        this.approved = approved;
        this.changePending = "";
    }

    String getName() {
        return name;
    }

    public boolean isApproved() {
        return approved;
    }

    String getChangePending() {
        return changePending;
    }

    void setName(String name) {
        this.name = name;
    }

    void setApproved(boolean approved) {
        this.approved = approved;
    }

    void setChangePending(String organizationName) {
        this.changePending = organizationName;
    }

    boolean compare(Organization other) {
        return name.equals(other.getName());
    }
}
