package calendar.user;

import calendar.databaseConnections.MongoDBClient;
import org.bson.types.ObjectId;
import org.jongo.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class UserDAOMongoTest
 *
 * @author Axel Nilsson (axnion)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDAOMongoTest {
    @Mock
    private MongoDBClient db;

    @InjectMocks
    private UserDAOMongo sut;

    @Test
    public void getExistingUserByValidId() {
        Jongo client = mock(Jongo.class);
        MongoCollection collection = mock(MongoCollection.class);
        FindOne findOne = mock(FindOne.class);
        User userMock = mock(User.class);

        when(db.getClient()).thenReturn(client);
        when(client.getCollection("users")).thenReturn(collection);
        when(collection.findOne(any(ObjectId.class))).thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(userMock);

        User user = sut.getUserById("507f1f77bcf86cd799439011");

        verify(db, times(1)).getClient();
        verify(client, timeout(1)).getCollection("users");
        verify(collection, times(1)).findOne(any(ObjectId.class));
        verify(findOne, times(1)).as(User.class);
        assertEquals(user, userMock);
    }

    @Test
    public void getNonExistingUserWithValidId() {
        Jongo client = mock(Jongo.class);
        MongoCollection collection = mock(MongoCollection.class);
        FindOne findOne = mock(FindOne.class);

        when(db.getClient()).thenReturn(client);
        when(client.getCollection("users")).thenReturn(collection);
        when(collection.findOne(any(ObjectId.class))).thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(null);

        User user = sut.getUserById("507f1f77bcf86cd799439011");

        verify(db, times(1)).getClient();
        verify(client, timeout(1)).getCollection("users");
        verify(collection, times(1)).findOne(any(ObjectId.class));
        verify(findOne, times(1)).as(User.class);
        assertNull(user);
    }

    @Test
    public void getUserWithInvalidId() {
        Jongo client = mock(Jongo.class);
        MongoCollection collection = mock(MongoCollection.class);
        FindOne findOne = mock(FindOne.class);
        User userMock = mock(User.class);

        when(db.getClient()).thenReturn(client);
        when(client.getCollection("users")).thenReturn(collection);
        when(collection.findOne(any(ObjectId.class))).thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(userMock);

        try {
            User user = sut.getUserById("ljkjjkkjjljkjk");
            fail();
        } catch (IllegalArgumentException expt) {
            expt.getMessage();
        }
    }

    @Test
    public void getExistingUserByEmail() {
        Jongo client = mock(Jongo.class);
        MongoCollection collection = mock(MongoCollection.class);
        FindOne findOne = mock(FindOne.class);
        User userMock = mock(User.class);

        when(db.getClient()).thenReturn(client);
        when(client.getCollection("users")).thenReturn(collection);
        when(collection.findOne("{email: \"test@test.com\"}")).thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(userMock);

        User user = sut.getUserByEmail("test@test.com");

        assertEquals(userMock, user);
    }

    @Test
    public void getNonExistingUserByEmail() {
        Jongo client = mock(Jongo.class);
        MongoCollection collection = mock(MongoCollection.class);
        FindOne findOne = mock(FindOne.class);

        when(db.getClient()).thenReturn(client);
        when(client.getCollection("users")).thenReturn(collection);
        when(collection.findOne("{email: \"test@test.com\"}")).thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(null);

        User user = sut.getUserByEmail("test@test.com");

        assertNull(user);
    }

    @Test
    public void getExistingUserByPasswordRecoveryLink() {
        Jongo client = mock(Jongo.class);
        MongoCollection collection = mock(MongoCollection.class);
        FindOne findOne = mock(FindOne.class);
        User userMock = mock(User.class);

        when(db.getClient()).thenReturn(client);
        when(client.getCollection("users")).thenReturn(collection);
        when(collection.findOne("{resetPasswordLink.url: \"random_string_12345\"}"))
                .thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(userMock);

        User user = sut.getUserByPasswordRecoveryLink("random_string_12345");

        assertEquals(userMock, user);
    }

    @Test
    public void getNonExistingUserByPasswordRecoveryLink() {

        Jongo client = mock(Jongo.class);
        MongoCollection collection = mock(MongoCollection.class);
        FindOne findOne = mock(FindOne.class);
        User userMock = mock(User.class);

        when(db.getClient()).thenReturn(client);
        when(client.getCollection("users")).thenReturn(collection);
        when(collection.findOne("{resetPasswordLink.url: \"random_string_12345\"}"))
                .thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(null);

        User user = sut.getUserByPasswordRecoveryLink("random_string_12345");

        assertNull(user);
    }

    @Test
    public void getExistingUserByEmailVerificationLink() {
        Jongo client = mock(Jongo.class);
        MongoCollection collection = mock(MongoCollection.class);
        FindOne findOne = mock(FindOne.class);
        User userMock = mock(User.class);

        when(db.getClient()).thenReturn(client);
        when(client.getCollection("users")).thenReturn(collection);
        when(collection.findOne("{validateEmailLink.url: \"random_string_12345\"}"))
                .thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(userMock);

        User user = sut.getUserByEmailVerificationLink("random_string_12345");

        assertEquals(userMock, user);
    }

    @Test
    public void getNonExistingUserByEmailVerificationLink() {
        Jongo client = mock(Jongo.class);
        MongoCollection collection = mock(MongoCollection.class);
        FindOne findOne = mock(FindOne.class);

        when(db.getClient()).thenReturn(client);
        when(client.getCollection("users")).thenReturn(collection);
        when(collection.findOne("{validateEmailLink.url: \"random_string_12345\"}"))
                .thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(null);

        User user = sut.getUserByEmailVerificationLink("random_string_12345");

        assertNull(user);
    }

    @Test
    public void getUsersByExistingOrganization() {
        Jongo client = mock(Jongo.class);
        MongoCollection collection = mock(MongoCollection.class);
        Find find = mock(Find.class);
        MongoCursor<User> cursor = mock(MongoCursor.class);
        User userMock1 = mock(User.class);
        User userMock2 = mock(User.class);

        when(db.getClient()).thenReturn(client);
        when(client.getCollection("users")).thenReturn(collection);
        when(collection.find("{organization.name: \"org\"}")).thenReturn(find);
        when(find.as(User.class)).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true, true, false);
        when(cursor.next()).thenReturn(userMock1, userMock2);

        ArrayList<User> users = sut.getUsersByOrganization("org");

        assertEquals(2, users.size());
        assertEquals(userMock1, users.get(0));
        assertEquals(userMock2, users.get(1));
    }

    @Test
    public void getUsersByNonExistingOrganization() {
        Jongo client = mock(Jongo.class);
        MongoCollection collection = mock(MongoCollection.class);
        Find find = mock(Find.class);
        MongoCursor<User> cursor = mock(MongoCursor.class);

        when(db.getClient()).thenReturn(client);
        when(client.getCollection("users")).thenReturn(collection);
        when(collection.find("{organization.name: \"org\"}")).thenReturn(find);
        when(find.as(User.class)).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(false);

        ArrayList<User> users = sut.getUsersByOrganization("org");

        assertEquals(0, users.size());
    }

    @Test
    public void getAllUsers() {
        Jongo client = mock(Jongo.class);
        MongoCollection collection = mock(MongoCollection.class);
        Find find = mock(Find.class);
        MongoCursor<User> cursor = mock(MongoCursor.class);
        User userMock1 = mock(User.class);
        User userMock2 = mock(User.class);

        when(db.getClient()).thenReturn(client);
        when(client.getCollection("users")).thenReturn(collection);
        when(collection.find("{}")).thenReturn(find);
        when(find.as(User.class)).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(true, true, false);
        when(cursor.next()).thenReturn(userMock1, userMock2);

        ArrayList<User> users = sut.getAllUsers();

        assertEquals(2, users.size());
        assertEquals(userMock1, users.get(0));
        assertEquals(userMock2, users.get(1));
    }

    @Test
    public void getAllUsersWhenNoneExist() {
        Jongo client = mock(Jongo.class);
        MongoCollection collection = mock(MongoCollection.class);
        Find find = mock(Find.class);
        MongoCursor<User> cursor = mock(MongoCursor.class);

        when(db.getClient()).thenReturn(client);
        when(client.getCollection("users")).thenReturn(collection);
        when(collection.find("{}")).thenReturn(find);
        when(find.as(User.class)).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(false);

        ArrayList<User> users = sut.getAllUsers();

        assertEquals(0, users.size());
    }

}
