package calendar.event;

import java.util.List;

public interface EventDAO {
    List<Event> getEvent(String id);
    List<Event> getEvents();
    List<Event> getEventsFromCountry(String country);
    List<Event> getEventsWithinRadius(double longitude, double latitude, double radius);
    List<Event> getEventsWithinDates(long fromDate, long toDate);
    Event createEvent(Event event);
    void deleteEvent(String id);
    Event updateEvent(Event event);
}
