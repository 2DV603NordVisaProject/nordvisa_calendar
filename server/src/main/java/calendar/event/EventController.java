package calendar.event;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @RequestMapping(method = RequestMethod.GET)
    public EventDTO getEvent(@RequestParam(value="get", required = true) String id) {
        EventDAO dao = new EventDAOMongo();
        Event event = dao.getEvent(id);
        return event.toEventDTO();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public EventDTO createEvent(@RequestBody EventDTO eventDTO) {
        Event event = new Event(eventDTO);
        EventDAO dao = new EventDAOMongo();
        dao.createEvent(event);
        return event.toEventDTO();
    }
}
