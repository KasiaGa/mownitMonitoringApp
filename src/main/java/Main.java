import database.Connection;
import database.DBObject;
import generator.GeneratorFromHSQL;

public class Main {

    public static void main(String[] args) {
        GeneratorFromHSQL generatorFromHSQL = new GeneratorFromHSQL();
        Connection connection = new Connection();

        generatorFromHSQL.joinData();

        for (DBObject dbObject : generatorFromHSQL.dbObjects) {
            connection.insertData(dbObject);
        }
    }
}
