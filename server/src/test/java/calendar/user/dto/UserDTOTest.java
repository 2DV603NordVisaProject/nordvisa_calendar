package calendar.user.dto;

import calendar.user.Organization;
import calendar.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class UserDTOTest
 *
 * @author Axel Nilsson (axnion)
 */
public class UserDTOTest {
    private UserDTO sut;
    private Organization organizationMock;

    @Before
    public void setup() {
        organizationMock = mock(Organization.class);

        User userMock = mock(User.class);
        when(userMock.getId()).thenReturn("1");
        when(userMock.getEmail()).thenReturn("test@test.com");
        when(userMock.getRole()).thenReturn("USER");
        when(userMock.getCreatedAt()).thenReturn(123L);
        when(userMock.getUpdatedAt()).thenReturn(123L);
        when(userMock.getOrganization()).thenReturn(organizationMock);

        sut = new UserDTO(userMock);
    }

    @Test
    public void defaultValues() {
        assertEquals("1", sut.getId());
        assertEquals("test@test.com", sut.getEmail());
        assertEquals("USER", sut.getRole());
        assertEquals(123, sut.getCreatedAt());
        assertEquals(123, sut.getUpdatedAt());
        assertEquals(organizationMock, sut.getOrganization());
    }
}
