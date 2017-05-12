package calendar.event;

import calendar.event.dto.CreateEventDTO;
import calendar.event.dto.GetEventDTO;
import org.joda.time.DateTime;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

class Event {

    @MongoId
    @MongoObjectId
    private String id;
    private String name;
    private EventLocation location;
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

    Event() {
    }

    Event(GetEventDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.location = dto.getLocation();
        this.description = dto.getDescription();
        this.date = dto.getDate();
        this.duration = dto.getDuration();
        this.isRecurring = dto.isRecurring();
        this.recursEvery = dto.getRecursEvery();
        this.recursUntil = dto.getRecursUntil();
        this.url = dto.getUrl();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = DateTime.now().getMillis();
        this.editedBy = dto.getEditedBy();
    }

    Event(CreateEventDTO dto) {
        this.name = dto.getName();
        this.location = dto.getGeoCodedLocation(dto.getLocation());
        this.description = dto.getDescription();
        this.date = dto.getDate();
        this.duration = dto.getDuration();
        this.isRecurring = dto.isRecurring();
        this.recursEvery = dto.getRecursEvery();
        this.recursUntil = dto.getRecursUntil();
        this.url = dto.getUrl();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = DateTime.now().getMillis();
        this.editedBy = dto.getEditedBy();
    }

    public CreateEventDTO toCreateEventDTO() {
        CreateEventDTO dto = new CreateEventDTO();
        dto.setName(this.name);
        dto.setDescription(this.description);
        return dto;
    }

    public GetEventDTO toGetEventDTO() {
        GetEventDTO dto = new GetEventDTO();
        dto.setId(this.id);
        dto.setName(this.name);
        dto.setDescription(this.description);
        dto.setLocation(this.location);
        dto.setCreatedAt(this.createdAt);
        dto.setUpdatedAt(this.updatedAt);
        return dto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventLocation getLocation() {
        return location;
    }

    public void setLocation(EventLocation location) {
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
}
