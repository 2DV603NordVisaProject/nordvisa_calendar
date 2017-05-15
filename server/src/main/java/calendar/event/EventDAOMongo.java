package calendar.event;

import calendar.databaseConnections.MongoDBClient;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

public class EventDAOMongo implements EventDAO {

    private Jongo client;

    public EventDAOMongo() {
        client = MongoDBClient.getClient();
    }

    public Event getEvent(String id) {
        MongoCollection collection = client.getCollection("events");
        return collection.findOne(new ObjectId(id)).as(Event.class);
    }

    public void createEvent(Event event) {
        MongoCollection collection = client.getCollection("events");
        collection.insert(event);
    }

    public void deleteEvent(String id) {
        MongoCollection collection = client.getCollection("events");
        collection.remove(new ObjectId(id));
    }

    public Event updateEvent(Event event) {
        MongoCollection collection = client.getCollection("events");
        Event eventToUpdate = collection.findOne(new ObjectId(event.getId())).as(Event.class);
        event.setCreatedAt(eventToUpdate.getCreatedAt());
        event.setUpdatedAt(DateTime.now().getMillis());
        collection.update(new ObjectId(event.getId())).with(event);
        return event;
    }
}
