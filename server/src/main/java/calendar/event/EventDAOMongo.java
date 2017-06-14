package calendar.event;

import calendar.databaseConnections.MongoDBClient;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Class EventDAOMongo
 *
 * This is the MongoDB implementation of EventDAO which uses Jongo to communicate with the MongoDB
 * database.
 *
 * @author Leif Karlsson (leifkarlsson)
 */
@Repository
public class EventDAOMongo implements EventDAO {
    @Autowired
    private MongoDBClient client;

    /**
     * Takes the specified id and fetches the corresponding event from the database.
     *
     * @param id    Id of the event.
     * @return      A list containing the single event.
     */
    public List<Event> getEvent(ObjectId id) {
        MongoCollection collection = client.getClient().getCollection("events");
        List<Event> eventList = new ArrayList<>();
        eventList.add(collection.findOne(id).as(Event.class));
        return eventList;
    }

    /**
     * Fetches all events from the database.
     *
     * @return      A list of events.
     */
    public List<Event> getEvents() {
        MongoCollection collection = client.getClient().getCollection("events");
        return cursorToArray(collection.find().as(Event.class));
    }

    /**
     * Fetches events from a specific county.
     *
     * @param county    The county from which events are to be fetched.
     * @return          A list of events.
     */
    public List<Event> getEventsFromCounty(String county) {
        MongoCollection collection = client.getClient().getCollection("events");
        return cursorToArray(collection.find("{ location.county: # }", county).as(Event.class));
    }

    /**
     * Fetches events from a specific country.
     *
     * @param country    The country from which events are to be fetched.
     * @return          A list of events.
     */
    public List<Event> getEventsFromCountry(String country) {
        MongoCollection collection = client.getClient().getCollection("events");
        return cursorToArray(collection.find("{ location.country: # }", country).as(Event.class));
    }

    /**
     * Fetches events from within a specified radius with its center on the supplied coordinates.
     *
     * @param longitude     Longitude of center point.
     * @param latitude      Latitude of center point.
     * @param radius        Radius in kilometers.
     *
     * @return              A list of events.
     */
    public List<Event> getEventsWithinRadius(double longitude, double latitude, double radius) {
        MongoCollection collection = client.getClient().getCollection("events");
        return cursorToArray(collection.find("{ location.coordinates: { " +
                "$near: { " +
                "$geometry: { " +
                "type: 'Point', " +
                "coordinates: [ #, # ] }, " +
                "$maxDistance: # } } }", longitude, latitude, radius * 1000).as(Event.class));
    }

    /**
     * Fetches events that occur within two dates.
     *
     * @param fromDate      Starting date.
     * @param toDate        Ending date.
     *
     * @return              A list of events.
     */
    public List<Event> getEventsWithinDates(long fromDate, long toDate) {
        MongoCollection collection = client.getClient().getCollection("events");

        List<Event> eventList = new ArrayList<>();

        List<Event> events = cursorToArray(collection.find("{ startDateTime: { " +
                "$gte: #," +
                "$lt: # } }", fromDate, toDate).as(Event.class));

        List<Event> recurringEvents = cursorToArray(collection.find("{ recurring: true }").as(Event.class));

        for (Event event : events) {

            for (Event recurringEvent : recurringEvents) {
                if (!recurringEvent.getId().equals(event.getId()) && recurringEvent.getRecursUntil() <= toDate) {
                    eventList.add(recurringEvent);
                }
            }

            eventList.add(event);
        }

        return eventList;
    }

    /**
     * Fetches events created by a specific user.
     *
     * @param id    Id of user.
     * @return      A list of events.
     */
    public List<Event> getEventsByUserId(String id) {
        MongoCollection collection = client.getClient().getCollection("events");
        return cursorToArray(collection.find("{createdBy: \"" + id + "\"}").as(Event.class));
    }

    /**
     * Stores a created event in the database.
     *
     * @param event     An Event object.
     * @return          The created event.
     */
    public Event createEvent(Event event) {
        MongoCollection collection = client.getClient().getCollection("events");
        collection.ensureIndex("{ location.coordinates: '2dsphere' }");
        collection.insert(event);
        return event;
    }

    /**
     * Deletes the event with the specified id.
     *
     * @param id    Id of event to be deleted.
     */
    public void deleteEvent(String id) {
        MongoCollection collection = client.getClient().getCollection("events");
        collection.remove(new ObjectId(id));
    }

    /**
     * Updates an event with new information.
     *
     * @param event     An Event object containing the new information.
     * @return          The updated event.
     */
    public Event updateEvent(Event event) {
        MongoCollection collection = client.getClient().getCollection("events");
        Event eventToUpdate = collection.findOne(new ObjectId(event.getId())).as(Event.class);
        event.setCreatedBy(eventToUpdate.getCreatedBy());
        event.setCreatedAt(eventToUpdate.getCreatedAt());
        event.setUpdatedAt(DateTime.now().getMillis());
        collection.update(new ObjectId(event.getId())).with(event);
        return event;
    }

    /**
     * Takes a MongoCursor containing Event objects and moves them to an ArrayList.
     *
     * @param cursor    MongoCursor to be converted to an ArrayList.
     * @return          An ArrayList containing all Event objects from the cursor.
     * @author Axel Nilsson (axnion)
     */
    private static List<Event> cursorToArray(MongoCursor<Event> cursor) {
        List<Event> list = new ArrayList<>();

        while(cursor.hasNext()) {
            list.add(cursor.next());
        }

        return list;
    }
}
