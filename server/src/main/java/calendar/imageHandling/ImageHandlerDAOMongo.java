package calendar.imageHandling;

import calendar.databaseConnections.MongoDBClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.Oid;
import org.jongo.bson.Bson;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

class ImageHandlerDAOMongo implements ImageHandlerDAO {

    private Jongo client;

    ImageHandlerDAOMongo() {
        client = MongoDBClient.getClient();
    }

    @Override
    public void updateEvent(String eventID, String filename) {
        MongoCollection collection = client.getCollection("events");
    }

    @Override
    public boolean saveImage(String name, MultipartFile file) {
        byte[] imageByteArray;

        try {
            imageByteArray = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Image image = new Image(name, imageByteArray);
        MongoCollection collection = client.getCollection("images");
        collection.insert(image);

        return true;
    }

    @Override
    public byte[] getImage(String name) {
        MongoCollection collection = client.getCollection("images");
        Image image = collection.findOne("{name: '" + name + "'}").as(Image.class);

        if(image == null) {
            return null;
        } else {
            return image.getFile();
        }
    }
}
