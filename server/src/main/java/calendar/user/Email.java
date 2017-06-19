package calendar.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

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
    @Autowired
    private Environment env;

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
        System.out.println("Sent to (" + email + ")http://" + getURI() + "/api/visitor/verify_email?id=" + id);
        String link = "http://" + getURI() + "/api/visitor/verify_email?id=" + id;
        String title = "Verify Email";
        String message = "Hello!\nSomeone has created an account for this account. If this was " +
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
        String link = "http://" + getURI() + "/update-password/" + id;
        String title = "Password recovery";
        String message = "Hello!\nSomeone has requested a password recovery on this email. If this" +
                "was not you then just ignore this message. If not then click the link bellow. " +
                "\n\n" + link;
        sendMessage(email, title, message);
    }

    /**
     * Sends an email to the email address telling them a recent action has been successful. This
     * could be registration or organization change.
     *
     * @param email The email address email should be sent to
     * @param type  The type of action, registration or organization change
     */
    void sendSuccessEmail(String email, String type) {
        String title = "Your " + type + " was successful";
        String message = "Hello!\nYour " + type + " was accepted by an administrator";
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
        String message = "Hello!\nYour " + type + " was denied by an administrator";
        sendMessage(email, title, message);
    }

    private void sendMessage(String to, String title, String message) {
        if(configOk()) {
            System.out.println("DEV MODE");
            printMessage(to, title, message);
        } else {
            System.out.println("NON DEV MODE");
            sendEmail(to, title, message);
        }
    }

    private void printMessage(String to, String title, String message) {
        System.out.println("SMTP_HOST: " + smtpHost);
        System.out.println("-------------------------------EMAIL---------------------------------");
        System.out.println("Sent to " + to);
        System.out.println(title);
        System.out.println(message);
        System.out.println("---------------------------------------------------------------------");
    }

    private void sendEmail(String to, String title, String message) {
        System.out.println("EMAIL IS NOT IMPLEMENTED YOU LOOSER!");
    }

    private String getURI() {
        String host = InetAddress.getLoopbackAddress().getHostName();
        String port = env.getProperty("local.server.port");

        if(!port.equals("80")) {
            host = host + ":" + port;
        }

        return host;
    }

    private boolean configOk() {
        return !smtpHost.equals("") && !smtpSender.equals("");
    }
}
