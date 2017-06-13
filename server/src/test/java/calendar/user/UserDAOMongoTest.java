package calendar.user;

import calendar.databaseConnections.MongoDBClient;
import org.bson.types.ObjectId;
import org.jongo.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class UserDAOMongoTest
 *
 * TODO: Add comments
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

    private Jongo client;
    private MongoCollection collection;

    @Before
    public void setup() {
        client = mock(Jongo.class);
        collection = mock(MongoCollection.class);
        when(db.getClient()).thenReturn(client);
        when(client.getCollection("users")).thenReturn(collection);
    }

    @Test
    public void getExistingUserByValidId() {
        FindOne findOne = mock(FindOne.class);
        User userMock = mock(User.class);

        when(collection.findOne(any(ObjectId.class))).thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(userMock);

        User user = sut.getUserById("507f1f77bcf86cd799439011");

        verify(collection, times(1)).findOne(any(ObjectId.class));
        verify(findOne, times(1)).as(User.class);
        assertEquals(user, userMock);
    }

    @Test
    public void getNonExistingUserWithValidId() {
        FindOne findOne = mock(FindOne.class);

        when(collection.findOne(any(ObjectId.class))).thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(null);

        User user = sut.getUserById("507f1f77bcf86cd799439011");

        verify(collection, times(1)).findOne(any(ObjectId.class));
        verify(findOne, times(1)).as(User.class);
        assertNull(user);
    }

    @Test
    public void getUserWithInvalidId() {
        FindOne findOne = mock(FindOne.class);
        User userMock = mock(User.class);

        when(collection.findOne(any(ObjectId.class))).thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(userMock);

        try {
            sut.getUserById("ljkjjkkjjljkjk");
            fail();
        } catch (IllegalArgumentException expt) {
            expt.getMessage();
        }
    }

    @Test
    public void getExistingUserByEmail() {
        FindOne findOne = mock(FindOne.class);
        User userMock = mock(User.class);

        when(collection.findOne("{email: \"test@test.com\"}")).thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(userMock);

        User user = sut.getUserByEmail("test@test.com");

        assertEquals(userMock, user);
    }

    @Test
    public void getNonExistingUserByEmail() {
        FindOne findOne = mock(FindOne.class);

        when(collection.findOne("{email: \"test@test.com\"}")).thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(null);

        User user = sut.getUserByEmail("test@test.com");

        assertNull(user);
    }

    @Test
    public void getExistingUserByPasswordRecoveryLink() {
        FindOne findOne = mock(FindOne.class);
        User userMock = mock(User.class);

        when(collection.findOne("{resetPasswordLink.url: \"random_string_12345\"}"))
                .thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(userMock);

        User user = sut.getUserByPasswordRecoveryLink("random_string_12345");

        assertEquals(userMock, user);
    }

    @Test
    public void getNonExistingUserByPasswordRecoveryLink() {
        FindOne findOne = mock(FindOne.class);
        User userMock = mock(User.class);

        when(collection.findOne("{resetPasswordLink.url: \"random_string_12345\"}"))
                .thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(null);

        User user = sut.getUserByPasswordRecoveryLink("random_string_12345");

        assertNull(user);
    }

    @Test
    public void getExistingUserByEmailVerificationLink() {
        FindOne findOne = mock(FindOne.class);
        User userMock = mock(User.class);

        when(collection.findOne("{validateEmailLink.url: \"random_string_12345\"}"))
                .thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(userMock);

        User user = sut.getUserByEmailVerificationLink("random_string_12345");

        assertEquals(userMock, user);
    }

    @Test
    public void getNonExistingUserByEmailVerificationLink() {
        FindOne findOne = mock(FindOne.class);

        when(collection.findOne("{validateEmailLink.url: \"random_string_12345\"}"))
                .thenReturn(findOne);
        when(findOne.as(User.class)).thenReturn(null);

        User user = sut.getUserByEmailVerificationLink("random_string_12345");

        assertNull(user);
    }

    @Test
    public void getUsersByExistingOrganization() {
        Find find = mock(Find.class);
        MongoCursor<User> cursor = mock(MongoCursor.class);
        User userMock1 = mock(User.class);
        User userMock2 = mock(User.class);

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
        Find find = mock(Find.class);
        MongoCursor<User> cursor = mock(MongoCursor.class);

        when(collection.find("{organization.name: \"org\"}")).thenReturn(find);
        when(find.as(User.class)).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(false);

        ArrayList<User> users = sut.getUsersByOrganization("org");

        assertEquals(0, users.size());
    }

    @Test
    public void getAllUsers() {
        Find find = mock(Find.class);
        MongoCursor<User> cursor = mock(MongoCursor.class);
        User userMock1 = mock(User.class);
        User userMock2 = mock(User.class);

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
        Find find = mock(Find.class);
        MongoCursor<User> cursor = mock(MongoCursor.class);

        when(collection.find("{}")).thenReturn(find);
        when(find.as(User.class)).thenReturn(cursor);
        when(cursor.hasNext()).thenReturn(false);
        when(cursor.next()).thenReturn(null);

        ArrayList<User> users = sut.getAllUsers();

        assertEquals(0, users.size());
    }

    @Test
    public void getPendingRegistrationsAsGlobalAdmin() {
        MongoCursor<User> regCursor= mock(MongoCursor.class);
        User regUser1 = createUserMock("1", "test1@test.com", "org1");
        User regUser2 = createUserMock("2", "test2@test.com", "_");
        Find regFind = mock(Find.class);
        when(collection.find("{\"organization.approved\": false}")).thenReturn(regFind);
        when(regFind.as(User.class)).thenReturn(regCursor);
        when(regCursor.hasNext()).thenReturn(true, true, false);
        when(regCursor.next()).thenReturn(regUser1, regUser2);

        MongoCursor<User> changeCursor = mock(MongoCursor.class);
        User changeUser1 = createUserMock("3", "test3@test.com", "org1");
        User changeUser2 = createUserMock("4", "test4@test.com", "_");
        User changeUser3 = createUserMock("5", "test5@test.com", "");
        Find changeFind = mock(Find.class);
        when(collection.find("{\"organization.changePending\": {$ne: \"_\"}}"))
                .thenReturn(changeFind);
        when(changeFind.as(User.class)).thenReturn(changeCursor);
        when(changeCursor.hasNext()).thenReturn(true, true, true, true, false);
        when(changeCursor.next()).thenReturn(changeUser1, changeUser2, changeUser3, regUser1);

        ArrayList<User> users = sut.getPendingRegistrations("");

        assertEquals(4, users.size());

        users.remove(regUser1);
        users.remove(regUser2);
        users.remove(changeUser1);
        users.remove(changeUser3);
        assertEquals(0, users.size());
    }

    @Test
    public void getPendingRegistrationsAsOrgAdmin() {
        MongoCursor<User> regCursor= mock(MongoCursor.class);
        User regUser1 = createUserMock("1", "test1@test.com", "org1");
        Find regFind = mock(Find.class);
        when(collection.find("{\"organization.approved\": false}")).thenReturn(regFind);
        when(regFind.as(User.class)).thenReturn(regCursor);
        when(regCursor.hasNext()).thenReturn(true, false);
        when(regCursor.next()).thenReturn(regUser1);

        MongoCursor<User> changeCursor = mock(MongoCursor.class);
        User changeUser1 = createUserMock("5", "test5@test.com", "org1");
        Find changeFind = mock(Find.class);
        when(collection.find("{\"organization.changePending\": \"org1\"}"))
                .thenReturn(changeFind);
        when(changeFind.as(User.class)).thenReturn(changeCursor);
        when(changeCursor.hasNext()).thenReturn(true, true, false);
        when(changeCursor.next()).thenReturn(changeUser1, regUser1);

        ArrayList<User> users = sut.getPendingRegistrations("org1");

        assertEquals(2, users.size());

        users.remove(regUser1);
        users.remove(changeUser1);
        assertEquals(0, users.size());
    }

    @Test
    public void getExistingOrganizations() {
        Distinct distinct = mock(Distinct.class);
        List<String> orgs = new ArrayList<>();
        orgs.add("");
        orgs.add("my_org");
        orgs.add("other_org");

        when(collection.distinct("organization.name")).thenReturn(distinct);
        when(distinct.as(String.class)).thenReturn(orgs);

        List<String> returnedOrgs = sut.getOrganizations();

        assertEquals(orgs, returnedOrgs);
    }

    @Test
    public void getNonExistingOrganizations() {
        Distinct distinct = mock(Distinct.class);
        List<String> orgs = new ArrayList<>();

        when(collection.distinct("organization.name")).thenReturn(distinct);
        when(distinct.as(String.class)).thenReturn(orgs);

        List<String> returnedOrgs = sut.getOrganizations();

        assertEquals(orgs, returnedOrgs);
        assertTrue(returnedOrgs.isEmpty());
    }

    @Test
    public void addUser(){
        User userMock = mock(User.class);
        when(collection.insert(userMock)).thenReturn(null);
        sut.add(userMock);
        verify(collection, times(1)).insert(userMock);
    }

    @Test
    public void deleteUser(){
        when(client.getCollection("users")).thenReturn(collection);
        when(collection.remove(any(ObjectId.class))).thenReturn(null);

        sut.delete("507f1f77bcf86cd799439011");

        verify(collection, times(1)).remove(any(ObjectId.class));
    }

    @Test
    public void updateUser(){
        User userMock = mock(User.class);
        Update update = mock(Update.class);

        when(userMock.getId()).thenReturn("507f1f77bcf86cd799439011");
        when(client.getCollection("users")).thenReturn(collection);
        when(collection.update(any(ObjectId.class))).thenReturn(update);
        when(update.with(userMock)).thenReturn(null);

        sut.update(userMock);

        verify(collection, times(1)).update(any(ObjectId.class));
        verify(update, times(1)).with(userMock);
    }

    private User createUserMock(String id, String email, String changePending) {
        User user = mock(User.class);
        Organization org = mock(Organization.class);

        when(user.getId()).thenReturn(id);
        when(user.getEmail()).thenReturn(email);
        when(user.getOrganization()).thenReturn(org);

        when(org.getChangePending()).thenReturn(changePending);

        return user;
    }
}
