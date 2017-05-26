package calendar.event;

import java.util.List;

/**
 * Class EventLocationCoordinates
 *
 * This class is used to properly format event coordinates for storing
 * and indexing in a MongoDB database.
 *
 * @author Leif Karlsson (leifkarlsson)
 */
public class EventLocationCoordinates {
    private String type;
    private List<Double> coordinates;

    EventLocationCoordinates() {
        this.type = "Point";
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }
}
