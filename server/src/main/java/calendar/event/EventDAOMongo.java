package calendar.event;

import calendar.databaseConnections.MongoDBClient;
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
        Event event = collection.findOne(Oid.withOid(id)).as(Event.class);
        return event;
    }

    public void createEvent(Event event) {
        MongoCollection collection = client.getCollection("events");
        event.setCreatedAt(DateTime.now().getMillis());
        collection.insert(event);
    }
}
