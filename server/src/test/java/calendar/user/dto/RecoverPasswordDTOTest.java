package calendar.user.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class RecoverPasswordDTOTest
 *
 * @author Axel Nilsson (axnion)
 */
public class RecoverPasswordDTOTest {
    private RecoverPasswordDTO sut;

    @Before
    public void setup() {
        sut = new RecoverPasswordDTO();
    }

    @Test
    public void defaultValues() {
        assertNull(sut.getUrlId());
        assertNull(sut.getPassword());
        assertNull(sut.getPasswordConfirmation());
    }

    @Test
    public void gettersAndSetters() {
        String urlId = "url_id";
        String password = "password";
        String passwordConf = "password_conf";

        sut.setUrlId(urlId);
        sut.setPassword(password);
        sut.setPasswordConfirmation(passwordConf);

        assertEquals(urlId, sut.getUrlId());
        assertEquals(password, sut.getPassword());
        assertEquals(passwordConf, sut.getPasswordConfirmation());
    }
}
