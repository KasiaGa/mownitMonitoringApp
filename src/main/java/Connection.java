import com.mongodb.*;

import java.net.UnknownHostException;
import java.time.LocalDateTime;

public class Connection {

    public static void main(String[] args) {
        try {
            MongoClient mongoClient = new MongoClient();
            DB database = mongoClient.getDB("projectdb");
            DBCollection collection = database.getCollection("monitoring_data");

            DBObject data = new BasicDBObject("_id", 1).append("timestamp", LocalDateTime.now().toString())
                    .append("source_id", 111).append("source_name", "Kernel-Power")
                    .append("event_id", 3).append("type", "information")
                    .append("percentage_usage", null).append("comment", "System przechodzi do trybu uśpienia");

            /*DBObject data = new BasicDBObject("_id", 2).append("timestamp", LocalDateTime.now().toString())
                    .append("source_id", 112).append("source_name", "Kernel-Troubleshooter")
                    .append("event_id", 3).append("type", "information")
                    .append("percentage_usage", null).append("comment", "System wrócił ze stanu niskiego poboru energii");*/

            collection.insert(data);


        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
//ID (int), timestamp(date), source ID(int), source name(varchar), event ID(int),
// type(varchar), percentage usage(int), comment(text)