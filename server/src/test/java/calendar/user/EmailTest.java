package calendar.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class EmailTest
 *
 * @author Axel Nilsson (axnion)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailTest {
    @Mock
    private Environment env;
    @Mock
    private MailSender sender;

    @InjectMocks
    Email sut;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream stdout;

    @Before
    public void setUpStreams() {
        stdout = System.out;
        System.setOut(new PrintStream(outContent));
        when(env.getProperty("local.server.port")).thenReturn("8080");
    }

    @After
    public void cleanUpStreams() {
        System.setOut(stdout);
    }

    @Test
    public void sendVerificationEmailPrintOut() {
        ReflectionTestUtils.setField(sut, "mailEnabled", false);
        String id = "1234567890";
        String email = "test@test.com";

        String expected = "Sent to test@test.com\nVerify Email\nHello!\n" +
                "Someone has created an account for this email address. If this was not you then just ignore " +
                "this message. If it was you then click the link bellow.\n\n" +
                "http://localhost:8080/api/visitor/verify_email?id=" + id + "\n";

        sut.sendVerificationEmail(id, email);

        assertEquals(expected, outContent.toString());
    }

    @Test
    public void sendPasswordResetEmailPrintOut() {
        ReflectionTestUtils.setField(sut, "mailEnabled", false);
        String id = "1234567890";
        String email = "test@test.com";

        String expected = "Sent to test@test.com\nPassword recovery\nHello!\n" +
                "Someone has requested a password recovery on this email address. If this was not you then just " +
                "ignore this message. If not then click the link bellow.\n\n" +
                "http://localhost:8080/update-password/" + id + "\n";

        sut.sendPasswordResetEmail(id, email);

        assertEquals(expected, outContent.toString());
    }

    @Test
    public void sendRegistrationSuccessEmailPrintOut() {
        ReflectionTestUtils.setField(sut, "mailEnabled", false);
        String email = "test@test.com";

        String expected = "Sent to test@test.com\nYour registration was successful\nHello!\n" +
                "Your registration was accepted by an administrator\n";

        sut.sendSuccessEmail(email, "registration");

        assertEquals(expected, outContent.toString());
    }

    @Test
    public void sendOrganizationChangeSuccessEmailPrintOut() {
        ReflectionTestUtils.setField(sut, "mailEnabled", false);
        String email = "test@test.com";

        String expected = "Sent to test@test.com\nYour organization change was successful\nHello!\n" +
                "Your organization change was accepted by an administrator\n";

        sut.sendSuccessEmail(email, "organization change");

        assertEquals(expected, outContent.toString());
    }

    @Test
    public void sendRegistrationDenialEmailPrintOut() {
        ReflectionTestUtils.setField(sut, "mailEnabled", false);
        String email = "test@test.com";

        String expected = "Sent to test@test.com\nYour registration was denied\nHello!\n" +
                "Your registration was denied by an administrator\n";

        sut.sendDenialEmail(email, "registration");

        assertEquals(expected, outContent.toString());
    }

    @Test
    public void sendOrganizationChangeDenialEmailPrintOut() {
        ReflectionTestUtils.setField(sut, "mailEnabled", false);
        String email = "test@test.com";

        String expected = "Sent to test@test.com\nYour organization change was denied\nHello!\n" +
                "Your organization change was denied by an administrator\n";

        sut.sendDenialEmail(email, "organization change");

        assertEquals(expected, outContent.toString());
    }

    @Test
    public void sendVerificationEmailSMTP() {
        ReflectionTestUtils.setField(sut, "mailEnabled", true);

        String id = "1234567890";
        String email = "test@test.com";
        String subject = "Verify Email";
        String expected = "Hello!\nSomeone has created an account for this email address. If this was not you then " +
                "just ignore this message. If it was you then click the link bellow.\n\n" +
                "http://localhost:8080/api/visitor/verify_email?id=" + id;

        sut.sendVerificationEmail(id, email);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(sender, times(1)).send(captor.capture());
        SimpleMailMessage message = captor.getValue();


        assertEquals(1, message.getTo().length);
        assertEquals(email, message.getTo()[0]);
        assertEquals(subject, message.getSubject());
        assertEquals(expected, message.getText());
    }

    @Test
    public void sendPasswordResetEmailSMTP() {
        ReflectionTestUtils.setField(sut, "mailEnabled", true);

        String id = "1234567890";
        String email = "test@test.com";
        String subject = "Password recovery";
        String expected = "Hello!\nSomeone has requested a password recovery on this email address. If this was not " +
                "you then just ignore this message. If not then click the link bellow.\n\n" +
                "http://localhost:8080/update-password/" + id;

        sut.sendPasswordResetEmail(id, email);

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(sender, times(1)).send(captor.capture());
        SimpleMailMessage message = captor.getValue();


        assertEquals(1, message.getTo().length);
        assertEquals(email, message.getTo()[0]);
        assertEquals(subject, message.getSubject());
        assertEquals(expected, message.getText());
    }

    @Test
    public void sendRegistrationSuccessEmailSMTP() {
        ReflectionTestUtils.setField(sut, "mailEnabled", true);

        String email = "test@test.com";
        String subject = "Your registration was successful";
        String expected = "Hello!\nYour registration was accepted by an administrator";

        sut.sendSuccessEmail(email, "registration");

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(sender, times(1)).send(captor.capture());
        SimpleMailMessage message = captor.getValue();


        assertEquals(1, message.getTo().length);
        assertEquals(email, message.getTo()[0]);
        assertEquals(subject, message.getSubject());
        assertEquals(expected, message.getText());
    }

    @Test
    public void sendOrganizationChangeSuccessEmailSMTP() {
        ReflectionTestUtils.setField(sut, "mailEnabled", true);

        String email = "test@test.com";
        String subject = "Your organization change was successful";
        String expected = "Hello!\nYour organization change was accepted by an administrator";

        sut.sendSuccessEmail(email, "organization change");

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(sender, times(1)).send(captor.capture());
        SimpleMailMessage message = captor.getValue();


        assertEquals(1, message.getTo().length);
        assertEquals(email, message.getTo()[0]);
        assertEquals(subject, message.getSubject());
        assertEquals(expected, message.getText());
    }

    @Test
    public void sendRegistrationDenialEmailSMTP() {
        ReflectionTestUtils.setField(sut, "mailEnabled", true);

        String email = "test@test.com";
        String subject = "Your registration was denied";
        String expected = "Hello!\nYour registration was denied by an administrator";

        sut.sendDenialEmail(email, "registration");

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(sender, times(1)).send(captor.capture());
        SimpleMailMessage message = captor.getValue();

        assertEquals(1, message.getTo().length);
        assertEquals(email, message.getTo()[0]);
        assertEquals(subject, message.getSubject());
        assertEquals(expected, message.getText());
    }

    @Test
    public void sendOrganizationChangeDenialEmailSMTP() {
        ReflectionTestUtils.setField(sut, "mailEnabled", true);

        String email = "test@test.com";
        String subject = "Your organization change was denied";
        String expected = "Hello!\nYour organization change was denied by an administrator";

        sut.sendDenialEmail(email, "organization change");

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(sender, times(1)).send(captor.capture());
        SimpleMailMessage message = captor.getValue();

        assertEquals(1, message.getTo().length);
        assertEquals(email, message.getTo()[0]);
        assertEquals(subject, message.getSubject());
        assertEquals(expected, message.getText());
    }

    @Test
    public void portEightyShortenURL() {
        when(env.getProperty("local.server.port")).thenReturn("80");
        ReflectionTestUtils.setField(sut, "mailEnabled", false);
        String id = "1234567890";
        String email = "test@test.com";

        String expected = "Sent to test@test.com\nVerify Email\nHello!\n" +
                "Someone has created an account for this email address. If this was not you then just ignore " +
                "this message. If it was you then click the link bellow.\n\n" +
                "http://localhost/api/visitor/verify_email?id=" + id + "\n";

        sut.sendVerificationEmail(id, email);

        assertEquals(expected, outContent.toString());
    }
}
