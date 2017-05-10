package calendar.databaseConnections;

import org.jongo.Jongo;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class MongoDBClientTest
 *
 * @author Axel Nilsson (axnion)
 */
public class MongoDBClientTest {
    @Test
    public void getClientShouldNeverReturnNull() {
        Jongo jongo1 = MongoDBClient.getClient();
        assertNotNull(jongo1);

        Jongo jongo2 = MongoDBClient.getClient();
        assertNotNull(jongo2);
    }
}
