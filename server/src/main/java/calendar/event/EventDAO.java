package calendar.event;

import org.bson.types.ObjectId;

import java.util.List;

public interface EventDAO {
    List<Event> getEvent(ObjectId id);
    List<Event> getEvents();
    List<Event> getEventsFromCounty(String county);
    List<Event> getEventsFromCountry(String country);
    List<Event> getEventsWithinRadius(double longitude, double latitude, double radius);
    List<Event> getEventsWithinDates(long fromDate, long toDate);
    List<Event> getEventsByUserId(String id);       // TODO: Add to docs
    Event createEvent(Event event);
    void deleteEvent(String id);
    Event updateEvent(Event event);
}
