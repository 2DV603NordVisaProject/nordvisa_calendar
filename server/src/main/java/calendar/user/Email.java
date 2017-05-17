package calendar.user;

import org.springframework.stereotype.Component;

/**
 * Class Email
 *
 * @author Axel Nilsson (axnion)
 */
@Component
class Email {
    // TODO: Make base url a property in application.propoerties
    void sendVerificationEmail(String id, String email) {
        System.out.println("Sent to (" + email + ")http://localhost:8080/api/visitor/verify_email?id=" + id);
    }

    void sendPasswordResetEmail(String id, String email) {
        System.out.println("Sent to (" + email + ") http://localhost:8080/change_password/" + id);
    }
}
