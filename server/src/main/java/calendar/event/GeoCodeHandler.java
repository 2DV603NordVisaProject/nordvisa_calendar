package calendar.event;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;

public abstract class GeoCodeHandler {

    public static EventLocation getGeoCodedLocation(String address) {

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
