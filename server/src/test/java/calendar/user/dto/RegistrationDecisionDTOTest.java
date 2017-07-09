package calendar.user.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class RegistrationDecisionDTOTest
 *
 * @author Axel Nilsson (axnion)
 */
public class RegistrationDecisionDTOTest {
    private RegistrationDecisionDTO sut;

    @Before
    public void setup() {
        sut = new RegistrationDecisionDTO();
    }

    @Test
    public void defaultValues() {
        assertNull(sut.getId());
        assertFalse(sut.isApproved());
    }

    @Test
    public void gettersAndSetters() {
        String id = "id";

        sut.setId(id);
        sut.setApproved(true);

        assertEquals(id, sut.getId());
        assertTrue(sut.isApproved());
    }
}
