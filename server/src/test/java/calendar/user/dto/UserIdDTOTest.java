package calendar.user.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class UserIdDTOTest
 *
 * @author Axel Nilsson (axnion)
 */
public class UserIdDTOTest {
    private UserIdDTO sut;

    @Before
    public void setup() {
        sut = new UserIdDTO();
    }

    @Test
    public void defaultValues() {
        assertNull(sut.getId());
    }

    @Test
    public void gettersAndSetters() {
        sut.setId("1");
        assertEquals("1", sut.getId());
    }
}
