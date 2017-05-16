package calendar.user;

import calendar.user.dto.UserIdDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class AdminControllerTest
 *
 * @author Axel Nilsson (axnion)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminControllerTest {
    @Mock
    private UserDAO dao;
    @Mock
    private CurrentUser currentUser;

    @InjectMocks
    private AdminController sut;

    @Test
    public void makeUserValid() throws Exception {
        User actorMock = mock(User.class);
        User targetMock = mock(User.class);
        UserIdDTO dtoMock = mock(UserIdDTO.class);

        when(currentUser.getEmailAddres()).thenReturn("test@test.com");
        when(dao.getUserByEmail("test@test.com")).thenReturn(actorMock);
        when(dao.getUserById("1")).thenReturn(targetMock);
        when(dtoMock.getId()).thenReturn("1");
        when(actorMock.canChangeRoleTo(targetMock, "USER")).thenReturn(true);

        try {
            sut.makeUser(dtoMock);
        }
        catch (Exception expt) {
            fail(expt.getMessage());
        }

        verify(targetMock, times(1)).setRole("USER");
        verify(dao, times(1)).update(targetMock);
    }

    @Test
    public void makeUserInvalid() throws Exception {
        User actorMock = mock(User.class);
        User targetMock = mock(User.class);
        UserIdDTO dtoMock = mock(UserIdDTO.class);

        when(currentUser.getEmailAddres()).thenReturn("test@test.com");
        when(dao.getUserByEmail("test@test.com")).thenReturn(actorMock);
        when(dao.getUserById("1")).thenReturn(targetMock);
        when(dtoMock.getId()).thenReturn("1");
        when(actorMock.canChangeRoleTo(targetMock, "USER")).thenReturn(false);

        try {
            sut.makeUser(dtoMock);
        }
        catch (Exception expt) {
            fail(expt.getMessage());
        }

        verify(targetMock, never()).setRole("USER");
        verify(dao, never()).update(targetMock);
    }

    @Test
    public void makeAdminValid() throws Exception {
        User actorMock = mock(User.class);
        User targetMock = mock(User.class);
        UserIdDTO dtoMock = mock(UserIdDTO.class);

        when(currentUser.getEmailAddres()).thenReturn("test@test.com");
        when(dao.getUserByEmail("test@test.com")).thenReturn(actorMock);
        when(dao.getUserById("1")).thenReturn(targetMock);
        when(dtoMock.getId()).thenReturn("1");
        when(actorMock.canChangeRoleTo(targetMock, "ADMIN")).thenReturn(true);

        try {
            sut.makeAdmin(dtoMock);
        }
        catch (Exception expt) {
            fail(expt.getMessage());
        }

        verify(targetMock, times(1)).setRole("ADMIN");
        verify(dao, times(1)).update(targetMock);
    }

    @Test
    public void makeAdminInvalid() throws Exception {
        User actorMock = mock(User.class);
        User targetMock = mock(User.class);
        UserIdDTO dtoMock = mock(UserIdDTO.class);

        when(currentUser.getEmailAddres()).thenReturn("test@test.com");
        when(dao.getUserByEmail("test@test.com")).thenReturn(actorMock);
        when(dao.getUserById("1")).thenReturn(targetMock);
        when(dtoMock.getId()).thenReturn("1");
        when(actorMock.canChangeRoleTo(targetMock, "ADMIN")).thenReturn(false);

        try {
            sut.makeAdmin(dtoMock);
        }
        catch (Exception expt) {
            fail(expt.getMessage());
        }

        verify(targetMock, never()).setRole(anyString());
        verify(dao, never()).update(any(User.class));
    }
}
