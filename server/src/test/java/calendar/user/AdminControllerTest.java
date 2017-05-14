package calendar.user;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

}
