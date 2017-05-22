package calendar.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class Email
 *
 * This class is responsible for sending emails. Currently in a simplified state methods only
 * print the message in the console.
 *
 * @author Axel Nilsson (axnion)
 */
@Component
class Email {
    @Value("${base_url}")
    private String baseUrl; //Base url for the website

    /**
     * Send a verification email to the specified email. The email will contain a link which will
     * take the user to a webpage which validates their email address since only the person with
     * access to that email address should be able to access this link.
     *
     * @param id    The url id which is unique for this email
     * @param email The email address which the email should be sent to.
     */
    void sendVerificationEmail(String id, String email) {
        System.out.println("Sent to (" + email + ")http://" + baseUrl + "/api/visitor/verify_email?id=" + id);
    }

    /**
     * Send a password recovery link though email to the specified address. Takes a unique id which
     * identifies the specified user. Only the owner of the email address should be able to access
     * this link.
     *
     * @param id    The url id which is unique for this email
     * @param email The email address which the email should be sent to
     */
    void sendPasswordResetEmail(String id, String email) {
        System.out.println("Sent to (" + email + ") http://" + baseUrl + "/api/visitor/change_password/" + id);
    }
    // TODO: Add success email on registration when registration is approved or declined.
}
