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
}
