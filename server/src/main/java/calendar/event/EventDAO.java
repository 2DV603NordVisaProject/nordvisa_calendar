package calendar.event;

public interface EventDAO {
    Event getEvent(String id);
    void createEvent(Event event);
}
