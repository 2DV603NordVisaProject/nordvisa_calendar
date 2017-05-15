package calendar.event;

import calendar.databaseConnections.MongoDBClient;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

public class EventDAOMongo implements EventDAO {

    private Jongo client;

    public EventDAOMongo() {
        client = MongoDBClient.getClient();
    }

    public List<Event> getEvent(String id) {
        MongoCollection collection = client.getCollection("events");
        List<Event> eventList = new ArrayList<>();
        eventList.add(collection.findOne(new ObjectId(id)).as(Event.class));
        return eventList;
    }

    public List<Event> getEvents() {
        MongoCollection collection = client.getCollection("events");
        return cursorToArray(collection.find().as(Event.class));
    }

    public List<Event> getEventsFromCountry(String country) {
        MongoCollection collection = client.getCollection("events");
        return cursorToArray(collection.find("{ location.country: # }", country).as(Event.class));
    }

    public Event createEvent(Event event) {
        MongoCollection collection = client.getCollection("events");
        collection.insert(event);
        return event;
    }

    public void deleteEvent(String id) {
        MongoCollection collection = client.getCollection("events");
        collection.remove(new ObjectId(id));
    }

    public Event updateEvent(Event event) {
        MongoCollection collection = client.getCollection("events");
        Event eventToUpdate = collection.findOne(new ObjectId(event.getId())).as(Event.class);
        event.setCreatedBy(eventToUpdate.getCreatedBy());
        event.setCreatedAt(eventToUpdate.getCreatedAt());
        event.setUpdatedAt(DateTime.now().getMillis());
        collection.update(new ObjectId(event.getId())).with(event);
        return event;
    }

    private static List<Event> cursorToArray(MongoCursor<Event> cursor) {
        List<Event> list = new ArrayList<>();

        while(cursor.hasNext()) {
            list.add(cursor.next());
        }

        return list;
    }
}
