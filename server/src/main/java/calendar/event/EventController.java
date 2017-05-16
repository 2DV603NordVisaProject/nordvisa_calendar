package calendar.event;

import calendar.event.dto.CreateEventDTO;
import calendar.event.dto.DeleteEventDTO;
import calendar.event.dto.UpdateEventDTO;
import calendar.event.exceptions.EventNotFoundException;
import calendar.event.exceptions.Error;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<Event> getEvents(@RequestParam(required = false) String id,
                                 @RequestParam(required = false) Double longitude,
                                 @RequestParam(required = false) Double latitude,
                                 @RequestParam(required = false) Double radius,
                                 @RequestParam(required = false) String country,
                                 @RequestParam(required = false) Long fromDate,
                                 @RequestParam(required = false) Long toDate) {

        EventDAO dao = new EventDAOMongo();

        if (id != null) {

            List<Event> event = dao.getEvent(id);

            if (event.get(0) == null) {
                throw new EventNotFoundException("Event not found");
            }

            return event;
        }

        if (country != null) {

            List<Event> events = dao.getEventsFromCountry(country);

            if (events.size() == 0) {
                throw new EventNotFoundException("Events not found");
            }

            return events;

        }

        if (longitude != null && latitude != null && radius != null) {

            List<Event> events = dao.getEventsWithinRadius(longitude, latitude, radius);

            if (events.size() == 0) {
                throw new EventNotFoundException("Events not found");
            }

            return events;
        }

        if (fromDate != null && toDate != null) {

            List<Event> events = dao.getEventsWithinDates(fromDate, toDate);

            if (events.size() == 0) {
                throw new EventNotFoundException("Events not found");
            }

            return events;
        }

        throw new EventNotFoundException("Events not found");

    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Event createEvent(@RequestBody CreateEventDTO createEventDTO) {
        Event event = new Event(createEventDTO);
        EventDAO dao = new EventDAOMongo();
        return dao.createEvent(event);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void deleteEvent(@RequestBody DeleteEventDTO deleteEventDTO) {
        EventDAO dao = new EventDAOMongo();
        dao.deleteEvent(deleteEventDTO.getId());
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Event updateEvent(@RequestBody UpdateEventDTO updateEventDTO) {
        Event event = new Event(updateEventDTO);
        EventDAO dao = new EventDAOMongo();
        return dao.updateEvent(event);
    }

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error eventNotFound(EventNotFoundException e) {
        String message = e.getMessage();
        return new Error(message);
    }
}
