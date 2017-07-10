package calendar.token;

import calendar.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class TokenControllerTest
 *
 * @author Axel Nilsson (axnion)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenControllerTest {
    @Mock
    private TokenDAO dao;
    @Mock
    private Util util;

    @InjectMocks
    private TokenController sut;

    @Test
    public void getTokenTest() {
        String hex = "20EB69D5EF78F9CABE3C";
        when(util.getRandomHexString(20)).thenReturn(hex);

        TokenDTO dto = sut.getToken();
        assertEquals(hex, dto.getToken());

        ArgumentCaptor<Token> captor = ArgumentCaptor.forClass(Token.class);
        verify(dao, times(1)).add(captor.capture());
        Token capturedToken = captor.getValue();
        assertEquals(hex, capturedToken.getToken());
    }
}
