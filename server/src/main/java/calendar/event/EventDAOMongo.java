package calendar.event;

import calendar.databaseConnections.MongoDBClient;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.Oid;

public class EventDAOMongo implements EventDAO {

    private Jongo client;

    public EventDAOMongo() {
        client = MongoDBClient.getClient();
    }

    public Event getEvent(String id) {
        MongoCollection collection = client.getCollection("events");
        Event event = collection.findOne(new ObjectId(id)).as(Event.class);
        return event;
    }

    public void createEvent(Event event) {
        MongoCollection collection = client.getCollection("events");
        event.setCreatedAt(DateTime.now().getMillis());
        collection.insert(event);
    }

    public void deleteEvent(String id) {
        MongoCollection collection = client.getCollection("events");
        collection.remove(new ObjectId(id));
    }
}
