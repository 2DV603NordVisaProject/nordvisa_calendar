package calendar.event.dto;

import calendar.event.EventLocation;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;

public class CreateEventDTO {

    private String name;
    private String location;
    private String description;
    private long date;
    private float duration;
    private boolean isRecurring;
    private int recursEvery;
    private long recursUntil;
    private String url;
    //private List<Image> images;
    private long createdAt;
    private long updatedAt;
    private String editedBy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    public int getRecursEvery() {
        return recursEvery;
    }

    public void setRecursEvery(int recursEvery) {
        this.recursEvery = recursEvery;
    }

    public long getRecursUntil() {
        return recursUntil;
    }

    public void setRecursUntil(long recursUntil) {
        this.recursUntil = recursUntil;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public EventLocation getGeoCodedLocation(String address) {

        EventLocation eventLocation = new EventLocation();

        // Temporary API key
        final String API_KEY = "AIzaSyAM9tW28Kcfem-zAIyyPnnPnyqL1WY5TGo";

        GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);
        GeocodingApiRequest request = GeocodingApi.newRequest(context).address(address);

        try {
            GeocodingResult[] results = request.await();
            eventLocation.setLatitude(results[0].geometry.location.lat);
            eventLocation.setLongitude(results[0].geometry.location.lng);
            eventLocation.setAddress(address);
            eventLocation.setParsedAddress(results[0].formattedAddress);

            for (AddressComponent addressComponent : results[0].addressComponents) {
                switch (addressComponent.types[0]) {
                    case POSTAL_CODE:
                        // Remove any white space from postal code
                        String postalCode = addressComponent.longName
                                .replaceAll("\\s+","");
                        eventLocation.setPostalCode(postalCode);
                        break;
                    case LOCALITY:
                        eventLocation.setCity(addressComponent.longName);
                        break;
                    case POSTAL_TOWN:
                        eventLocation.setCity(addressComponent.longName);
                        break;
                    case COUNTRY:
                        eventLocation.setCountry(addressComponent.shortName);
                        break;
                    default:
                        break;
                }
            }

        } catch (Exception e) {
            // Handle error
        }

        return eventLocation;
    }
}
