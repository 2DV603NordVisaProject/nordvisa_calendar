package calendar.event;

import calendar.event.dto.CreateEventDTO;
import calendar.event.dto.DeleteEventDTO;
import calendar.event.dto.UpdateEventDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @RequestMapping(method = RequestMethod.GET)
    public Event getEvent(@RequestParam(value="get", required = true) String id) {
        EventDAO dao = new EventDAOMongo();
        return dao.getEvent(id);
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
