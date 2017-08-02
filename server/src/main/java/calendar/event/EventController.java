package calendar.event;

import calendar.event.dto.CreateEventDTO;
import calendar.event.dto.DeleteEventDTO;
import calendar.event.dto.UpdateEventDTO;
import calendar.event.exceptions.EventNotFoundException;
import calendar.event.exceptions.Error;
import calendar.event.exceptions.MissingTokenException;
import calendar.user.AuthorizationChecker;
import calendar.token.TokenValidator;
import calendar.user.CurrentUser;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class EventController
 *
 * The EventController class maps to /api/event. It exposes the API endpoints used to perform
 * CRUD operations on the events.
 *
 * The following endpoints are available:
 *
 * Get events (GET)                 /api/event/get?id={id}
 *  .                               /api/event/get?longitude={longitude}&latitude={latitude}&radius={radius}
 *  .                               /api/event/get?country={country}
 *  .                               /api/event/get?county={county}
 *  .                               /api/event/get?fromDate={fromDate}&toDate={toDate}
 * Get user manageable events (GET) /api/event/get_manageable
 * Get all events (GET)             /api/event/get_all
 * Update event (POST)              /api/event/update
 * Delete event (POST)              /api/event/delete
 *
 * @author Leif Karlsson (leifkarlsson)
 */
@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventDAO dao;
    @Autowired
    private TokenValidator tokenValidator;
    @Autowired
    private AuthorizationChecker auth;
    @Autowired
    private CurrentUser currentUser;

    //private TokenValidator tokenValidator = new TokenValidator();
    //private AuthorizationChecker auth = new AuthorizationChecker();

    /**
     * This method accepts multiple parameters in various configurations to fetch events
     * from the database according to certain criteria.
     *
     * @param id            Id of the event to fetch
     * @param longitude     Longitude of the point from which the radius should be calculated
     * @param latitude      Latitude of the point from which the radius should be calculated
     * @param radius        Radius in kilometers in which events should be fetched
     * @param county        County from which events should be fetched
     * @param country       Country from which events should be fetched
     * @param fromDate      Starting date of events to be fetched
     * @param toDate        Ending date of events to be fetched
     * @param token         Token needed by the widget to fetch events
     *
     * @return              A list of events
     */
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

        String user = currentUser.getEmailAddress();

        if (token == null && user == null) {
            throw new MissingTokenException("Unauthorized access");
        }

        if (!tokenValidator.validate(token) && user == null) {
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

    /**
     * This method fetches the events that the currently logged in user can manage.
     *
     * @return      A list of manageable events
     */
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

    /**
     * This method fetches all available events from the database.
     *
     * @return      A list of all events
     */
    @RequestMapping(value = "/get_all", method = RequestMethod.GET)
    public List<Event> getAll() {
        return dao.getEvents();
    }

    /**
     * This method creates a new event with data from the CreateEventDTO.
     *
     * @param createEventDTO    Data access object bridging the client and server.
     *
     * @return                  The created event.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Event createEvent(@ModelAttribute CreateEventDTO createEventDTO) {
        Event event = new Event(createEventDTO);
        return dao.createEvent(event);
    }

    /**
     * This method deletes an event by getting the specified id from the DeleteEventDTO.
     *
     * @param deleteEventDTO    Data access object containing the id of the event to be deleted.
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void deleteEvent(@ModelAttribute DeleteEventDTO deleteEventDTO) {
        Event event = dao.getEvent(new ObjectId(deleteEventDTO.getId())).get(0);

        if(auth.currentUserCanManage(event.getCreatedBy())) {
            dao.deleteEvent(deleteEventDTO.getId());
        }
    }

    /**
     * This method updates an event with new data from the UpdateEventDTO.
     *
     * @param updateEventDTO    Data access object containing the updated information.
     *
     * @return                  The updated event.
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Event updateEvent(@ModelAttribute UpdateEventDTO updateEventDTO) {
        Event event = new Event(updateEventDTO);

        if(auth.currentUserCanManage(event.getCreatedBy())) {
            return dao.updateEvent(event);
        }

        return event;
    }

    /**
     * Custom exception that returns a 404 HTTP response when no events
     * were found.
     *
     * @param e     Custom EventNotFoundException containing an error message.
     *
     * @return      Error message.
     */
    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error eventNotFound(EventNotFoundException e) {
        String message = e.getMessage();
        return new Error(404, message);
    }

    /**
     * Custom exception that returns a 401 HTTP response when no token was provided.
     *
     * @param e     Custom MissingTokenException containing an error message.
     *
     * @return      Error message.
     */
    @ExceptionHandler(MissingTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Error missingToken(MissingTokenException e) {
        String message = e.getMessage();
        return new Error(401, message);
    }
}
