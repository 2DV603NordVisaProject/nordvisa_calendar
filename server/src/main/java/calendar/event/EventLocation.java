package calendar.event;

/**
 * Class EventLocation
 *
 * @author Leif Karlsson (leifkarlsson)
 */
public class EventLocation {

    private EventLocationCoordinates coordinates;
    private String address;
    private String parsedAddress;
    private String postalCode;
    private String city;
    private String county;
    private String country;

    public EventLocation() {}

    public EventLocationCoordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(EventLocationCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParsedAddress() {
        return parsedAddress;
    }

    public void setParsedAddress(String parsedAddress) {
        this.parsedAddress = parsedAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
