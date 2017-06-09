package calendar.token;

import calendar.databaseConnections.MongoDBClient;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
class TokenDAOMongo implements TokenDAO {
    @Autowired
    private MongoDBClient client;

    public void add(Token token) {
        MongoCollection collection = client.getClient().getCollection("tokens");
        collection.insert(token);
    }

    public Token get(String strToken) {
        MongoCollection collection = client.getClient().getCollection("tokens");
        return collection.findOne("{token: strToken}").as(Token.class);
    }

    public void update(Token token) {
        MongoCollection collection = client.getClient().getCollection("tokens");
        collection.update(new ObjectId(token.getId())).with(token);
    }
}
