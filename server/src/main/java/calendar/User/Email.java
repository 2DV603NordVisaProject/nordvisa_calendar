package calendar.User;

/**
 * Class Email
 *
 * @author Axel Nilsson (axnion)
 */
class Email {
    void sendVerificationEmail(String id) {
        System.out.println("http://localhost:8080/api/visitor/verify_email?id=" + id);
    }

    void sendPasswordResetEmail(String id) {
        System.out.println("http://localhost:8080/email/reset?id=" + id);
    }
}
