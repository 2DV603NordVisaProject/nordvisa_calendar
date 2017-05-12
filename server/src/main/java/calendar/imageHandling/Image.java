package calendar.imageHandling;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

public class Image {

    @MongoId
    @MongoObjectId
    private String id;
    private String name;
    private byte[] file;

    Image() {}

    Image(String name, byte[] file) {
        this.name = name;
        this.file = file;
    }

    Image(String id, String name, byte[] file) {
        this.id = id;
        this.name = name;
        this.file = file;
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
}
