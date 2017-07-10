package calendar.token;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class TokenValidatorTest
 *
 * @author Axel Nilsson (axnion)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenValidatorTest {
    @Mock
    private TokenDAO dao;
    @InjectMocks
    private TokenValidator sut;

    @Test
    public void validToken() {
        String strToken = "token";
        int maxRequests = 10000;
        int requests = 999;

        Token token = mock(Token.class);

        when(dao.get(strToken)).thenReturn(token);
        when(token.getMaxRequests()).thenReturn(maxRequests);
        when(token.getRequests()).thenReturn(requests);

        boolean results = sut.validate(strToken);

        verify(token, times(1)).setRequests(requests + 1);
        verify(dao, times(1)).update(token);

        assertTrue(results);
    }

    @Test
    public void invalidTokenDoesNotExist() {
        String strToken = "token";
        int maxRequests = 10000;
        int requests = 999;

        Token token = mock(Token.class);

        when(dao.get(strToken)).thenReturn(null);
        when(token.getMaxRequests()).thenReturn(maxRequests);
        when(token.getRequests()).thenReturn(requests);

        boolean results = sut.validate(strToken);

        verify(token, never()).setRequests(requests + 1);
        verify(dao, never()).update(token);

        assertFalse(results);
    }

    @Test
    public void invalidTokenHasExceededMaxRequests() {
        String strToken = "token";
        int maxRequests = 10000;
        int requests = 10001;

        Token token = mock(Token.class);

        when(dao.get(strToken)).thenReturn(token);
        when(token.getMaxRequests()).thenReturn(maxRequests);
        when(token.getRequests()).thenReturn(requests);

        boolean results = sut.validate(strToken);

        verify(token, never()).setRequests(requests + 1);
        verify(dao, never()).update(token);

        assertFalse(results);
    }
}
