package calendar.image;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * Image class.
 *
 * @author Francis Menkes (fmenkes)
 */
public class Image {

    @MongoId
    @MongoObjectId
    private String id;
    private String name;
    private String path;
    private byte[] file;
    private String type;

    /**
     * Empty constructor used by Jackson
     */
    Image() {}

    Image(String name, byte[] file, String path, String type) {
        this.name = name;
        this.file = file;
        this.type = type;
        this.path = path;
    }

    Image(String id, String name, byte[] file, String path, String type) {
        this.id = id;
        this.name = name;
        this.file = file;
        this.type = type;
        this.path = path;
    }

    // Getters and setters

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
