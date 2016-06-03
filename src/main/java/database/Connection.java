package database;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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

    public void insertData(DBObject object) {
        DBCollection collection = connectToDatabase();
        com.mongodb.DBObject data = new BasicDBObject("_id", object.getId()).append("result", object.getResult()).append("timestamp", object.getTimestamp())
                .append("monitoring_case", object.getMonitoringCase()).append("agent_address", object.getAgentAddress())
                .append("name", object.getName()).append("type", object.getType());
        assert collection != null;
        collection.insert(data);
    }

    public int count() {
        try {
            MongoClient mongoClient;
            mongoClient = new MongoClient();
            DB database = mongoClient.getDB("projectdb");
            return database.getCollection("monitoring_data").find().count();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<DatabaseObject> getData() {
        List<DatabaseObject> data = new ArrayList<>();
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        DB database = mongoClient.getDB("projectdb");
        DBCursor cursor = database.getCollection("monitoring_data").find();
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            DatabaseObject object = new DatabaseObject();
            object.setId(obj.getInt("_id"));
            object.setTimestamp(obj.getString("timestamp"));
            object.setSourceId(obj.getInt("source_id"));
            object.setSourceName(obj.getString("source_name"));
            object.setType(obj.getString("type"));
            object.setComment(obj.getString("comment"));
            data.add(object);
        }
        return data;
    }
}