package calendar.user;

import org.springframework.stereotype.Component;

/**
 * Class Email
 *
 * @author Axel Nilsson (axnion)
 */
@Component
class Email {
    // TODO: Add Email as parameter
    void sendVerificationEmail(String id) {
        System.out.println("http://localhost:8080/api/visitor/verify_email?id=" + id);
    }

    void sendPasswordResetEmail(String id) {
        System.out.println("http://localhost:8080/change_password/" + id);
    }
}
