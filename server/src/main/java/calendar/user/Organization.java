package calendar.user;

/**
 * Class Organization
 *
 * An instance of this class is a representation of a persistent Organization of a User. It has the
 * name of the organization, if the user has been approved, and any pending organzation changes.
 *
 * @author Axel Nilsson (axnion)
 */
public class Organization {
    private String name;
    private boolean approved;
    private String changePending;

    /**
     * Constructor used by Jackson when converting from JSON
     */
    Organization() {
        name = "";
        approved = false;
        changePending = "";
    }

    /**
     * Getter
     * @return Name of the organziation
     */
    String getName() {
        return name;
    }

    /**
     * Getter
     * @return True if the user is approved, false if not
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * Getter
     * @return The name of the organization the user want to change to
     */
    String getChangePending() {
        return changePending;
    }

    /**
     * Setter
     * @param name Name of the organziation the user is to join
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * Setter
     * @param approved True if the user is approved, false if not
     */
    void setApproved(boolean approved) {
        this.approved = approved;
    }

    /**
     * Setter
     * @param organizationName Name of the organization the user want to change to
     */
    void setChangePending(String organizationName) {
        this.changePending = organizationName;
    }

    /**
     * Compares two organzations to see if thay are the same. It's only based on the name
     * @param other The organziation we will compare this organiztaion to
     * @return      True if the organizaton names match. False if not
     */
    boolean compare(Organization other) {
        return name.equals(other.getName());
    }
}
