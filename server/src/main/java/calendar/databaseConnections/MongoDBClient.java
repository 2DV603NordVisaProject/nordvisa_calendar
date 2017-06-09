package calendar.databaseConnections;

import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.springframework.stereotype.Component;

/**
 * Class MongoDBClient
 *
 * @author Axel Nilsson (axnion)
 */
@Component
public class MongoDBClient {
    private static Jongo jongo;

    public Jongo getClient() {
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
