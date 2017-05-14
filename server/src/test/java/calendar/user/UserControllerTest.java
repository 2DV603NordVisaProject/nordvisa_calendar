package calendar.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @InjectMocks
    private UserController sut;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

    @Test
    public void getUserByIdGettingExistingUser() throws Exception {
        User user = new User();

        when(dao.getUserById("1")).thenReturn(user);

        mockMvc.perform(get("/api/user?id=1"))
                .andExpect(status().isOk());

        verify(dao, times(1)).getUserById("1");
    }

    @Test
    public void getUserByIdGettingNonexistentUser() throws Exception {
        when(dao.getUserById("1")).thenReturn(null);

        mockMvc.perform(get("/api/user?id=1"))
                .andExpect(status().isOk());

        verify(dao, times(1)).getUserById("1");
    }
}
