package calendar.user;

import calendar.user.dto.ChangePasswordDTO;
import calendar.user.dto.UserDTO;
import calendar.user.dto.UserDetailsUpdateDTO;
import calendar.user.dto.UserIdDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class UserControllerTest
 *
 * @author Axel Nilsson (axnion)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Mock
    private UserDAO dao;
    @Mock
    private Email email;
    @Mock
    private UserInformationValidator validator;
    @Mock
    private CurrentUser currentUser;

    @InjectMocks
    private UserController sut;

    @Test
    public void getUserById() throws Exception {
        long longNumber = 12351;

        User user = mock(User.class);
        when(user.getId()).thenReturn("1");
        when(user.getEmail()).thenReturn("test@test.com");
        when(user.getRole()).thenReturn("USER");

        when(user.getCreatedAt()).thenReturn(longNumber);
        when(user.getUpdatedAt()).thenReturn(longNumber);

        ArrayList<String> events = new ArrayList<>();
        Organization org = mock(Organization.class);
        when(user.getEvents()).thenReturn(events);
        when(user.getOrganization()).thenReturn(org);

        when(dao.getUserById("1")).thenReturn(user);

        UserDTO dto = sut.getUserById("1");

        assertEquals(dto.getId(), "1");
        assertEquals(dto.getEmail(), "test@test.com");
        assertEquals(dto.getRole(), "USER");
        assertEquals(dto.getCreatedAt(), longNumber);
        assertEquals(dto.getUpdatedAt(), longNumber);
        assertEquals(dto.getEvents(), events);
        assertEquals(dto.getOrganization(), org);

        verify(dao, times(1)).getUserById("1");
    }

    @Test
    public void getUserByEmail() throws Exception {
        long longNumber = 12351;

        User user = mock(User.class);
        when(user.getId()).thenReturn("1");
        when(user.getEmail()).thenReturn("test@test.com");
        when(user.getRole()).thenReturn("USER");

        when(user.getCreatedAt()).thenReturn(longNumber);
        when(user.getUpdatedAt()).thenReturn(longNumber);

        ArrayList<String> events = new ArrayList<>();
        Organization org = mock(Organization.class);
        when(user.getEvents()).thenReturn(events);
        when(user.getOrganization()).thenReturn(org);

        when(dao.getUserByEmail("test@test.com")).thenReturn(user);
        UserDTO dto = sut.getUserByEmail("test@test.com");

        assertEquals(dto.getId(), "1");
        assertEquals(dto.getEmail(), "test@test.com");
        assertEquals(dto.getRole(), "USER");
        assertEquals(dto.getCreatedAt(), longNumber);
        assertEquals(dto.getUpdatedAt(), longNumber);
        assertEquals(dto.getEvents(), events);
        assertEquals(dto.getOrganization(), org);

        verify(dao, times(1)).getUserByEmail("test@test.com");
    }

    @Test
    public void getUsersByOrganization() throws Exception {
        sut.getUsersByOrganization("my_org");
        verify(dao, times(1)).getUsersByOrganization("my_org");
    }

    @Test
    public void getAllUsers() throws Exception {
        sut.getAllUsers();
        verify(dao, times(1)).getAllUsers();
    }

    @Test
    public void unregisterExistingUser() throws Exception {
        User actorMock = mock(User.class);
        User targetMock = mock(User.class);

        UserIdDTO idMock = mock(UserIdDTO.class);

        when(currentUser.getEmailAddres()).thenReturn("test@test.com");
        when(dao.getUserByEmail("test@test.com")).thenReturn(actorMock);
        when(dao.getUserById("1")).thenReturn(targetMock);
        when(actorMock.canManage(targetMock)).thenReturn(true);
        when(idMock.getId()).thenReturn("1");

        try {
            sut.unregister(idMock);
        }
        catch (Exception expt) {
            fail();
        }

        verify(dao, times(1)).delete("1");
    }

    @Test
    public void unregisterNonexistentUser() throws Exception {
        User actorMock = mock(User.class);
        User targetMock = mock(User.class);

        UserIdDTO idMock = mock(UserIdDTO.class);

        when(currentUser.getEmailAddres()).thenReturn("test@test.com");
        when(dao.getUserByEmail("test@test.com")).thenReturn(actorMock);
        when(dao.getUserById("1")).thenReturn(null);
        when(actorMock.canManage(targetMock)).thenReturn(true);
        when(idMock.getId()).thenReturn("1");

        try {
            sut.unregister(idMock);
            fail();
        }
        catch (Exception expt) {
            expt.getMessage();
        }

        verify(dao, never()).delete("1");
    }

    @Test
    public void unregisterCanNotManage() throws Exception {
        User actorMock = mock(User.class);
        User targetMock = mock(User.class);

        UserIdDTO idMock = mock(UserIdDTO.class);

        when(currentUser.getEmailAddres()).thenReturn("test@test.com");
        when(dao.getUserByEmail("test@test.com")).thenReturn(actorMock);
        when(dao.getUserById("1")).thenReturn(targetMock);
        when(actorMock.canManage(targetMock)).thenReturn(false);
        when(idMock.getId()).thenReturn("1");

        try {
            sut.unregister(idMock);
            fail();
        } catch (Exception expt) {
            expt.getMessage();
        }

        verify(dao, never()).delete("1");
    }

    @Test
    public void updateBothEmailAndOrganization() throws Exception {
        User userMock = mock(User.class);
        Organization orgMock = mock(Organization.class);
        AuthenticationLink validateEmailLink = mock(AuthenticationLink.class);
        UserDetailsUpdateDTO dtoMock = mock(UserDetailsUpdateDTO.class);

        doNothing().when(validator).validate(dtoMock);
        when(dao.getUserById("1")).thenReturn(userMock);
        when(userMock.getEmail()).thenReturn("test@test.com");
        when(userMock.getOrganization()).thenReturn(orgMock);
        when(userMock.getValidateEmailLink()).thenReturn(validateEmailLink);
        when(dtoMock.getId()).thenReturn("1");
        when(dtoMock.getEmail()).thenReturn("test2@test.com");
        when(dtoMock.getOrganization()).thenReturn("new_org");
        when(validateEmailLink.getUrl()).thenReturn("urlid");

        try {
            sut.updateUserDetails(dtoMock);
        }
        catch (Exception expt) {
            fail(expt.getMessage());
        }

        verify(validator).validate(dtoMock);
        verify(userMock, times(1)).setEmail("test2@test.com");
        verify(userMock, times(1)).
                setValidateEmailLink(any(AuthenticationLink.class));
        verify(email, times(1)).sendVerificationEmail("urlid");

        verify(orgMock).setChangePending("new_org");
        verify(dao).update(userMock);
    }

    @Test
    public void updateOnlyOrganization() throws Exception {
        User userMock = mock(User.class);
        Organization orgMock = mock(Organization.class);
        AuthenticationLink validateEmailLink = mock(AuthenticationLink.class);
        UserDetailsUpdateDTO dtoMock = mock(UserDetailsUpdateDTO.class);

        doNothing().when(validator).validate(dtoMock);
        when(dao.getUserById("1")).thenReturn(userMock);
        when(userMock.getEmail()).thenReturn("test@test.com");
        when(userMock.getOrganization()).thenReturn(orgMock);
        when(userMock.getValidateEmailLink()).thenReturn(validateEmailLink);
        when(dtoMock.getId()).thenReturn("1");
        when(dtoMock.getEmail()).thenReturn("test@test.com");
        when(dtoMock.getOrganization()).thenReturn("new_org");
        when(validateEmailLink.getUrl()).thenReturn("urlid");

        try {
            sut.updateUserDetails(dtoMock);
        }
        catch (Exception expt) {
            fail(expt.getMessage());
        }

        verify(validator).validate(dtoMock);
        verify(userMock, never()).setEmail(anyString());
        verify(userMock, never()).
                setValidateEmailLink(any(AuthenticationLink.class));
        verify(email, never()).sendVerificationEmail("urlid");

        verify(orgMock).setChangePending("new_org");
        verify(dao).update(userMock);
    }

    @Test
    public void updateUserDetailsValidationFailure() throws Exception {
        UserDetailsUpdateDTO dtoMock = mock(UserDetailsUpdateDTO.class);

        doThrow(new Exception()).when(validator).validate(dtoMock);

        try{
            sut.updateUserDetails(dtoMock);
            fail();
        }
        catch (Exception expt) {
            expt.getMessage();
        }
    }

    @Test
    public void changePasswordValid() throws Exception {
        User userMock = mock(User.class);
        ChangePasswordDTO dtoMock = mock(ChangePasswordDTO.class);

        doNothing().when(validator).validate(dtoMock);
        when(dtoMock.getId()).thenReturn("1");
        when(dtoMock.getPassword()).thenReturn("new_password");
        when(dao.getUserById("1")).thenReturn(userMock);

        try {
            sut.changePassword(dtoMock);
        }
        catch (Exception expt) {
            fail();
        }

        verify(userMock, times(1)).setPassword("new_password");
        verify(dao, times(1)).update(userMock);
    }

    @Test
    public void changePasswordInvalid() throws Exception {
        User userMock = mock(User.class);
        ChangePasswordDTO dtoMock = mock(ChangePasswordDTO.class);

        doThrow(new Exception()).when(validator).validate(dtoMock);
        when(dtoMock.getId()).thenReturn("1");
        when(dtoMock.getPassword()).thenReturn("new_password");
        when(dao.getUserById("1")).thenReturn(userMock);

        try {
            sut.changePassword(dtoMock);
            fail();
        }
        catch (Exception expt) {
            expt.getMessage();
        }

        verify(userMock, never()).setPassword("new_password");
        verify(dao, never()).update(userMock);
    }
}
