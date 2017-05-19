package calendar.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class Email
 *
 * @author Axel Nilsson (axnion)
 */
@Component
class Email {
    @Value("${base_url}")
    private String baseUrl;

    // TODO: Make base url a property in application.propoerties
    void sendVerificationEmail(String id, String email) {
        System.out.println("Sent to (" + email + ")http://" + baseUrl + "/api/visitor/verify_email?id=" + id);
    }

    void sendPasswordResetEmail(String id, String email) {
        System.out.println("Sent to (" + email + ") http://" + baseUrl + "/change_password/" + id);
    }
}
