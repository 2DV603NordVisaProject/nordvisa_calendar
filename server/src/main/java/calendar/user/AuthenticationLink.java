package calendar.user;

import org.joda.time.DateTime;

/**
 * Class AuthenticationLink
 *
 * An instance of this object represents a persistent authentcation link in memory. It holds a
 * url/urlid and a timestamp. If the timestamp is older than 24 hours this link has expired.
 *
 * @author Axel Nilsson (axnion)
 */
class AuthenticationLink {
    private String url;
    private long timestamp;

    /**
     * Constructor used by Jackson when mapping from JSON
     */
    AuthenticationLink() {

    }

    /**
     * Constructor which tests both url and timestamp
     *
     * @param url       A url id which is a unique random string
     * @param timestamp An Epoch timestamp
     */
    AuthenticationLink(String url, long timestamp) {
        this.url = url;
        this.timestamp = timestamp;
    }

    /**
     * Getter url
     * @return value of the url field
     */
    String getUrl() {
        return url;
    }

    /**
     * Getter timestamp
     * @return  value of the timestamp field
     */
    long getTimestamp() {
        return timestamp;
    }

    /**
     * Checks if the timestamp is older than 24 hours. If so the link has expired
     * @return True if the link has expired, false if not.
     */
    boolean hasExpired() {
        return new DateTime(timestamp).plusDays(1).isBeforeNow();
    }
}
