package calendar.event;

import java.util.List;

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
