package calendar.user.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class ChangePasswordDTOTest
 *
 * @author Axel Nilsson (axnion)
 */
public class ChangePasswordDTOTest {
    private ChangePasswordDTO sut;

    @Before
    public void setup() {
        sut = new ChangePasswordDTO();
    }

    @Test
    public void defaultValues() {
        assertNull(sut.getId());
        assertNull(sut.getOldPassword());
        assertNull(sut.getPassword());
        assertNull(sut.getPasswordConfirmation());
    }

    @Test
    public void gettersAndSetters() {
        String id = "my_id";
        String oldPassword = "old_password";
        String password = "password";
        String passwordConf = "password_conf";

        sut.setId(id);
        sut.setOldPassword(oldPassword);
        sut.setPassword(password);
        sut.setPasswordConfirmation(passwordConf);

        assertEquals(sut.getId(), id);
        assertEquals(sut.getOldPassword(), oldPassword);
        assertEquals(sut.getPassword(), password);
        assertEquals(sut.getPasswordConfirmation(), passwordConf);
    }
}
