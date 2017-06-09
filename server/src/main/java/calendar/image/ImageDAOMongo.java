package calendar.image;

import calendar.databaseConnections.MongoDBClient;
import com.mongodb.WriteResult;
import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * This is the MongoDB implementation of ImageDAO, which uses Jongo to communicate with the MongoDB
 * database
 *
 * @author Francis Menkes (fmenkes)
 */
@Repository
class ImageDAOMongo implements ImageDAO {
    @Autowired
    private MongoDBClient client;

    /**
     * Saves an image in the database.
     *
     * @param name      The filename of the image, including type.
     * @param file      The file to be saved.
     * @param path      The special path that ties an event to an image.
     * @param type      The mimetype of the image.
     * @return          A boolean that indicates whether the saving was successful.
     */
    @Override
    public boolean saveImage(String name, MultipartFile file, String path, String type) {
        byte[] imageByteArray;

        try {
            imageByteArray = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Image image = new Image(name, imageByteArray, path, type);
        MongoCollection collection = client.getClient().getCollection("images");
        collection.insert(image);

        return true;
    }

    /**
     * Gets an image from the database.
     *
     * @param path      The unique path that identifies the event.
     * @param name      The filename of the image, including file type.
     * @return          The image as an instance of the Image class.
     */
    @Override
    public Image getImage(String path, String name) {
        MongoCollection collection = client.getClient().getCollection("images");
        Image image = collection.findOne("{path: '" + path + "', name: '" + name + "'}").as(Image.class);

        if(image == null) {
            return null;
        } else {
            return image;
        }
    }

    /**
     * Deletes an image from the database.
     *
     * Currently unused
     *
     * @param path      The unique path that identifies the event.
     * @param name      The filename of the image, including file type.
     * @return          A boolean that indicates whether the deletion was successful.
     */
    @Override
    public boolean deleteImage(String path, String name) {
        MongoCollection collection = client.getClient().getCollection("images");
        WriteResult r = collection.remove("{path: '" + path + "', name: '" + name + "'}");

        return r.getN() == 1;
    }

    /**
     * Deletes all images connected to a certain event.
     *
     * @param path      The unique path that identifies the event.
     */
    @Override
    public void deleteAllImages(String path) {
        MongoCollection collection = client.getClient().getCollection("images");
        collection.remove("{path: '" + path + "'}");
    }

    /**
     * Check if a path already exists in the database
     *
     * @param path      The path to check.
     * @return          Boolean indicating whether the path exists or not.
     */
    @Override
    public boolean pathExists(String path) {
        MongoCollection collection = client.getClient().getCollection("images");
        return collection.count("{path: '" + path + "'}") > 0;
    }
}
