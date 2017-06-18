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
    public void getCurrentUser() {
        String email = "test@test.com";

        User user = mock(User.class);
        Organization org = mock(Organization.class);
        when(user.getId()).thenReturn("1");
        when(user.getEmail()).thenReturn(email);
        when(user.getRole()).thenReturn("USER");
        when(user.getCreatedAt()).thenReturn(111L);
        when(user.getUpdatedAt()).thenReturn(111L);
        when(user.getOrganization()).thenReturn(org);

        when(currentUser.getEmailAddress()).thenReturn(email);
        when(dao.getUserByEmail(email)).thenReturn(user);

        UserDTO dto = sut.getCurrentUser();

        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getRole(), dto.getRole());
        assertEquals(user.getCreatedAt(), dto.getCreatedAt());
        assertEquals(user.getUpdatedAt(), dto.getUpdatedAt());
        assertEquals(user.getOrganization(), dto.getOrganization());
    }

    @Test
    public void getUserById() {
        long longNumber = 12351;

        User user = mock(User.class);
        when(user.getId()).thenReturn("1");
        when(user.getEmail()).thenReturn("test@test.com");
        when(user.getRole()).thenReturn("USER");

        when(user.getCreatedAt()).thenReturn(longNumber);
        when(user.getUpdatedAt()).thenReturn(longNumber);

        Organization org = mock(Organization.class);
        when(user.getOrganization()).thenReturn(org);

        when(dao.getUserById("1")).thenReturn(user);

        UserDTO dto = sut.getUserById("1");

        assertEquals(dto.getId(), "1");
        assertEquals(dto.getEmail(), "test@test.com");
        assertEquals(dto.getRole(), "USER");
        assertEquals(dto.getCreatedAt(), longNumber);
        assertEquals(dto.getUpdatedAt(), longNumber);
        assertEquals(dto.getOrganization(), org);

        verify(dao, times(1)).getUserById("1");
    }

    @Test
    public void getUserByEmail() {
        long longNumber = 12351;

        User user = mock(User.class);
        when(user.getId()).thenReturn("1");
        when(user.getEmail()).thenReturn("test@test.com");
        when(user.getRole()).thenReturn("USER");

        when(user.getCreatedAt()).thenReturn(longNumber);
        when(user.getUpdatedAt()).thenReturn(longNumber);

        Organization org = mock(Organization.class);
        when(user.getOrganization()).thenReturn(org);

        when(dao.getUserByEmail("test@test.com")).thenReturn(user);
        UserDTO dto = sut.getUserByEmail("test@test.com");

        assertEquals(dto.getId(), "1");
        assertEquals(dto.getEmail(), "test@test.com");
        assertEquals(dto.getRole(), "USER");
        assertEquals(dto.getCreatedAt(), longNumber);
        assertEquals(dto.getUpdatedAt(), longNumber);
        assertEquals(dto.getOrganization(), org);

        verify(dao, times(1)).getUserByEmail("test@test.com");
    }

    @Test
    public void getUsersByOrganization() {
        ArrayList<User> users = new ArrayList<>();
        User user = mock(User.class);
        Organization org = mock(Organization.class);
        when(user.getId()).thenReturn("1");
        when(user.getEmail()).thenReturn("test@test.com");
        when(user.getRole()).thenReturn("USER");
        when(user.getCreatedAt()).thenReturn(111L);
        when(user.getUpdatedAt()).thenReturn(111L);
        when(user.getOrganization()).thenReturn(org);
        users.add(user);
        when(dao.getUsersByOrganization("my_org")).thenReturn(users);

        ArrayList<UserDTO> results = sut.getUsersByOrganization("my_org");

        assertEquals(1, results.size());
        assertEquals("1", results.get(0).getId());
        verify(dao, times(1)).getUsersByOrganization("my_org");
    }

    @Test
    public void getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        User user = mock(User.class);
        Organization org = mock(Organization.class);
        when(user.getId()).thenReturn("1");
        when(user.getEmail()).thenReturn("test@test.com");
        when(user.getRole()).thenReturn("USER");
        when(user.getCreatedAt()).thenReturn(111L);
        when(user.getUpdatedAt()).thenReturn(111L);
        when(user.getOrganization()).thenReturn(org);
        users.add(user);
        when(dao.getAllUsers()).thenReturn(users);

        ArrayList<UserDTO> results = sut.getAllUsers();

        assertEquals(1, results.size());
        assertEquals("1", results.get(0).getId());
        verify(dao, times(1)).getAllUsers();
    }

    @Test
    public void unregisterExistingUser() throws Exception {
        User actorMock = mock(User.class);
        User targetMock = mock(User.class);

        UserIdDTO idMock = mock(UserIdDTO.class);

        when(currentUser.getEmailAddress()).thenReturn("test@test.com");
        when(dao.getUserByEmail("test@test.com")).thenReturn(actorMock);
        when(dao.getUserById("1")).thenReturn(targetMock);
        when(actorMock.canManage(targetMock)).thenReturn(true);
        when(idMock.getId()).thenReturn("1");

        sut.unregister(idMock);

        verify(dao, times(1)).delete("1");
    }

    @Test(expected = Exception.class)
    public void unregisterNonexistentUser() throws Exception {
        User actorMock = mock(User.class);
        User targetMock = mock(User.class);

        UserIdDTO idMock = mock(UserIdDTO.class);

        when(currentUser.getEmailAddress()).thenReturn("test@test.com");
        when(dao.getUserByEmail("test@test.com")).thenReturn(actorMock);
        when(dao.getUserById("1")).thenReturn(null);
        when(actorMock.canManage(targetMock)).thenReturn(true);
        when(idMock.getId()).thenReturn("1");

        sut.unregister(idMock);

        verify(dao, never()).delete("1");
    }

    @Test(expected = Exception.class)
    public void unregisterCanNotManage() throws Exception {
        User actorMock = mock(User.class);
        User targetMock = mock(User.class);

        UserIdDTO idMock = mock(UserIdDTO.class);

        when(currentUser.getEmailAddress()).thenReturn("test@test.com");
        when(dao.getUserByEmail("test@test.com")).thenReturn(actorMock);
        when(dao.getUserById("1")).thenReturn(targetMock);
        when(actorMock.canManage(targetMock)).thenReturn(false);
        when(idMock.getId()).thenReturn("1");

        sut.unregister(idMock);

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

        sut.updateUserDetails(dtoMock);

        verify(validator).validate(dtoMock);
        verify(userMock, times(1)).setEmailChange("test2@test.com");
        verify(userMock, times(1)).
                setValidateEmailLink(any(AuthenticationLink.class));
        verify(email, times(1)).sendVerificationEmail("urlid", "test2@test.com");

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

        sut.updateUserDetails(dtoMock);

        verify(validator).validate(dtoMock);
        verify(userMock, never()).setEmail(anyString());
        verify(userMock, never()).
                setValidateEmailLink(any(AuthenticationLink.class));
        verify(email, never()).sendVerificationEmail("urlid", "test@test.com");

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
