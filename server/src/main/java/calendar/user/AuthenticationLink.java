package calendar.user;

import org.joda.time.DateTime;

/**
 * Class AuthenticationLink
 *
 * @author Axel Nilsson (axnion)
 */
class AuthenticationLink {
    private String url;     //TODO: Should be encrypted
    private long timestamp;

    AuthenticationLink() {

    }

    AuthenticationLink(String url, long timestamp) {
        this.url = url;
        this.timestamp = timestamp;
    }

    String getUrl() {
        return url;
    }

    long getTimestamp() {
        return timestamp;
    }

    boolean hasExpired() {
        return new DateTime(timestamp).plusDays(1).isBeforeNow();
    }
}
