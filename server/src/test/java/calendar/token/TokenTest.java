package calendar.token;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class TokenTest
 *
 * @author Axel Nilsson (axnion)
 */
public class TokenTest {
    private Token sut;

    @Before
    public void setup() {
        sut = new Token("token");
    }

    @Test
    public void defaultValues() {
        assertNull(sut.getId());
        assertEquals("token", sut.getToken());
        assertEquals(0, sut.getRequests());
        assertEquals(10000, sut.getMaxRequests());
        assertTrue(new Interval(
                DateTime.now().minusSeconds(5), DateTime.now().plusSeconds(5)).contains(sut.getCreatedAt()
        ));
        assertTrue(DateTime.now().plusYears(1).isAfter(sut.getValidUntil()));
        assertTrue(DateTime.now().plusMonths(11).isBefore(sut.getValidUntil()));
    }

    @Test
    public void gettersAndSetters() {
        String id = "my_test_id";
        String token = "new_token";
        int requests = 50;
        int maxRequests = 5000;
        long createdAt = 100;
        long validUntil = 1000;

        sut.setId(id);
        sut.setToken(token);
        sut.setRequests(requests);
        sut.setMaxRequests(maxRequests);
        sut.setCreatedAt(createdAt);
        sut.setValidUntil(validUntil);

        assertEquals(id, sut.getId());
        assertEquals(token, sut.getToken());
        assertEquals(requests, sut.getRequests());
        assertEquals(maxRequests, sut.getMaxRequests());
        assertEquals(createdAt, sut.getCreatedAt());
        assertEquals(validUntil, sut.getValidUntil());
    }
}
