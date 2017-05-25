package calendar.event;

import calendar.event.dto.CreateEventDTO;
import calendar.event.dto.DeleteEventDTO;
import calendar.event.dto.UpdateEventDTO;
import calendar.event.exceptions.EventNotFoundException;
import calendar.event.exceptions.Error;
import calendar.event.exceptions.MissingTokenException;
import calendar.user.AuthorizationChecker;
import calendar.token.TokenValidator;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    // TODO: Update class diagram
    @Autowired
    private EventDAO dao;
    private TokenValidator tokenValidator = new TokenValidator();
    private AuthorizationChecker auth = new AuthorizationChecker();

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<Event> getEvents(@RequestParam(required = false) String id,
                                 @RequestParam(required = false) Double longitude,
                                 @RequestParam(required = false) Double latitude,
                                 @RequestParam(required = false) Double radius,
                                 @RequestParam(required = false) String county,
                                 @RequestParam(required = false) String country,
                                 @RequestParam(required = false) Long fromDate,
                                 @RequestParam(required = false) Long toDate,
                                 @RequestParam(required = false) String token) {


        if (token == null) {
            throw new MissingTokenException("Unauthorized access");
        }

        if (!tokenValidator.validate(token)) {
            throw new MissingTokenException("Unauthorized access");
        }

        if (id != null) {

            // Check if supplied id is valid ObjectId
            try {

                ObjectId eventId = new ObjectId(id);

                List<Event> event = dao.getEvent(eventId);

                if (event.get(0) == null) {
                    throw new EventNotFoundException("Event not found");
                }

                return event;

            } catch (IllegalArgumentException e) {
                throw new EventNotFoundException("Event not found");
            }

        }

        if (county != null) {

            List<Event> events = dao.getEventsFromCounty(county);

            if (events.size() == 0) {
                throw new EventNotFoundException("Events not found");
            }

            return events;
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

    @RequestMapping(value = "/get_manageable", method = RequestMethod.GET)
    public List<Event> getManageable() {
        List<String> ids = auth.getAllUserIds();
        List<Event> events = new ArrayList<>();

        for(String id : ids) {
            if(auth.currentUserCanManage(id)) {
                events.addAll(dao.getEventsByUserId(id));
            }
        }

        return events;
    }

    @RequestMapping(value = "/get_all", method = RequestMethod.GET)
    public List<Event> getAll() {
        return dao.getEvents();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Event createEvent(@ModelAttribute CreateEventDTO createEventDTO) {
        Event event = new Event(createEventDTO);
        return dao.createEvent(event);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void deleteEvent(@ModelAttribute DeleteEventDTO deleteEventDTO) {
        Event event = dao.getEvent(new ObjectId(deleteEventDTO.getId())).get(0);

        if(auth.currentUserCanManage(event.getCreatedBy())) {
            dao.deleteEvent(deleteEventDTO.getId());
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Event updateEvent(@ModelAttribute UpdateEventDTO updateEventDTO) {
        Event event = new Event(updateEventDTO);

        if(auth.currentUserCanManage(event.getCreatedBy())) {
            return dao.updateEvent(event);
        }

        return event;
    }

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error eventNotFound(EventNotFoundException e) {
        String message = e.getMessage();
        return new Error(404, message);
    }

    @ExceptionHandler(MissingTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Error missingToken(MissingTokenException e) {
        String message = e.getMessage();
        return new Error(401, message);
    }
}
