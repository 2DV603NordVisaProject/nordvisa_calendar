package calendar.token;

import calendar.databaseConnections.MongoDBClient;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;


class TokenDAOMongo implements TokenDAO {
    private Jongo client;

    TokenDAOMongo() {
        client = MongoDBClient.getClient();
    }

    public void add(Token token) {
        MongoCollection collection = client.getCollection("tokens");
        collection.insert(token);
    }

    public Token get(String strToken) {
        MongoCollection collection = client.getCollection("tokens");
        return collection.findOne("{token: strToken}").as(Token.class);
    }

    public void update(Token token) {
        MongoCollection collection = client.getCollection("tokens");
        collection.update(new ObjectId(token.getId())).with(token);
    }
}
