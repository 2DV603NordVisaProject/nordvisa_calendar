package calendar.user.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class PasswordRecoverRequestDTOTest
 *
 * @author Axel Nilsson (axnion)
 */
public class PasswordRecoverRequestDTOTest {
    private PasswordRecoveryRequestDTO sut;

    @Before
    public void setup() {
        sut = new PasswordRecoveryRequestDTO();
    }

    @Test
    public void defaultValues() {
        assertNull(sut.getEmail());
    }

    @Test
    public void gettersAndSetters() {
        String email = "test@test.com";
        sut.setEmail(email);
        assertEquals(email, sut.getEmail());
    }
}
