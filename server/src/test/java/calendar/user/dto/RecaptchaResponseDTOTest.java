package calendar.user.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class RecaptchaResponseDTOTest
 *
 * @author Axel Nilsson (axnion)
 */
public class RecaptchaResponseDTOTest {
    private RecaptchaResponseDTO sut;

    @Before
    public void setup() {
        sut = new RecaptchaResponseDTO();
    }

    @Test
    public void defaultValues() {
        assertFalse(sut.isSuccess());
        assertNull(sut.getErrorCodes());
    }

    @Test
    public void gettersAndSetters() {
        boolean success = true;
        String[] errorCodes = {"error1", "error2"};

        sut.setSuccess(success);
        sut.setErrorCodes(errorCodes);

        assertTrue(sut.isSuccess());
        assertEquals(2, sut.getErrorCodes().length);
        assertEquals(errorCodes[0], sut.getErrorCodes()[0]);
        assertEquals(errorCodes[1], sut.getErrorCodes()[1]);
    }
}
