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

    public List<DBObject> getData() {
        List<DBObject> data = new ArrayList<>();
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
            DBObject object = new DBObject();
            object.setId(obj.getString("_id"));
            object.setTimestamp(obj.getString("timestamp"));
            object.setMonitoringCase(obj.getString("monitoring_case"));
            object.setAgentAddress(obj.getString("agent_address"));
            object.setName(obj.getString("name"));
            object.setResult(obj.getString("result"));
            data.add(object);
        }
        return data;
    }
}