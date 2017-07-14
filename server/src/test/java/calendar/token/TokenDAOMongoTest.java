package calendar.token;

import calendar.databaseConnections.MongoDBClient;
import org.bson.types.ObjectId;
import org.jongo.FindOne;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.Update;
import org.junit.Before;
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
 * Class TokenDAOMongoTest
 *
 * @author Axel Nilsson (axnion)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenDAOMongoTest {
    @Mock
    private MongoDBClient client;

    @InjectMocks
    private TokenDAOMongo sut;

    private MongoCollection collection;

    @Before
    public void setup() {
        Jongo jongo = mock(Jongo.class);
        collection = mock(MongoCollection.class);
        when(client.getClient()).thenReturn(jongo);
        when(jongo.getCollection("tokens")).thenReturn(collection);
    }

    @Test
    public void addTest() {
        Token token = mock(Token.class);
        sut.add(token);
        verify(collection, times(1)).insert(token);
    }

    @Test
    public void getTest() {
        String strToken = "123456789";
        FindOne findOneMock = mock(FindOne.class);
        Token token = mock(Token.class);

        when(collection.findOne(anyString())).thenReturn(findOneMock);
        when(findOneMock.as(Token.class)).thenReturn(token);

        Token results = sut.get(strToken);

        assertEquals(token, results);
        verify(collection, times(1)).findOne("{token: \"" + strToken + "\"}");
    }

    @Test
    public void updateTest() {
        String id = "507f1f77bcf86cd799439011";
        Token token = mock(Token.class);
        Update update = mock(Update.class);

        when(token.getId()).thenReturn(id);
        when(collection.update(any(ObjectId.class))).thenReturn(update);

        sut.update(token);

        ArgumentCaptor<ObjectId> captor = ArgumentCaptor.forClass(ObjectId.class);
        verify(collection, times(1)).update(captor.capture());
        assertEquals(0, captor.getValue().compareTo(new ObjectId(id)));

        verify(update, times(1)).with(token);
    }
}
