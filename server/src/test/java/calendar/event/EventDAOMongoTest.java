package calendar.event;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import calendar.databaseConnections.MongoDBClient;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.bson.types.ObjectId;
import org.jongo.Find;
import org.jongo.FindOne;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Class EventDAOMongoTest
 * 
 * @author Axel Nilsson (axnion)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EventDAOMongoTest {
    @Mock
    private MongoDBClient db;

    @InjectMocks
    private EventDAOMongo sut;

    private Jongo client;
    private MongoCollection collection;

    @Before
    public void setup() {
        client = mock(Jongo.class);
        collection = mock(MongoCollection.class);
        when(db.getClient()).thenReturn(client);
        when(client.getCollection("events")).thenReturn(collection);
    }

    @Test
    public void getEventValidId() {
        ObjectId id = new ObjectId();
        FindOne findOne = mock(FindOne.class);
        Event event = mock(Event.class);
        
        when(collection.findOne(id)).thenReturn(findOne);
        when(findOne.as(Event.class)).thenReturn(event);

        List<Event> res = sut.getEvent(id);

        assertEquals(1, res.size());
        assertEquals(event, res.get(0));

        verify(collection).findOne(id);
    }

    @Test
    public void getEventInvalidId() {
        ObjectId id = new ObjectId();
        FindOne findOne = mock(FindOne.class);
        Event event = null;
        
        when(collection.findOne(id)).thenReturn(findOne);
        when(findOne.as(Event.class)).thenReturn(event);

        List<Event> res = sut.getEvent(id);

        assertEquals(1, res.size());
        assertEquals(event, res.get(0));

        verify(collection).findOne(id);
    }

    @Test
    public void getEventsTest() {
        Find find = mock(Find.class);
        Event event1 = mock(Event.class);
        Event event2 = mock(Event.class);
        MongoCursor cursor = mock(MongoCursor.class);
        

        when(collection.find()).thenReturn(find);
        when(find.as(Event.class)).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true, true, false);
        when(cursor.next()).thenReturn(event1, event2);

        List<Event> res = sut.getEvents();

        assertEquals(2, res.size());
        assertEquals(event1, res.get(0));
        assertEquals(event2, res.get(1));
    }

    @Test
    public void getEventsFromCountyTest() {
        Find find = mock(Find.class);
        Event event1 = mock(Event.class);
        Event event2 = mock(Event.class);
        MongoCursor cursor = mock(MongoCursor.class);

        when(collection.find(anyString(), anyString())).thenReturn(find);
        when(find.as(Event.class)).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true, true, false);
        when(cursor.next()).thenReturn(event1, event2);

        List<Event> res = sut.getEventsFromCounty("sweden");

        assertEquals(2, res.size());
        assertEquals(event1, res.get(0));
        assertEquals(event2, res.get(1));

        verify(collection).find("{ location.county: # }", "sweden");
    }

    @Test
    public void getEventsFromCountryTest() {
        Find find = mock(Find.class);
        Event event1 = mock(Event.class);
        Event event2 = mock(Event.class);
        MongoCursor cursor = mock(MongoCursor.class);

        when(collection.find(anyString(), anyString())).thenReturn(find);
        when(find.as(Event.class)).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true, true, false);
        when(cursor.next()).thenReturn(event1, event2);

        List<Event> res = sut.getEventsFromCountry("sweden");

        assertEquals(2, res.size());
        assertEquals(event1, res.get(0));
        assertEquals(event2, res.get(1));

        verify(collection).find("{ location.country: # }", "sweden");
    }

    @Test
    public void getEventsWithinDatesTest() {
        Find find = mock(Find.class);
        Event event1 = mock(Event.class);
        Event event2 = mock(Event.class);
        MongoCursor cursor = mock(MongoCursor.class);

        when(collection.find(anyString(), anyDouble(), anyDouble(), anyDouble())).thenReturn(find);
        when(find.as(Event.class)).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true, true, false);
        when(cursor.next()).thenReturn(event1, event2);

        List<Event> res = sut.getEventsWithinRadius(150.0, 120.0, 500.0);

        assertEquals(2, res.size());
        assertEquals(event1, res.get(0));
        assertEquals(event2, res.get(1));

        verify(collection).find("{ location.coordinates: { " +
                "$near: { " +
                "$geometry: { " +
                "type: 'Point', " +
                "coordinates: [ #, # ] }, " +
                "$maxDistance: # } } }", 150.0, 120.0, 500000.0);
    }

    @Test
    public void getEventsByUserIdTest() {
        Find find = mock(Find.class);
        Event event1 = mock(Event.class);
        Event event2 = mock(Event.class);
        MongoCursor cursor = mock(MongoCursor.class);

        when(collection.find(anyString(), anyString())).thenReturn(find);
        when(find.as(Event.class)).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true, true, false);
        when(cursor.next()).thenReturn(event1, event2);

        List<Event> res = sut.getEventsByUserId("id");

        assertEquals(2, res.size());
        assertEquals(event1, res.get(0));
        assertEquals(event2, res.get(1));

        verify(collection).find("{createdBy: #}", "id");
    }

    @Test
    public void createEventTest() {
        Event event = mock(Event.class);

        Event res = sut.createEvent(event);

        verify(collection).ensureIndex("{ location.coordinates: '2dsphere' }");
        verify(collection).insert(eq(event));
        assertEquals(event, res);
    }
}
