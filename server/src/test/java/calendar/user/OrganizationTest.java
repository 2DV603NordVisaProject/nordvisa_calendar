package calendar.user;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class OrganizationTest
 *
 * @author Axel Nilsson (axnion)
 */
public class OrganizationTest {
    Organization sut;

    @Before
    public void setup() {
        sut = new Organization();
    }

    @Test
    public void checkDefaultValues() {
        assertEquals("", sut.getName());
        assertFalse(sut.isApproved());
        assertEquals("_", sut.getChangePending());
    }

    @Test
    public void settersAndGettersTest() {
        sut.setName("new_name");
        sut.setApproved(true);
        sut.setChangePending("new_changePending");

        assertEquals("new_name", sut.getName());
        assertTrue(sut.isApproved());
        assertEquals("new_changePending", sut.getChangePending());
    }

    @Test
    public void compareTwoEqualOrganization() {
        Organization other = mock(Organization.class);
        when(other.getName()).thenReturn("");
        assertTrue(sut.compare(other));
    }

    @Test
    public void compareDifferentOrganizations() {
        Organization other = mock(Organization.class);
        when(other.getName()).thenReturn("other_name");
        assertFalse(sut.compare(other));
    }
}
