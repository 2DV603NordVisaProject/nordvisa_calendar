package calendar.token;

import org.joda.time.DateTime;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

// TODO: Add comments
public class Token {
    @MongoId
    @MongoObjectId
    private String id;
    private String token;
    private int requests;
    private int maxRequests;
    private long createdAt;
    private long validUntil;

    Token(String token) {
        this.token = token;
        this.maxRequests = 10000;
        this.createdAt = DateTime.now().getMillis();
        this.validUntil = DateTime.now().plusYears(1).getMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRequests() {
        return requests;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public void setMaxRequests(int maxRequests) {
        this.maxRequests = maxRequests;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(long validUntil) {
        this.validUntil = validUntil;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
