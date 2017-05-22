package calendar.event;

import calendar.databaseConnections.MongoDBClient;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventDAOMongo implements EventDAO {

    private Jongo client;

    public EventDAOMongo() {
        client = MongoDBClient.getClient();
    }

    public List<Event> getEvent(ObjectId id) {
        MongoCollection collection = client.getCollection("events");
        List<Event> eventList = new ArrayList<>();
        eventList.add(collection.findOne(id).as(Event.class));
        return eventList;
    }

    public List<Event> getEvents() {
        MongoCollection collection = client.getCollection("events");
        return cursorToArray(collection.find().as(Event.class));
    }

    public List<Event> getEventsFromCounty(String county) {
        MongoCollection collection = client.getCollection("events");
        return cursorToArray(collection.find("{ location.county: # }", county).as(Event.class));
    }

    public List<Event> getEventsFromCountry(String country) {
        MongoCollection collection = client.getCollection("events");
        return cursorToArray(collection.find("{ location.country: # }", country).as(Event.class));
    }

    public List<Event> getEventsWithinRadius(double longitude, double latitude, double radius) {
        MongoCollection collection = client.getCollection("events");
        return cursorToArray(collection.find("{ location.coordinates: { " +
                "$near: { " +
                "$geometry: { " +
                "type: 'Point', " +
                "coordinates: [ #, # ] }, " +
                "$maxDistance: # } } }", longitude, latitude, radius * 1000).as(Event.class));
    }

    public List<Event> getEventsWithinDates(long fromDate, long toDate) {
        MongoCollection collection = client.getCollection("events");
        return cursorToArray(collection.find( "{ createdAt: { " +
                "$gte: #," +
                "$lt: # } }", fromDate, toDate).as(Event.class));
    }

    // TODO: Add to docs and diagrams
    public List<Event> getEventsByUserId(String id) {
        MongoCollection collection = client.getCollection("events");
        return cursorToArray(collection.find("{createdBy: \"" + id + "\"}").as(Event.class));
    }

    public Event createEvent(Event event) {
        MongoCollection collection = client.getCollection("events");
        collection.ensureIndex("{ location.coordinates: '2dsphere' }");
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
