package calendar.token;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;


public class Token {
    @MongoId
    @MongoObjectId
    private String id;
    private String token;
    private int requests;
    private int maxRequests;
    private long createdAt;
    private long validUntil;

    Token(String token, int maxRequests, long createdAt, long validUntil) {
        this.token = token;
        this.maxRequests = maxRequests;
        this.createdAt = createdAt;
        this.validUntil = validUntil;
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

    @Override
    public String toString() {
        return "Token{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", requests=" + requests +
                ", maxRequests=" + maxRequests +
                ", createdAt=" + createdAt +
                ", validUntil=" + validUntil +
                '}';
    }
}
