package calendar.user;

import calendar.user.dto.UserDetailsUpdateDTO;
import calendar.user.dto.UserIdDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

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
        sut.getUserById("1");
        verify(dao, times(1)).getUserById("1");
    }

    @Test
    public void getUserByEmail() throws Exception {
        sut.getUserByEmail("test@test.com");
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
        UserDetailsUpdateDTO dtoMock = mock(UserDetailsUpdateDTO.class);

        doNothing().when(validator).validate(dtoMock);
        when(dao.getUserById("1")).thenReturn(userMock);
        when(userMock.getEmail()).thenReturn("test@test.com");
        when(userMock.getOrganization()).thenReturn(orgMock);
        when(dtoMock.getId()).thenReturn("1");
        when(dtoMock.getEmail()).thenReturn("test2@test.com");

        try {
            sut.updateUserDetails(dtoMock);
        }
        catch (Exception expt) {
//            fail(expt.getMessage());
        }

        verify(validator).validate(dtoMock);
//        verify(userMock, times(1)).setEmail("test2@test.com");
//        verify(userMock, times(1)).
//                setValidateEmailLink(any(AuthenticationLink.class));
//        verify(email, times(1)).sendPasswordResetEmail(anyString());

//        verify(orgMock).setChangePending("test2@test.com");
//        verify(dao).update(userMock);
    }

    @Test
    public void updateUserDetailsValidationFailure() {

    }
}
