package calendar.event;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @RequestMapping(method = RequestMethod.GET)
    public Event getEvent(@RequestParam(value="get", required = true) String id) {
        return new Event();
    }

}
