package calendar.widget;

import calendar.databaseConnections.MongoDBClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;


public class WidgetGeneratorDaoMongo implements WidgetGeneratorDao {
    private Jongo client;

    public WidgetGeneratorDaoMongo() {
        client = MongoDBClient.getClient();
    }


    @Override
    public void addApiToken(Token token) {
        MongoCollection collection = client.getCollection("tokens");
        collection.insert(token);
    }
}
