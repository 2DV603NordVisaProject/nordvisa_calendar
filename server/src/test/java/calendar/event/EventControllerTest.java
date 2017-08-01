package calendar.event;

import calendar.Application;
import calendar.token.TokenValidator;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class EventControllerTest {

    @Mock
    private EventDAO dao;

    @Mock
    private Event event;

    @Mock
    private TokenValidator tokenValidator;

    @Mock
    private EventLocation eventLocation;

    @Mock
    private EventLocationCoordinates eventLocationCoordinates;

    @InjectMocks
    private EventController sut;

    @Test
    public void getEvents() throws Exception {
        List<Event> testEvents = Arrays.asList(event);

        when(event.getId()).thenReturn("5925b4aac274ad1921de196e");
        when(event.getName()).thenReturn("Event name");
        when(event.getDescription()).thenReturn("Event description");

        when(tokenValidator.validate("dashboard")).thenReturn(true);

        when(dao.getEvent(new ObjectId("5925b4aac274ad1921de196e"))).thenReturn(testEvents);

        List<Event> events = sut.getEvents("5925b4aac274ad1921de196e",
                null, null, null, null,
                null, null, null, "dashboard");

        assertEquals(events.get(0).getId(), "5925b4aac274ad1921de196e");
        assertEquals(events.get(0).getName(), "Event name");
        assertEquals(events.get(0).getDescription(), "Event description");

        verify(dao, times(1)).getEvent(new ObjectId("5925b4aac274ad1921de196e"));
    }

    @Test
    public void getAll() throws Exception {
        sut.getAll();
        verify(dao, times(1)).getEvents();
    }

    @Test
    public void getGeoCodedLocation() throws Exception {
        GeoCodeHandler geo = mock(GeoCodeHandler.class, CALLS_REAL_METHODS);

        eventLocation = geo.getGeoCodedLocation("Drottninggatan 100, Stockholm");
        eventLocationCoordinates = eventLocation.getCoordinates();

        assertEquals(eventLocation.getAddress(), "Drottninggatan 100, Stockholm");
        assertEquals(eventLocation.getParsedAddress(), "Drottninggatan 100, 111 60 Stockholm, Sweden");
        assertEquals(eventLocation.getPostalCode(), "11160");
        assertEquals(eventLocation.getCity(), "Stockholm");
        assertEquals(eventLocation.getCounty(), "Stockholms län");
        assertEquals(eventLocation.getCountry(), "SE");
        assertEquals(eventLocationCoordinates.getCoordinates(), Arrays.asList(18.0573795, 59.3380546));

    }

}
