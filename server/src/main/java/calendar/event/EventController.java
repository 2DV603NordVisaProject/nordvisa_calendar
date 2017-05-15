package calendar.event;

import calendar.event.dto.CreateEventDTO;
import calendar.event.dto.DeleteEventDTO;
import calendar.event.dto.UpdateEventDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<Event> getEvents(@RequestParam(required = false) String id,
                                 @RequestParam(required = false) Double longitude,
                                 @RequestParam(required = false) Double latitute,
                                 @RequestParam(required = false) Double radius,
                                 @RequestParam(required = false) String country,
                                 @RequestParam(required = false) Long fromDate,
                                 @RequestParam(required = false) Long toDate) {

        EventDAO dao = new EventDAOMongo();

        if (id != null) {
            return dao.getEvent(id);
        }

        if (country != null) {
            return dao.getEventsFromCountry(country);
        }

        return dao.getEvents();

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
}
