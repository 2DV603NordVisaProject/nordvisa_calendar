package calendar.DatabaseConnections;

import com.mongodb.MongoClient;
import org.jongo.Jongo;

/**
 * Class MongoDBClient
 *
 * @author Axel Nilsson (axnion)
 */
public class MongoDBClient {
    private static Jongo jongo;

    public static Jongo getClient() {
        if(jongo == null) {
            createConnection();
        }

        return jongo;
    }

    private static void createConnection() {
        MongoClient mongo = new MongoClient("localhost");
        jongo = new Jongo(mongo.getDB("nordvisa_calendar"));
    }
}
