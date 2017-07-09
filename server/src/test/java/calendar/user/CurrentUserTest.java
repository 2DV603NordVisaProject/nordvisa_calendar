package calendar.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class CurrentUserTest
 *
 * @author Axel Nilsson (axnion)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrentUserTest {
    @Autowired
    private CurrentUser sut;

    @Test
    public void getEmailAddress() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("test@test.com");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        assertEquals("test@test.com", sut.getEmailAddress());
    }
}
