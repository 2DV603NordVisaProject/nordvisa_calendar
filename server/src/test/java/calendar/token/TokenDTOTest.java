package calendar.token;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class TokenDTOTest
 *
 * @author Axel Nilsson (axnion)
 */
public class TokenDTOTest {
    private TokenDTO sut;

    @Before
    public void setup() {
        sut = new TokenDTO();
    }

    @Test
    public void defaultValues() {
        assertNull(sut.getToken());
    }

    @Test
    public void gettersAndSetters() {
        String token = "new_token";
        sut.setToken(token);
        assertEquals(token, sut.getToken());
    }
}
