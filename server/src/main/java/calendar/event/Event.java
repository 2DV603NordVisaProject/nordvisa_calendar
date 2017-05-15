package calendar.event;

import calendar.event.dto.CreateEventDTO;
import calendar.event.dto.UpdateEventDTO;
import calendar.image.Image;
import org.joda.time.DateTime;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.List;

class Event {

    @MongoId
    @MongoObjectId
    private String id;
    private String name;
    private EventLocation location;
    private String description;
    private long date;
    private float duration;
    private boolean recurring;
    private int recursEvery;
    private long recursUntil;
    private List<Image> images;
    private long createdAt;
    private long updatedAt;
    private String createdBy;
    private String editedBy;

    Event() {}

    Event(CreateEventDTO dto) {
        long createdAt = DateTime.now().getMillis();
        this.name = dto.getName();
        this.location = GeoCodeHandler.getGeoCodedLocation(dto.getLocation());
        this.description = dto.getDescription();
        this.date = dto.getDate();
        this.duration = dto.getDuration();
        this.recurring = dto.getRecurring();
        this.recursEvery = dto.getRecursEvery();
        this.recursUntil = dto.getRecursUntil();
        this.images = dto.getImages();
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
        this.createdBy = dto.getCreatedBy();
        this.editedBy = dto.getEditedBy();
    }

    Event(UpdateEventDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.location = GeoCodeHandler.getGeoCodedLocation(dto.getLocation());
        this.description = dto.getDescription();
        this.date = dto.getDate();
        this.duration = dto.getDuration();
        this.recurring = dto.getRecurring();
        this.recursEvery = dto.getRecursEvery();
        this.recursUntil = dto.getRecursUntil();
        this.images = dto.getImages();
        this.updatedAt = DateTime.now().getMillis();
        this.editedBy = dto.getEditedBy();
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

    public boolean getRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }
}
