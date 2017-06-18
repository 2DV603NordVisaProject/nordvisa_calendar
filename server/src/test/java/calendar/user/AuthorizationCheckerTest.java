package calendar.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class AuthorizationCheckerTest
 *
 * @author Axel Nilsson (axnion)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorizationCheckerTest {
    @Mock
    private UserDAO dao;
    @Mock
    private CurrentUser currentUser;
    @InjectMocks
    private AuthorizationChecker sut;

    @Test
    public void existingCurrentUserCanManageTarget() {
        User curUser = mock(User.class);
        User target = mock(User.class);

        when(currentUser.getEmailAddress()).thenReturn("user@test.com");
        when(dao.getUserByEmail("user@test.com")).thenReturn(curUser);
        when(dao.getUserById("1")).thenReturn(target);
        when(curUser.canManage(target)).thenReturn(true);

        assertTrue(sut.currentUserCanManage("1"));
    }

    @Test
    public void existingCurrentUserCanNotManageTarget() {
        User curUser = mock(User.class);
        User target = mock(User.class);

        when(currentUser.getEmailAddress()).thenReturn("user@test.com");
        when(dao.getUserByEmail("user@test.com")).thenReturn(curUser);
        when(dao.getUserById("1")).thenReturn(target);
        when(curUser.canManage(target)).thenReturn(false);

        assertFalse(sut.currentUserCanManage("1"));
    }

    @Test
    public void nonExistingCurrentUserCanNotManageExistingTarget() {
        User target = mock(User.class);

        when(currentUser.getEmailAddress()).thenReturn("user@test.com");
        when(dao.getUserByEmail("user@test.com")).thenReturn(null);
        when(dao.getUserById("1")).thenReturn(target);

        assertFalse(sut.currentUserCanManage("1"));
    }

    @Test
    public void existingCurrentUserCanNotManageNonExistingTarget() {
        User curUser = mock(User.class);

        when(currentUser.getEmailAddress()).thenReturn("user@test.com");
        when(dao.getUserByEmail("user@test.com")).thenReturn(curUser);
        when(dao.getUserById("1")).thenReturn(null);
        when(curUser.canManage(null)).thenReturn(false);

        assertFalse(sut.currentUserCanManage("1"));
    }

    @Test
    public void getAllUserIdsTest() {
        User user1 = mock(User.class);
        User user2 = mock(User.class);

        when(user1.getId()).thenReturn("1");
        when(user2.getId()).thenReturn("2");

        ArrayList<User> allUsers = new ArrayList<>();
        allUsers.add(user1);
        allUsers.add(user2);

        when(dao.getAllUsers()).thenReturn(allUsers);

        List<String> result = sut.getAllUserIds();

        assertEquals(2, result.size());
        assertEquals("1", result.get(0));
        assertEquals("2", result.get(1));
    }
}
