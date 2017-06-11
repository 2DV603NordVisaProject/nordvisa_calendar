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
    @Value("${smtp.host}")
    private String smtpHost;
    @Value("${smtp.sender}")
    private String smtpSender;

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
        String link = "http://" + baseUrl + "/api/visitor/verify_email?id=" + id;
        String title = "Verify Email";
        String message = "Hello!\n Someone has created an account for this account. If this was " +
                "not you then just ignore this message. If it was you then click the link bellow" +
                "\n\n" + link;
        sendMessage(email, title, message);
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
        String link = "http://" + baseUrl + "/update-password/" + id;
        String title = "Password recovery";
        String message = "Hello!\n Someone has requested a password recovery on this email. If this" +
                "was not you then just ignore this message. If not then click the link bellow. " +
                "\n\n" + link;
        sendMessage(email, title, message);
    }

    /**
     * Sends an email to the email address telling them a recent action has been successfull. This
     * could be registration or organization change.
     *
     * @param email The email address email should be sent to
     * @param type  The type of action, registration or organization change
     */
    void sendSuccessEmail(String email, String type) {
        String title = "Your " + type + " was successful";
        String message = "Hello!\n Your " + type + " was accepted by an administrator";
        sendMessage(email, title, message);
    }

    /**
     * Sends and email to the email address telling the user a recent action has been denied. This
     * could be registration on organization change.
     *
     * @param email The email address email should be sent to
     * @param type  The type of action, registration or organization change.
     */

    void sendDenialEmail(String email, String type) {
        String title = "Your " + type + " was denied";
        String message = "Hello!\n Your " + type + " was denied by an administrator";
        sendMessage(email, title, message);
    }

    private void sendMessage(String to, String title, String message) {
        System.out.println("Sent to " + to);
        System.out.println(title);
        System.out.println(message);
    }
}
