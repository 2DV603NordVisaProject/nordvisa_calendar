package calendar.user.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class UserDetailsUpdateDTOTest
 *
 * @author Axel Nilsson (axnion)
 */
public class UserDetailsUpdateDTOTest {
    private UserDetailsUpdateDTO sut;

    @Before
    public void setup() {
        sut = new UserDetailsUpdateDTO();
    }

    @Test
    public void defaultValues() {
        assertNull(sut.getId());
        assertNull(sut.getEmail());
        assertNull(sut.getOrganization());
    }

    @Test
    public void gettersAndSetters() {
        String id = "id";
        String email = "test@test.com";
        String organization = "org";

        sut.setId(id);
        sut.setEmail(email);
        sut.setOrganization(organization);

        assertEquals(id, sut.getId());
        assertEquals(email, sut.getEmail());
        assertEquals(organization, sut.getOrganization());
    }
}
