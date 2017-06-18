package calendar.user;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class AuthenticationLinkTest
 *
 * @author Axel Nilsson (axnion)
 */
public class AuthenticationLinkTest {
    private AuthenticationLink sut;

    @Test
    public void testingJsonConstructor() {
        sut = new AuthenticationLink();
        assertNotNull(sut);

    }

    @Test
    public void getterTest() {
        String url = "http://someurl.com";
        long timestamp = 123456789;
        sut = new AuthenticationLink(url, timestamp);
        assertEquals(url, sut.getUrl());
        assertEquals(timestamp, sut.getTimestamp());
    }

    @Test
    public void linkHasNotExpired() {
        String url = "http://someurl.com";
        long timestamp = DateTime.now().minusHours(23).getMillis();
        sut = new AuthenticationLink(url, timestamp);
        assertFalse(sut.hasExpired());
    }

    @Test
    public void linkHasExpired() {
        String url = "http://someurl.com";
        long timestamp = DateTime.now().minusHours(25).getMillis();
        sut = new AuthenticationLink(url, timestamp);
        assertTrue(sut.hasExpired());
    }
}
