package calendar.user.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class RegistrationDTOTest
 *
 * @author Axel Nilsson (axnion)
 */
public class RegistrationDTOTest {
    private RegistrationDTO sut;

    @Before
    public void setup() {
        sut = new RegistrationDTO();
    }

    @Test
    public void defaultValues() {
        assertNull(sut.getEmail());
        assertNull(sut.getPassword());
        assertNull(sut.getPasswordConfirmation());
        assertNull(sut.getOrganization());
        assertNull(sut.getRecaptcha());
    }

    @Test
    public void gettersAndSetters() {
        String email = "test@test.com";
        String password = "password";
        String passwordConf = "password_conf";
        String org = "organization";
        String recaptcha = "secret";;

        sut.setEmail(email);
        sut.setPassword(password);
        sut.setPasswordConfirmation(passwordConf);
        sut.setOrganization(org);
        sut.setgRecaptchaResponse(recaptcha);

        assertEquals(email, sut.getEmail());
        assertEquals(password, sut.getPassword());
        assertEquals(passwordConf, sut.getPasswordConfirmation());
        assertEquals(org, sut.getOrganization());
        assertEquals(recaptcha, sut.getRecaptcha());
    }
}

