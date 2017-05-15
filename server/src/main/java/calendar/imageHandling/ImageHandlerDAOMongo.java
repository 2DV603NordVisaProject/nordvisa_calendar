package calendar.imageHandling;

import calendar.databaseConnections.MongoDBClient;
import com.mongodb.WriteResult;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

class ImageHandlerDAOMongo implements ImageHandlerDAO {

    private Jongo client;

    ImageHandlerDAOMongo() {
        client = MongoDBClient.getClient();
    }

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
        MongoCollection collection = client.getCollection("images");
        collection.insert(image);

        return true;
    }

    @Override
    public Image getImage(String path, String name) {
        MongoCollection collection = client.getCollection("images");
        Image image = collection.findOne("{path: '" + path + "', name: '" + name + "'}").as(Image.class);

        if(image == null) {
            return null;
        } else {
            return image;
        }
    }

    @Override
    public boolean deleteImage(String name) {
        MongoCollection collection = client.getCollection("images");
        WriteResult r = collection.remove("{name: '" + name + "'}");

        return r.getN() == 1;
    }
}
