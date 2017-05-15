package calendar.event;

public interface EventDAO {
    Event getEvent(String id);
    Event createEvent(Event event);
    void deleteEvent(String id);
    Event updateEvent(Event event);
}
