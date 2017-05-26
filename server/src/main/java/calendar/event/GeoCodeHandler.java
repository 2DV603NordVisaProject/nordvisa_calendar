package calendar.event;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;
import java.util.ArrayList;
import java.util.List;

/**
 * Class GeoCodeHandler
 *
 * This class contains the getGeoCodedLocation method which takes an address
 * and uses the Google Maps geocoding API to return geocoded information about
 * the given address.
 *
 * @author Leif Karlsson (leifkarlsson)
 */
abstract class GeoCodeHandler {

    /**
     * With the supplied address, this method uses the Google Maps API to retrieve geocoded
     * information, such as coordinates, postalcode, city, country etc.
     *
     * @param address       The address to geocode.
     * @return              An EventLocation object containing all geocoded data.
     */
    static EventLocation getGeoCodedLocation(String address) {

        EventLocation eventLocation = new EventLocation();

        // Temporary API key
        final String API_KEY = "AIzaSyAM9tW28Kcfem-zAIyyPnnPnyqL1WY5TGo";

        GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);
        GeocodingApiRequest request = GeocodingApi.newRequest(context).address(address);

        try {
            GeocodingResult[] results = request.await();

            List<Double> latlng = new ArrayList<>();
            latlng.add(results[0].geometry.location.lng);
            latlng.add(results[0].geometry.location.lat);

            EventLocationCoordinates eventLocationCoordinates = new EventLocationCoordinates();
            eventLocationCoordinates.setCoordinates(latlng);
            eventLocation.setCoordinates(eventLocationCoordinates);
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
                    case ADMINISTRATIVE_AREA_LEVEL_1:
                        eventLocation.setCounty(addressComponent.longName);
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
