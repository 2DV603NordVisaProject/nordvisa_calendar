package calendar.imageHandling;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

public class Image {

    @MongoId
    @MongoObjectId
    private String id;
    private String name;
    private byte[] file;
    private String type;

    Image() {}

    Image(String name, byte[] file, String type) {
        this.name = name;
        this.file = file;
        this.type = type;
    }

    Image(String id, String name, byte[] file, String type) {
        this.id = id;
        this.name = name;
        this.file = file;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
