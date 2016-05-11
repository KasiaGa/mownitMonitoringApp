package database;

import com.mongodb.*;

import java.net.UnknownHostException;

public class Connection {

    private DBCollection connectToDatabase() {
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient();
            DB database = mongoClient.getDB("projectdb");
            return database.getCollection("monitoring_data");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertData(DatabaseObject object, int id) {
        DBCollection collection = connectToDatabase();
        DBObject data = new BasicDBObject("_id", id).append("timestamp", object.getTimestamp())
                .append("source_id", object.getSourceId()).append("source_name", object.getSourceName())
                .append("type", object.getType()).append("comment", object.getComment());
        assert collection != null;
        collection.insert(data);
    }

}